package com.lia.system.modules.user;


import com.lia.system.entity.*;
import com.lia.system.exception.HttpException;
import com.lia.system.modules.file.SysFileService;
import com.lia.system.modules.registerCode.SysRegisterCodeService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.security.Jwt;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.ArrayUtils;
import com.lia.system.utils.DateUtils;
import com.lia.system.utils.HttpResult;
import com.lia.system.utils.StringUtils;
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
import org.springframework.web.socket.TextMessage;

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


    /**
     * 获取用户的Authorization字符串
     * @param checkUser 用户信息
     * @return 生成的Authorization字符串
     */
    public String getAuthorization(SysUser checkUser) {
        //判断是否合法用户
        if (checkUser.getUsername() == null || checkUser.getUsername().equals("")
                || checkUser.getPassword() == null || checkUser.getPassword().equals("")) {
            return "less param";
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(checkUser.getUsername(), checkUser.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            // 账号停用
            if (loginUser.getUser().getStatus() == '1') {
                return "user deactivate";
            }
            ValueOperations<String, Object> ops = Redis.getRedisTemplateByDb(RedisDb.USERTOKEN).opsForValue();
            // 挤下线
            String oldUUID = (String) ops.get("userId:" + loginUser.getUser().getUserId());
            if (oldUUID != null) {
                Redis.getRedisTemplateByDb(RedisDb.USERTOKEN).delete("uuid:" + oldUUID);
            }
            Map userInfo = new HashMap();
            userInfo.put("loginUser", loginUser);
            String uid = UUID.randomUUID().toString();
            // 登录状态存入redis
            ops.set("userId:" + loginUser.getUser().getUserId(), uid);
            ops.set("uuid:" + uid, jwt.getToken(userInfo));
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
            return "login failed";
        }
    }

    /**
     * 退出登录
     */
    public void logout(Long userId) {
        // 删除redis内的用户登录数据
        RedisTemplate<String, Object> redisTemplate = Redis.getRedisTemplateByDb(RedisDb.USERTOKEN);
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String uuid = (String) ops.get("userId:" + userId);
        redisTemplate.delete("uuid:" + uuid);
        redisTemplate.delete("userId:" + userId);
    }

    /**
     * 强制下线
     */
    public void forceLogout(Long userId) {
        this.logout(userId);
        // 通知客户端登录状态已经无效
        WebSocketHandler.sendMessage(new TextMessage("账号状态发生改变"), userId);
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
     * 编辑用户信息
     */
    public HttpResult editUser(SysUser user) {
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new HttpException(400, "缺少参数username");
        }
        if (user.getNick() == null || user.getNick().equals("")) {
            throw new HttpException(400, "缺少参数nick");
        }
        if (user.getRoleId() == null) {
            throw new HttpException(400, "缺少参数roleId");
        }
        // 校验手机号
        if(!StringUtils.isEmpty(user.getPhone())){
            String regex = "^[1]([3-9])[0-9]{9}$";
            if(user.getPhone().length() != 11 || !Pattern.matches(regex, user.getPhone())){
                throw new HttpException(400, "请输入正确的手机号");
            }
        }
        //查询是否有相同用户名未删除的用户
        SysUser newUser = new SysUser();
        newUser.setUsername(user.getUsername());
        newUser.setDelFlag('0');
        List<SysUser> sysUserPage = sysUserMapper.getSysUserPage(newUser);
        if (sysUserPage == null || sysUserPage.size() == 0
                || sysUserPage.get(0).getUserId().equals(user.getUserId())) {
            if(sysUserMapper.editSysUser(user) > 0){
                return HttpResult.success("success");
            }else{
                throw new HttpException(500, "编辑失败");
            }
        } else {
            return HttpResult.error(202, "用户名已存在");
        }
    }


    /**
     * 用户注册
     */
    public HttpResult register(SysUser user, String registerCode) {
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new HttpException(400, "缺少参数username");
        }
        if (user.getNick() == null || user.getNick().equals("")) {
            throw new HttpException(400, "缺少参数nick");
        }
        // 新增的用户必须要有password
        if (user.getUserId() == null && (user.getPassword() == null || user.getPassword().equals(""))) {
            throw new HttpException(400, "缺少参数password");
        }
        // 校验手机号
        if(!StringUtils.isEmpty(user.getPhone())){
            String regex = "^[1]([3-9])[0-9]{9}$";
            if(user.getPhone().length() != 11 || !Pattern.matches(regex, user.getPhone())){
                throw new HttpException(400, "请输入正确的手机号");
            }
        }
        SysRegisterCode sysRegisterCode = null;
        if(!StringUtils.isEmpty(registerCode)){
            sysRegisterCode = sysRegisterCodeService.selectOne(new SysRegisterCode().setCode(registerCode));
            if(sysRegisterCode == null){
                return HttpResult.error(203, "注册码不存在");
            }
            if(sysRegisterCode.getUseBy() != null){
                return HttpResult.error(201, "注册码已被使用");
            }
            user.setRoleId(sysRegisterCode.getRoleId());
        }else{
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
            if(sysUserMapper.addSysUser(user) > 0){
                if(!StringUtils.isEmpty(registerCode)){
                    // 将注册码标记为已使用
                    sysRegisterCode.setUseBy(user.getUserId()).setUseTime(DateUtils.mysqlDatetime(new Date()));
                    sysRegisterCodeService.save(sysRegisterCode);
                }
                return HttpResult.success(user);
            }else{
                throw new HttpException(500, "注册失败");
            }
        } else {
            return HttpResult.error(202, "用户名已存在");
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
        SysFile image = sysFileService.uploadFile(file, "image");
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
