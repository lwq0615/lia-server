package com.lia.system.security;

import com.alibaba.fastjson2.JSON;
import com.lia.system.entity.SysFile;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.result.RequestTemp;
import com.lia.system.utils.SpringUtils;
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
import java.util.Date;
import java.util.Map;


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


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestTemp req = SpringUtils.getBean(RequestTemp.class);
        req.setReq(request);
        String uid = request.getHeader(header);
        response.setContentType("application/json;charset=utf-8");
        // 如果是访问图片或者文件资源接口，则从uri中获取token
        if(SysFile.FILE_REQ_URL.contains(request.getRequestURI())){
            uid = request.getParameter(header);
        }
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
            e.printStackTrace();
        }finally{
            filterChain.doFilter(request,response);
        }
    }

}
