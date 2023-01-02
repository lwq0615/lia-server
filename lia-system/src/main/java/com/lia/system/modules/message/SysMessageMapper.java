package com.lia.system.modules.message;


import com.lia.system.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMessageMapper {

    /**
     * 查询用户1与用户2的聊天记录
     * @param u1Id 用户1ID
     * @param u2Id 用户2ID
     * @return 聊天记录列表
     */
    List<SysMessage> getMsgRecord(Long u1Id, Long u2Id);


    /**
     * 发送消息
     */
    int sendMessage(SysMessage message);

    /**
     * 未读聊天记录数量
     */
    List<SysMessage> getNoReadCount(Long userId);

    /**
     * 最后一条聊天记录
     */
    List<SysMessage> getLastMsg(Long u1Id, List<Long> userIds);

    /**
     * 将用户1与用户2的聊天记录都标记为已读
     */
    int readMessage(Long sendBy, Long sendTo);

}
