package com.lia.system.security;

import com.lia.system.entity.SysPower;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.exception.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser user;
    private SysRole role;
    private List<String> powers;


    /**
     * 获取当前登录的用户信息
     */
    public static SysUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 如果是游客登录，抛出异常提示用户登录
        if(loginUser.getUser().getUserId() == 3){
            throw new HttpException(406, "该资源游客无法访问");
        }
        return loginUser.getUser();
    }


    /**
     * 获取当前用户的角色信息
     * @return
     */
    public static SysRole getLoginRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser.getRole();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_"+role.getKey()));
        for (String power : this.powers) {
            authorities.add(new Authority(power));
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
