package com.lia.system.modules.message;


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
     *
     * @param u1Id 用户1ID
     * @param u2Id 用户2ID
     * @return 聊天记录列表
     */
    public List<SysMessage> getMsgRecord(Long u1Id, Long u2Id) {
        return sysMessageMapper.getMsgRecord(u1Id, u2Id);
    }


    /**
     * 发送消息
     *
     * @return 是否发送成功
     */
    public boolean sendMessage(SysMessage message) {
        return sysMessageMapper.sendMessage(message) > 0;
    }


    /**
     * 未读聊天记录数量
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
    public HashMap<Long, HashMap> getLastMsg(List<Long> userIds){
        HashMap<Long, HashMap> res = new HashMap();
        for (SysMessage msg : sysMessageMapper.getLastMsg(LoginUser.getLoginUserId(), userIds)) {
            Long personId = msg.getSendBy().equals(LoginUser.getLoginUserId()) ? msg.getSendTo() : msg.getSendBy();
            HashMap msgInfo = new HashMap();
            msgInfo.put("sendBy", msg.getSendBy().equals(LoginUser.getLoginUserId()) ? null : msg.getSendBy());
            msgInfo.put("content", msg.getContent());
            res.put(personId, msgInfo);
        }
        return res;

    }

}
