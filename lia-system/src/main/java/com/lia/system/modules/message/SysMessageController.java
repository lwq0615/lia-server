package com.lia.system.modules.message;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysMessage;
import com.lia.system.entity.SysUser;
import com.lia.system.modules.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/system/message")
public class SysMessageController {

    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 分页查询消息记录
     * @param sysMessage 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    public PageInfo<SysMessage> getMsgRecord(@RequestBody SysMessage sysMessage, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysMessageService.getMsgRecord(sysMessage.getSendBy(), sysMessage.getSendTo()));
    }


    /**
     * 获取可以聊天的用户（只有相同企业的用户可以相互聊天）
     */
    @GetMapping("/personList")
    public List<SysUser> personList(){
        return sysUserService.personList();
    }

    /**
     * 未读聊天记录数量
     */
    @GetMapping("/getNoReadCount")
    public HashMap<Long, Integer> getNoReadCount(){
        return sysMessageService.getNoReadCount();
    }

    /**
     * 最后一条聊天记录
     */
    @PostMapping("/getLastMsg")
    public HashMap<Long, String> getLastMsg(@RequestBody List<Long> userIds){
        return sysMessageService.getLastMsg(userIds);
    }

    /**
     * 将用户1发送给用户2的聊天记录都标记为已读
     */
    @PostMapping("/readMessage")
    public int readMessage(@RequestBody SysMessage message){
        return sysMessageService.readMessage(message.getSendBy(), message.getSendTo());
    }


}
