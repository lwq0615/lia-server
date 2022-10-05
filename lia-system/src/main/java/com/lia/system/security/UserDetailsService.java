package com.lia.system.security;

import com.lia.system.modules.auth.SysAuth;
import com.lia.system.modules.role.SysRole;
import com.lia.system.modules.user.SysUser;
import com.lia.system.modules.role.SysRoleMapper;
import com.lia.system.modules.user.SysUserMapper;
import com.lia.system.modules.auth.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SysAuthService sysAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser checkUser = new SysUser();
        checkUser.setUsername(username);
        SysUser user = sysUserMapper.getOneSysUser(checkUser);
        if(user == null){
            return null;
        }
        SysRole role = null;
        List<String> auths = new ArrayList<>();
        if(user.getRoleId() != null){
            SysRole checkRole = new SysRole();
            checkRole.setRoleId(user.getRoleId());
            List<SysRole> roles = sysRoleMapper.findSysRole(checkRole);
            if(roles.size() > 0){
                role = roles.get(0);
                for (SysAuth sysAuth : sysAuthService.findSysAuthByRoleId(role.getRoleId())) {
                    auths.add(sysAuth.getKey());
                }
            }
        }
        return new LoginUser(user, role, auths, new Date());
    }

}
