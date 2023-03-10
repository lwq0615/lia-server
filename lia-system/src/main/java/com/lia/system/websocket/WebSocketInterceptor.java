package com.lia.system.websocket;

import com.alibaba.fastjson2.JSON;
import com.lia.system.redis.Redis;
import com.lia.system.redis.RedisDb;
import com.lia.system.security.Jwt;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;


@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Value("${token.header}")
    private String header;
    @Autowired
    private Jwt jwt;


    /**
     * 握手前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) {
        try{
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            String uid = request.getServletRequest().getParameter(header);
            Map map = jwt.parse((String) Redis.getTemplate(RedisDb.SYSTEM).opsForValue()
                    .get(LoginUser.REDIS_LOGIN_USER_TOKEN+uid));
            LoginUser user = JSON.parseObject(JSON.toJSONString(map.get("loginUser")),LoginUser.class);
            Long userId = user.getUser().getUserId();
            if(Objects.isNull(userId)){
                return false;
            }
            attributes.put("loginUser", user);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    /**
     * 握手后
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }


}
