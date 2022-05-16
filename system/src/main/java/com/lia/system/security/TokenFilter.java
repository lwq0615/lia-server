package com.lia.system.security;

import com.alibaba.fastjson.JSONObject;
import com.lia.system.entity.SysPower;
import com.lia.system.tool.Jwt;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
import java.util.Map;



@Component
public class TokenFilter extends OncePerRequestFilter {

    @Value("${token.header}")
    private String header;
    @Autowired
    private Jwt jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader(header);
        // 没有token，不需要解析
        if(token == null || token.equals("")){
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try{
            Map map = jwt.parse(token);
            if(map == null || map.get("loginUser") == null){
                filterChain.doFilter(request,response);
                return;
            }
            LoginUser user = JSONObject.parseObject(JSONObject.toJSONString(map.get("loginUser")),LoginUser.class);
            request.setAttribute("sysUser",user.getUser());
            //登录成功
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }catch (ExpiredJwtException je){
            // 登录状态过期
            response.setStatus(402);
        }finally {
            filterChain.doFilter(request,response);
        }
    }

}
