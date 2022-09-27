package com.lia.system.security;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;


/**
 * 解析header中携带的token信息
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Value("${token.header}")
    private String header;
    @Autowired
    private Jwt jwt;
    @Autowired
    private RedisTemplate redisTemplate;

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
            Map map = jwt.parse((String) redisTemplate.opsForValue().get(uid));
            if(map == null || map.get("loginUser") == null){
                filterChain.doFilter(request,response);
                return;
            }
            LoginUser user = JSON.parseObject(JSON.toJSONString(map.get("loginUser")),LoginUser.class);
            //登录成功
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (ExpiredJwtException je){
            // 登录状态过期
            response.setStatus(402);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            filterChain.doFilter(request,response);
        }
    }

}
