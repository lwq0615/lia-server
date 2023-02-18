package com.lia.system.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lia.system.entity.SysMessage;
import com.lia.system.result.SysResult;
import com.lia.system.modules.message.SysMessageService;
import com.lia.system.security.LoginUser;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
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
     * @param session 客户端对应的通话session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LoginUser loginUser = (LoginUser) session.getAttributes().get("loginUser");
        WebSocketSession put = sessionPools.put(loginUser.getUser().getUserId(), session);
        if(put != null){
            LoginUser user = (LoginUser) put.getAttributes().get("loginUser");
            if(user.getTokenUuid().equals(loginUser.getTokenUuid())){
                return;
            }
            String res = JSON.toJSONString(HttpResult.error(SysResult.USER_LOGIN_OTHER));
            try {
                put.sendMessage(new TextMessage(res));
                put.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 接收客户端消息
     * @param session 客户端对应的通话session
     * @param message 客户端发送的消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        SysMessage sysMessage = JSONObject.parseObject(message.getPayload(), SysMessage.class);
        WebSocketSession toSession = sessionPools.get(sysMessage.getSendTo());
        // 消息发送成功
        if(sysMessageService.sendMessage(sysMessage)){
            try{
                // 通知消息发送者消息发送成功
                session.sendMessage(new TextMessage(JSON.toJSONString(HttpResult.ok(sysMessage))));
                // 接受者在线
                if(!Objects.isNull(toSession)){
                    // 将消息推送给接受者
                    toSession.sendMessage(new TextMessage(JSON.toJSONString(HttpResult.ok(sysMessage))));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭连接
     * @param session 客户端对应的通话session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        LoginUser loginUser = (LoginUser) session.getAttributes().get("loginUser");
        if(sessionPools.get(loginUser.getUser().getUserId()) == session){
            sessionPools.remove(loginUser.getUser().getUserId());
        }
    }


    /**
     * 向用户发送websocket消息
     * @param message
     * @param userId
     */
    public static void sendMessage(HttpResult message, Long userId) {
        if(sessionPools.get(userId) == null){
            return;
        }
        try {
            sessionPools.get(userId).sendMessage(new TextMessage(JSON.toJSONString(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
