package com.lia.system.security;

import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.mapper.SysPowerMapper;
import com.lia.system.mapper.SysRoleMapper;
import com.lia.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPowerMapper sysPowerMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser checkUser = new SysUser();
        checkUser.setStatus('0');
        checkUser.setDelFlag('0');
        checkUser.setUsername(username);
        SysUser user = sysUserMapper.getOneSysUser(checkUser);
        if(user == null){
            return null;
        }
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
        return new LoginUser(user,role,powers);
    }

}
