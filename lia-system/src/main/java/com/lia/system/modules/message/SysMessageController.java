package com.lia.system.modules.message;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.exception.HttpException;
import com.lia.system.modules.user.SysUser;
import com.lia.system.modules.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if(sysMessage.getSendBy() == null){
            throw new HttpException(400, "缺少参数sendBy");
        }
        if(sysMessage.getSendTo() == null){
            throw new HttpException(400, "缺少参数sendTo");
        }
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
    public HashMap<Long, HashMap> getLastMsg(@RequestBody List<Long> userIds){
        if(userIds == null || userIds.size() < 1){
            return new HashMap<>();
        }
        return sysMessageService.getLastMsg(userIds);

    }


}
