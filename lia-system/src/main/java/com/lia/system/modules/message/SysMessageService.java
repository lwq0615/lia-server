package com.lia.system.modules.message;


import com.lia.system.entity.SysMessage;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class SysMessageService {


    @Autowired
    private SysMessageMapper sysMessageMapper;


    /**
     * 查询用户1与用户2的聊天记录
     * @param u1Id 用户1ID
     * @param u2Id 用户2ID
     * @return 聊天记录列表
     */
    public List<SysMessage> getMsgRecord(Long u1Id, Long u2Id) {
        if(u1Id == null){
            throw new HttpException(400, "缺少参数sendBy");
        }
        if(u2Id == null){
            throw new HttpException(400, "缺少参数sendTo");
        }
        return sysMessageMapper.getMsgRecord(u1Id, u2Id);
    }


    /**
     * 发送消息
     * @return 是否发送成功
     */
    public boolean sendMessage(SysMessage message) {
        if(message.getType() == null) return false;
        if(message.getSendBy() == null) return false;
        if(message.getSendTo() == null) return false;
        if(message.getContent() == null || message.getContent().equals("")) return false;
        return sysMessageMapper.sendMessage(message) > 0;
    }


    /**
     * 各个联系人未读聊天记录数量
     */
    public HashMap<Long, Integer> getNoReadCount(){
        HashMap<Long, Integer> res = new HashMap();
        for (SysMessage msg : sysMessageMapper.getNoReadCount(LoginUser.getLoginUserId())) {
            res.put(msg.getSendBy(), Integer.parseInt(msg.getContent()));
        }
        return res;
    }

    /**
     * 最后一条聊天记录
     */
    public HashMap<Long, String> getLastMsg(List<Long> userIds){
        if(userIds == null || userIds.size() < 1){
            return new HashMap<>();
        }
        HashMap<Long, String> res = new HashMap();
        for (SysMessage msg : sysMessageMapper.getLastMsg(LoginUser.getLoginUserId(), userIds)) {
            Long personId = msg.getSendBy().equals(LoginUser.getLoginUserId()) ? msg.getSendTo() : msg.getSendBy();
            res.put(personId, msg.getContent());
        }
        return res;
    }

    /**
     * 将用户1发送给用户2的聊天记录都标记为已读
     */
    public int readMessage(Long sendBy, Long sendTo){
        return sysMessageMapper.readMessage(sendBy, sendTo);
    }

}
