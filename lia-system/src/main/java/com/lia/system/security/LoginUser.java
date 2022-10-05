package com.lia.system.security;

import com.lia.system.modules.role.SysRole;
import com.lia.system.modules.user.SysUser;
import com.lia.system.exception.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser user;
    private SysRole role;
    private List<String> auths;
    private Date loginTime;

    /**
     * 获取当前登录的用户ID
     */
    public static Long getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.user.getUserId();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_"+role.getKey()));
        for (String auth : this.auths) {
            authorities.add(new Authority(auth));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
