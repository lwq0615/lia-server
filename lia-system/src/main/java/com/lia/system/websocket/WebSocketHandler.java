package com.lia.system.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lia.system.modules.message.SysMessage;
import com.lia.system.modules.message.SysMessageService;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired
    private SysMessageService sysMessageService;

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
        SysMessage sysMessage = JSONObject.parseObject(message.getPayload(), SysMessage.class);
        WebSocketSession toSession = sessionPools.get(sysMessage.getSendTo());
        // 消息发送成功
        if(sysMessageService.sendMessage(sysMessage)){
            // 通知消息发送者消息发送成功
            session.sendMessage(new TextMessage(JSON.toJSONString(sysMessage)));
            // 接受者在线
            if(!Objects.isNull(toSession)){
                // 将消息推送给接受者
                toSession.sendMessage(new TextMessage(JSON.toJSONString(sysMessage)));
            }
        }
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
