package com.lia.system.modules.user;


import com.lia.system.modules.file.SysFile;
import com.lia.system.security.LoginUser;
import com.lia.system.security.Jwt;
import com.lia.system.modules.file.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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



    /**
     * 获取用户的Authorization字符串
     * @param checkUser 用户信息
     * @return 生成的Authorization字符串
     */
    public String getAuthorization(SysUser checkUser){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(checkUser.getUsername(),checkUser.getPassword());
        try{
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            Map userInfo = new HashMap();
            userInfo.put("loginTime",System.currentTimeMillis()/1000);
            userInfo.put("loginUser",loginUser);
            return jwt.getToken(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return "login failed";
        }
    }


    /**
     * 查询用户列表
     * @param user 查询参数
     * @return 用户列表
     */
    public List<SysUser> findSysUser(SysUser user){
        return sysUserMapper.getSysUserPage(user);
    }



    /**
     * 新增或编辑用户
     * @param user
     * @return
     */
    public String saveUser(SysUser user){
        // 密码加密后在存入数据库
        if(user.getPassword() != null && !user.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        int success;
        try{
            if(user.getUserId() == null){
                // 新增的用户createBy为当前用户
                user.setCreateBy(LoginUser.getLoginUserId());
                success = sysUserMapper.addSysUser(user);
            }else{
                success = sysUserMapper.editSysUser(user);
            }
        }catch (DuplicateKeyException e){
            return "用户名已存在";
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除用户
     * @param userIds 用户的id列表
     * @return 删除成功的数量
     */
    public int deleteUsers(List<Integer> userIds){
        // 不允许删除admin账户
        if(userIds.contains(1)){
            userIds.remove(userIds.indexOf(1));
        }
        if(userIds.size() == 0){
            return 0;
        }
        return sysUserMapper.deleteUsers(userIds);
    }


    /**
     * 更换用户头像
     * @param file
     * @return
     */
    public SysFile updateHeadImg(MultipartFile file, Long fileId){
        SysUser user = new SysUser();
        user.setUserId(LoginUser.getLoginUserId());
        SysFile image = sysFileService.uploadFile(file, "image");
        // 如果是更换头像，则修改原来的头像数据
        if(fileId != null){
            SysFile oldSysFile = new SysFile();
            oldSysFile.setFileId(fileId);
            oldSysFile = sysFileService.getSysFile(oldSysFile).get(0);
            // 删除旧的头像文件
            if(oldSysFile.getPath() != null && !oldSysFile.getPath().equals("")){
                File oldFile = new File(oldSysFile.getPath());
                if (oldFile.exists()) oldFile.delete();
            }
            //更换数据库内的头像数据
            image.setFileId(oldSysFile.getFileId());
            sysFileService.saveFile(image);
        }
        //如果是上传新的头像
        else{
            image = sysFileService.saveFile(image);
            user = this.findSysUser(user).get(0);
            user.setHeadImg(image.getFileId());
            this.saveUser(user);
        }
        return image;
    }

}
