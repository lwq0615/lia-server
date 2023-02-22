package com.lia.system.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lia.system.entity.SysAuth;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.modules.auth.SysAuthService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.utils.SpringUtils;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Accessors(chain = true)
@ToString
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


    private SysUser user;
    private String roleKey;
    private Long loginTime;
    private String tokenUuid;

    public LoginUser(){}


    public LoginUser(SysUser user, SysRole role, Date date){
        this.user = user;
        this.roleKey = role.getKey();
        this.loginTime = date.getTime();
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
        List<String> auths = (ArrayList<String>) Redis.getTemplate(RedisDb.SYSTEM)
                .opsForValue().get(REDIS_ROLE_AUTHS + user.getRoleId());
        if(auths == null){
            SysAuthService sysAuthService = SpringUtils.getContext().getBean(SysAuthService.class);
            // 如果从redis中没有查询到，从mysql中查，并将结果存入redis
            List<SysAuth> sysAuths = sysAuthService.findSysAuthByRoleId(user.getRoleId());
            auths = sysAuths.stream().map(auth -> auth.getKey()).collect(Collectors.toList());
            Redis.getTemplate(RedisDb.SYSTEM).opsForValue()
                    .set(LoginUser.REDIS_ROLE_AUTHS + user.getRoleId(), auths);
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
}
