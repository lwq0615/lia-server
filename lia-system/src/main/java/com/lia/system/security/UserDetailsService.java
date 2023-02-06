package com.lia.system.security;

import com.lia.system.entity.SysAuth;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.modules.role.SysRoleMapper;
import com.lia.system.modules.user.SysUserMapper;
import com.lia.system.modules.auth.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser checkUser = new SysUser();
        checkUser.setUsername(username);
        SysUser user = sysUserMapper.getOneSysUser(checkUser);
        if(user == null){
            return null;
        }
        SysRole role = null;
        if(user.getRoleId() != null){
            SysRole checkRole = new SysRole();
            checkRole.setRoleId(user.getRoleId());
            List<SysRole> roles = sysRoleMapper.findSysRole(checkRole);
            if(roles.size() > 0){
                role = roles.get(0);
            }
        }
        return new LoginUser(user, role, new Date());
    }

}
