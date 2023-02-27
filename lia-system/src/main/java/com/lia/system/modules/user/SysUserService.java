package com.lia.system.modules.user;


import com.alibaba.fastjson2.JSON;
import com.lia.system.entity.*;
import com.lia.system.modules.file.UploadDir;
import com.lia.system.modules.role.SysRoleService;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.modules.file.SysFileService;
import com.lia.system.modules.registerCode.SysRegisterCodeService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.security.Jwt;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.ArrayUtils;
import com.lia.system.utils.DateUtils;
import com.lia.system.result.HttpResult;
import com.lia.system.utils.StrUtils;
import com.lia.system.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;

@Service
@Transactional
public class SysUserService {


    @Autowired
    private Jwt jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private SysRegisterCodeService sysRegisterCodeService;
    @Autowired
    private SysRoleService sysRoleService;


    /**
     * 获取用户的Authorization字符串
     * @param checkUser 用户信息
     * @return 生成的Authorization字符串
     */
    public String getAuthorization(SysUser checkUser) {
        //判断是否合法用户
        if (StrUtils.isEmpty(checkUser.getUsername())) {
            throw new HttpException("缺少参数username");
        }
        if (StrUtils.isEmpty(checkUser.getPassword())) {
            throw new HttpException("缺少参数password");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(checkUser.getUsername(), checkUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 账号停用
        if (loginUser.getUser().getStatus() == '1') {
            throw new HttpException(SysResult.USER_DEACTIVATE);
        }
        ValueOperations<String, Object> ops = Redis.getTemplate(RedisDb.SYSTEM).opsForValue();
        // 挤下线
        String oldUUID = (String) ops.get(LoginUser.REDIS_LOGIN_USER_UUID + loginUser.getUser().getUserId());
        if (oldUUID != null) {
            Redis.getTemplate(RedisDb.SYSTEM).delete(LoginUser.REDIS_LOGIN_USER_TOKEN + oldUUID);
        }
        Map userInfo = new HashMap();
        String uid = UUID.randomUUID().toString();
        loginUser.setTokenUuid(uid);
        userInfo.put("loginUser", loginUser);
        // 登录状态存入redis
        ops.set(LoginUser.REDIS_LOGIN_USER_UUID + loginUser.getUser().getUserId(), uid);
        ops.set(LoginUser.REDIS_LOGIN_USER_TOKEN + uid, jwt.getToken(userInfo));
        return uid;
    }

    /**
     * 退出登录
     */
    public void logout(Long userId) {
        // 删除redis内的用户登录数据
        RedisTemplate<String, Object> redisTemplate = Redis.getTemplate(RedisDb.SYSTEM);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String uuid = (String) ops.get(LoginUser.REDIS_LOGIN_USER_UUID + userId);
        redisTemplate.delete(LoginUser.REDIS_LOGIN_USER_TOKEN + uuid);
        redisTemplate.delete(LoginUser.REDIS_LOGIN_USER_UUID + userId);
    }

    /**
     * 强制下线，客户端也会同时被退出登录
     */
    public void forceLogout(Long userId) {
        this.logout(userId);
        // 通知客户端登录状态已经无效
        WebSocketHandler.sendMessage(HttpResult.error(SysResult.USER_STATE_CHANGE), userId);
    }


    /**
     * 查询用户列表
     * @param user 查询参数
     * @return 用户列表
     */
    public List<SysUser> findSysUser(SysUser user) {
        return sysUserMapper.getSysUserPage(user);
    }

    /**
     * 根据企业ID获取可以聊天的用户
     * 只有相同企业的用户可以相互聊天
     */
    public List<SysUser> personList() {
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        Integer companyId = sysUserMapper.getSysUserPage(user).get(0).getCompanyId();
        return sysUserMapper.personList(companyId, user.getUserId());
    }


    /**
     * 校验用户信息合法性
     */
    private void checkUser(SysUser user){
        if(user.getUserId().equals(SysUser.ADMIN_USER_ID)){
            return;
        }
        if(user.getUserId().equals(SysUser.TEST_USER_ID)){
            return;
        }
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new HttpException("缺少参数username");
        }
        if (user.getUsername().length() < 8) {
            throw new HttpException("用户名长度最少为8位");
        }
        if (user.getNick() == null || user.getNick().equals("")) {
            throw new HttpException("缺少参数nick");
        }
        // 校验手机号
        if (!StrUtils.isEmpty(user.getPhone())) {
            String regex = "^[1]([3-9])[0-9]{9}$";
            if (user.getPhone().length() != 11 || !Pattern.matches(regex, user.getPhone())) {
                throw new HttpException("手机号格式错误");
            }
        }
    }


    /**
     * 更新redis中的用户登录信息
     */
    public void updateRedisLoginUser(SysUser user){
        RedisTemplate<String, Object> redisTemplate = Redis.getTemplate(RedisDb.SYSTEM);
        String uuid = (String) redisTemplate.opsForValue().get(LoginUser.REDIS_LOGIN_USER_UUID + user.getUserId());
        Map map = jwt.parse((String) redisTemplate.opsForValue().get(LoginUser.REDIS_LOGIN_USER_TOKEN +uuid));
        if(map == null || map.get("loginUser") == null){
            return;
        }
        LoginUser oldUser = JSON.parseObject(JSON.toJSONString(map.get("loginUser")),LoginUser.class);
        oldUser.setUser(user);
        SysRole role = sysRoleService.findSysRole(new SysRole().setRoleId(user.getRoleId())).get(0);
        oldUser.setRoleKey(role.getKey());
        Map userInfo = new HashMap();
        userInfo.put("loginUser", oldUser);
        redisTemplate.opsForValue().set(LoginUser.REDIS_LOGIN_USER_TOKEN+uuid, jwt.getToken(userInfo));
    }


    /**
     * 编辑用户信息
     */
    public int editUser(SysUser user) {
        this.checkUser(user);
        //查询是否有相同用户名未删除的用户
        SysUser newUser = new SysUser();
        newUser.setUsername(user.getUsername());
        newUser.setDelFlag('0');
        List<SysUser> sysUserPage = sysUserMapper.getSysUserPage(newUser);
        if (sysUserPage == null || sysUserPage.size() == 0
                || sysUserPage.get(0).getUserId().equals(user.getUserId())) {
            SysUser oldUser = sysUserMapper.getSysUserPage(new SysUser().setUserId(user.getUserId())).get(0);
            int success = sysUserMapper.editSysUser(user);
            // 修改了用户角色的同时修改redis中的用户信息
            if(success > 0 && !user.getRoleId().equals(oldUser.getRoleId())){
                this.updateRedisLoginUser(user);
            }
            return success;
        } else {
            throw new HttpException(SysResult.USERNAME_EXISTED);
        }
    }


    /**
     * 用户注册
     */
    public int register(SysUser user, String registerCode) {
        this.checkUser(user);
        // 新增的用户必须要有password
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new HttpException("缺少参数password");
        }
        if (user.getPassword().length() < 8) {
            throw new HttpException("用户密码长度最少为8位");
        }
        SysRegisterCode sysRegisterCode = null;
        if (!StrUtils.isEmpty(registerCode)) {
            sysRegisterCode = sysRegisterCodeService.selectOne(new SysRegisterCode().setCode(registerCode));
            if (sysRegisterCode == null) {
                throw new HttpException(SysResult.REGISTER_NOT_EXIST);
            }
            if (sysRegisterCode.getUseBy() != null) {
                throw new HttpException(SysResult.REGISTER_USED);
            }
            user.setRoleId(sysRegisterCode.getRoleId());
        } else {
            user.setRoleId(SysRole.COMMON_ROLE_ID);
        }
        // 密码加密后在存入数据库
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //查询是否有相同用户名未删除的用户
        SysUser newUser = new SysUser();
        newUser.setUsername(user.getUsername());
        newUser.setDelFlag('0');
        List<SysUser> sysUserPage = sysUserMapper.getSysUserPage(newUser);
        if (sysUserPage == null || sysUserPage.size() == 0) {
            // 新增的用户createBy为当前用户
            user.setCreateBy(LoginUser.getLoginUserId());
            int success = sysUserMapper.addSysUser(user);
            if (success > 0) {
                if (!StrUtils.isEmpty(registerCode)) {
                    // 将注册码标记为已使用
                    sysRegisterCode.setUseBy(user.getUserId()).setUseTime(DateUtils.mysqlDatetime(new Date()));
                    sysRegisterCodeService.save(sysRegisterCode);
                }
                return success;
            } else {
                throw new HttpException(SysResult.SERVER_ERROR);
            }
        } else {
            throw new HttpException(SysResult.USERNAME_EXISTED);
        }
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    public int deleteUsers(List<Long> userIds) {
        // 不允许删除admin账户
        if (userIds.contains(SysUser.ADMIN_USER_ID)) {
            userIds.remove(userIds.indexOf(SysUser.ADMIN_USER_ID));
        }
        // 不允许删除测试账户
        if (userIds.contains(SysUser.TEST_USER_ID)) {
            userIds.remove(userIds.indexOf(SysUser.TEST_USER_ID));
        }
        if (userIds.size() == 0) {
            return 0;
        }
        int result = sysUserMapper.deleteUsers(userIds);
        for (Long userId : userIds) {
            this.forceLogout(userId);
        }
        return result;
    }


    /**
     * 更换用户头像
     */
    public SysFile updateHeadImg(MultipartFile file) {
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        user = this.findSysUser(user).get(0);
        // 保存新的头像
        SysFile image = sysFileService.uploadFile(file, UploadDir.IMAGE);
        // 如果是更换头像，则删除原来的头像数据
        if (user.getHeadImg() != null) {
            //删除数据库内的头像数据
            sysFileService.deleteFiles(ArrayUtils.asList(user.getHeadImg()));
        }
        //保存新的头像到数据库
        user.setHeadImg(image.getFileId());
        this.editUser(user);
        return image;
    }


    /**
     * 获取创建人字典表
     */
    public List<SysDictData> getCreateByDict() {
        return sysUserMapper.getCreateByDict();
    }

}
