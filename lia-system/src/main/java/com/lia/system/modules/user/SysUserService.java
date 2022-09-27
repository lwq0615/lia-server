package com.lia.system.modules.user;


import com.lia.system.modules.dictData.SysDictData;
import com.lia.system.modules.file.SysFile;
import com.lia.system.security.LoginUser;
import com.lia.system.security.Jwt;
import com.lia.system.modules.file.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;


    /**
     * 获取用户的Authorization字符串
     * @param checkUser 用户信息
     * @return 生成的Authorization字符串
     */
    public String getAuthorization(SysUser checkUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(checkUser.getUsername(), checkUser.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            Map userInfo = new HashMap();
            userInfo.put("loginTime", System.currentTimeMillis() / 1000);
            userInfo.put("loginUser", loginUser);
            String uid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uid, jwt.getToken(userInfo));
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
            return "login failed";
        }
    }


    /**
     * 查询用户列表
     *
     * @param user 查询参数
     * @return 用户列表
     */
    public List<SysUser> findSysUser(SysUser user) {
        return sysUserMapper.getSysUserPage(user);
    }


    /**
     * 新增或编辑用户
     *
     * @param user
     * @return
     */
    public String saveUser(SysUser user) {
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
        SysFile image = sysFileService.uploadFile(file, "image");
        // 如果是更换头像，则修改原来的头像数据
        if (fileId != null) {
            SysFile oldSysFile = new SysFile();
            oldSysFile.setFileId(fileId);
            oldSysFile = sysFileService.getSysFile(oldSysFile).get(0);
            // 删除旧的头像文件
            if (oldSysFile.getPath() != null && !oldSysFile.getPath().equals("")) {
                File oldFile = new File(oldSysFile.getPath());
                if (oldFile.exists()) oldFile.delete();
            }
            //更换数据库内的头像数据
            image.setFileId(oldSysFile.getFileId());
            sysFileService.saveFile(image);
        }
        //如果是上传新的头像
        else {
            image = sysFileService.saveFile(image);
            user = this.findSysUser(user).get(0);
            user.setHeadImg(image.getFileId());
            this.saveUser(user);
        }
        return image;
    }


    /**
     * 获取创建人字典表
     */
    public List<SysDictData> getCreateByDict() {
        return sysUserMapper.getCreateByDict();
    }

}
