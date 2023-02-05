package com.lia.system.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lia.system.entity.SysAuth;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.modules.auth.SysAuthService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Accessors(chain = true)
@Component
@Scope("prototype")
public class LoginUser implements UserDetails {


    /**
     * redis中存放登录用户uuid的key前缀
     */
    public static final String REDIS_LOGIN_USER_UUID = "loginUser:id:";

    /**
     * redis中存放登录用户token的key前缀
     */
    public static final String REDIS_LOGIN_USER_TOKEN = "loginUser:uuid:";

    /**
     * redis中存放角色权限信息的key前缀
     */
    public static final String REDIS_ROLE_AUTHS = "role:";


    @Autowired
    @JsonIgnore
    private SysAuthService sysAuthService;


    private SysUser user;
    private Integer roleId;
    private String roleKey;
    private Long loginTime;


    public LoginUser init(SysUser user, SysRole role, Date date){
        this.user = user;
        this.roleId = role.getRoleId();
        this.roleKey = role.getKey();
        this.loginTime = date.getTime();
        return this;
    }


    /**
     * 获取当前登录的用户ID
     */
    public static Long getLoginUserId() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            return loginUser.user.getUserId();
        }catch (Exception e){
            return null;
        }
    }



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_"+roleKey));
        List<String> auths = (ArrayList<String>) Redis.getRedisTemplateByDb(RedisDb.USERTOKEN)
                .opsForValue().get(REDIS_ROLE_AUTHS + roleId);
        if(auths == null){
            // 如果从redis中没有查询到，从mysql中查，并将结果存入redis
            List<SysAuth> sysAuths = sysAuthService.findSysAuthByRoleId(roleId);
            auths = sysAuths.stream().map(auth -> auth.getKey()).collect(Collectors.toList());
            Redis.getRedisTemplateByDb(RedisDb.USERTOKEN).opsForValue()
                    .set(LoginUser.REDIS_ROLE_AUTHS + roleId, auths);
        }
        authorities.addAll(auths.stream().map(auth -> new Authority(auth)).collect(Collectors.toList()));
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

    @Override
    public String toString() {
        return "LoginUser{" +
                "user=" + user +
                ", roleId=" + roleId +
                ", roleKey='" + roleKey + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
