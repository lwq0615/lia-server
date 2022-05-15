package com.lia.system.service;


import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.mapper.SysPowerMapper;
import com.lia.system.mapper.SysRoleMapper;
import com.lia.system.mapper.SysUserMapper;
import com.lia.system.security.LoginUser;
import com.lia.system.tool.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserService {


    @Autowired
    private Jwt jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPowerMapper sysPowerMapper;



    /**
     * 获取用户的Authorization字符串
     * @param checkUser 用户信息
     * @return 生成的Authorization字符串
     */
    public String getAuthorization(SysUser checkUser){
        checkUser.setStatus('0');
        checkUser.setDelFlag('0');
        List<SysUser> users = sysUserMapper.findSysUser(checkUser);
        if(users.size() == 0){
            return null;
        }
        SysUser user = users.get(0);
        SysRole role = null;
        List<SysPower> powers = null;
        if(user.getRoleId() != null){
            SysRole checkRole = new SysRole();
            checkRole.setRoleId(user.getRoleId());
            List<SysRole> roles = sysRoleMapper.findSysRole(checkRole);
            if(roles.size() > 0){
                role = roles.get(0);
                powers = sysPowerMapper.findSysPowerByRoleId(role.getRoleId());
            }
        }
        Map userInfo = new HashMap();
        userInfo.put("loginTime",System.currentTimeMillis()/1000);
        userInfo.put("loginUser",new LoginUser(user,role,powers));
        return jwt.getToken(userInfo);
    }


    /**
     * 查询用户列表
     * @param user 查询参数
     * @return 用户列表
     */
    public List<SysUser> findSysUser(SysUser user){
        user.setDelFlag('0');
        return sysUserMapper.findSysUser(user);
    }



    /**
     * 新增或编辑用户
     * @param user
     * @return
     */
    public String saveUser(SysUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int success;
        try{
            if(user.getUserId() == null){
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
