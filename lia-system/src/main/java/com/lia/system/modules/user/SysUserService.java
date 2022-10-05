package com.lia.system.modules.user;


import com.lia.system.exception.HttpException;
import com.lia.system.modules.dictData.SysDictData;
import com.lia.system.modules.file.SysFile;
import com.lia.system.modules.file.SysFileService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.security.Jwt;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.ArrayUtils;
import com.sun.deploy.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

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
    private Redis redis;


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
            if(loginUser.getUser().getStatus() == '1'){
                return "user deactivate";
            }
            Map userInfo = new HashMap();
            userInfo.put("loginUser", loginUser);
            String uid = UUID.randomUUID().toString();
            redis.getRedisTemplateByDb(RedisDb.USERTOKEN).opsForValue().set(uid, jwt.getToken(userInfo));
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
            return "login failed";
        }
    }

    /**
     * 退出登录
     */
    public void logout(String uid){
        redis.getRedisTemplateByDb(RedisDb.USERTOKEN).delete(uid);
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
     * 根据企业ID获取可以聊天的用户（只有相同企业的用户可以相互聊天）
     */
    public List<SysUser> personList() {
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        Integer companyId = sysUserMapper.getSysUserPage(user).get(0).getCompanyId();
        return sysUserMapper.personList(companyId, user.getUserId());
    }


    /**
     * 新增或编辑用户
     * @param user
     * @return
     */
    public String saveUser(SysUser user) {
        if (user.getUsername() == null || user.getUsername().equals("")) {
            throw new HttpException(400, "缺少参数username");
        }
        if (user.getNick() == null || user.getNick().equals("")) {
            throw new HttpException(400, "缺少参数nick");
        }
        if (user.getRoleId() == null) {
            throw new HttpException(400, "缺少参数roleId");
        }
        // 新增的用户必须要有password
        if (user.getUserId() == null && (user.getPassword() == null || user.getPassword().equals(""))) {
            throw new HttpException(400, "缺少参数password");
        }
        // 密码加密后在存入数据库
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        int success;
        //查询是否有相同的未删除的用户名
        SysUser newUser = new SysUser();
        newUser.setUsername(user.getUsername());
        newUser.setDelFlag('0');
        List<SysUser> sysUserPage = sysUserMapper.getSysUserPage(newUser);
        // 新增
        if (user.getUserId() == null) {
            if (sysUserPage == null || sysUserPage.size() == 0) {
                // 新增的用户createBy为当前用户
                user.setCreateBy(LoginUser.getLoginUserId());
                success = sysUserMapper.addSysUser(user);
            } else {
                return "用户名已存在";
            }
        }
        // 编辑
        else {
            if (sysUserPage == null || sysUserPage.size() == 0
                    || sysUserPage.get(0).getUserId().equals(user.getUserId())) {
                success = sysUserMapper.editSysUser(user);
            }else {
                return "用户名已存在";
            }
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除用户
     *
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    public int deleteUsers(List<Integer> userIds) {
        // 不允许删除admin账户
        if (userIds.contains(1)) {
            userIds.remove(userIds.indexOf(1));
        }
        // 不允许删除测试账户
        if (userIds.contains(2)) {
            userIds.remove(userIds.indexOf(2));
        }
        if (userIds.size() == 0) {
            return 0;
        }
        return sysUserMapper.deleteUsers(userIds);
    }


    /**
     * 更换用户头像
     *
     * @param file
     * @return
     */
    public SysFile updateHeadImg(MultipartFile file, Long fileId) {
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        // 保存新的头像
        SysFile image = sysFileService.uploadFile(file, "image");
        // 如果是更换头像，则删除原来的头像数据
        if (fileId != null) {
            //删除数据库内的头像数据
            sysFileService.deleteFiles(ArrayUtils.asList(fileId));
        }
        //保存新的头像到数据库
        user = this.findSysUser(user).get(0);
        user.setHeadImg(image.getFileId());
        this.saveUser(user);
        return image;
    }


    /**
     * 获取创建人字典表
     */
    public List<SysDictData> getCreateByDict() {
        return sysUserMapper.getCreateByDict();
    }

}
