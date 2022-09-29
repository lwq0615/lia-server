package com.lia.system.websocket;

import com.lia.system.security.LoginUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;


@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    /**
     * 存放每个客户端连接对象
     */
    private static ConcurrentHashMap<Long, WebSocketSession> sessionPools = new ConcurrentHashMap<>();


    /**
     * 建立连接
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttributes().get("loginUser");
        sessionPools.put(loginUser.getUser().getUserId(), session);
    }


    /**
     * 接收客户端消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message);
    }


    /**
     * 关闭连接
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LoginUser loginUser = (LoginUser) session.getAttributes().get("loginUser");
        sessionPools.remove(loginUser.getUser().getUserId());
    }

}
