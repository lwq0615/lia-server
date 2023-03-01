package com.lia.system.modules.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lia.system.entity.SysFile;
import com.lia.system.entity.SysNotice;
import com.lia.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {


    /**
     * 根据角色id查询公告列表
     */
    List<SysNotice> getNoticePage(Integer roleId);


    /**
     * 将公告与角色绑定到公告角色关系表
     */
    int publishToRole(Long noticeId, List<Integer> roleIds);


    /**
     * 上传公告携带附件
     */
    int uploadNoticeFile(Long noticeId, List<Long> fileIds);


    /**
     * 根据公告id获取相关的文件
     */
    List<SysFile> getFilesOfNotice(Long noticeId);


    /**
     * 根据公告id获取相关的文件
     */
    List<SysRole> getRolesOfNotice(Long noticeId);


    /**
     * 删除公告
     */
    int deleteByIds(List<Long> ids);


    /**
     * 删除公告关联的角色
     */
    int deleteRoles(Long noticeId);


}
