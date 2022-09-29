package com.lia.system.modules.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysMessage {

    private Long msgId;
    private String content;
    private Character read = '0';
    private Long sendBy;
    private Long sendTo;
    private String sendTime;

}
