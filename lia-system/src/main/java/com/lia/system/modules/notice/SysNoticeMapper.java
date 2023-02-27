package com.lia.system.modules.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {


    /**
     * 将公告与角色绑定到公告角色关系表
     */
    int publishToRole(Long noticeId, List<Integer> roleIds);


    /**
     * 上传公告携带附件
     */
    int uploadNoticeFile(Long noticeId, List<Long> fileIds);


}
