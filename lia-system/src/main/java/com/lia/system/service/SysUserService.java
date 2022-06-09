package com.lia.system.service;


import com.lia.system.entity.SysUser;
import com.lia.system.mapper.SysUserMapper;
import com.lia.system.security.LoginUser;
import com.lia.system.security.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
        if(user.getPassword() != null && !user.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        int success;
        try{
            if(user.getUserId() == null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dateFormat.format(new Date());
                user.setCreateTime(date);
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

}
