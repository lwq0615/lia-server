package com.lia.system.modules.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysMessage {

    /**
     * 消息ID
     */
    private Long msgId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读（0：未读，1：已读）
     */
    private Character read;

    /**
     * 消息类型（0：普通消息，1：图片）
     */
    private Character type;

    /**
     * 发送人
     */
    private Long sendBy;

    /**
     * 接收人
     */
    private Long sendTo;

    /**
     * 发送时间
     */
    private String sendTime;


    /**
     * 是否已读set方法
     * read值只能是'0' || '1'
     */
    public void setRead(Character read) {
        if(!read.equals('0') && !read.equals('1')){
            read = '0';
        }
        this.read = read;
    }

    /**
     * 消息类型set方法
     * type值只能是'0' || '1'
     */
    public void setType(Character type) {
        if(!type.equals('0') && !type.equals('1')){
            type = '0';
        }
        this.type = type;
    }
}
