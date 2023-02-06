package com.lia.system.security;

import com.alibaba.fastjson2.JSON;
import com.lia.system.entity.SysAuth;
import com.lia.system.modules.auth.SysAuthService;
import com.lia.system.modules.user.SysUserService;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.result.ResultCode;
import com.lia.system.result.exception.GlobalException;
import com.lia.system.result.exception.HttpException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 解析header中携带的token信息
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    /**
     * Authorization字符串过期时间
     */
    @Value("${token.expireTime}")
    private int expireTime;
    @Value("${token.header}")
    private String header;
    @Autowired
    private Jwt jwt;
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String uid = request.getHeader(header);
        // 没有token，不需要解析
        if(uid == null || uid.equals("")){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try{
            RedisTemplate<String, Object> redisTemplate = Redis.getTemplate(RedisDb.SYSTEM);
            Map map = jwt.parse((String) redisTemplate.opsForValue().get(LoginUser.REDIS_LOGIN_USER_TOKEN +uid));
            if(map == null || map.get("loginUser") == null){
                filterChain.doFilter(request,response);
                return;
            }
            LoginUser user = JSON.parseObject(JSON.toJSONString(map.get("loginUser")),LoginUser.class);
            if(expireTime != 0){
                long time = new Date().getTime() - user.getLoginTime();
                // 登录状态过期
                if(time > expireTime * 60 * 1000){
                    response.setStatus(402);
                    redisTemplate.delete(uid);
                    return;
                }
            }
            //登录成功
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (Exception e) {
        }finally{
            filterChain.doFilter(request,response);
        }
    }

}
