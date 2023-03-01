package com.lia.system.modules.notice;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysFile;
import com.lia.system.entity.SysNotice;
import com.lia.system.entity.SysRole;
import com.lia.system.modules.file.SysFileService;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysNoticeService extends BaseService<SysNotice> {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private SysFileService sysFileService;


    /**
     * 分页查询
     */
    public List<SysNotice> selectList() {
        return sysNoticeMapper.getNoticePage(LoginUser.getUserRoleId());
    }

    /**
     * 发布公告
     */
    @Override
    public int insert(SysNotice entity) {
        if(entity.getPublishTo() == null || entity.getPublishTo().size() < 1){
            throw new HttpException("缺少参数publishTo");
        }
        int success = super.insert(entity, true);
        if(entity.getFiles() != null && entity.getFiles().size() > 0){
            sysNoticeMapper.uploadNoticeFile(entity.getId(), entity.getFiles());
        }
        sysNoticeMapper.publishToRole(entity.getId(), entity.getPublishTo());
        return success;
    }

    @Override
    public int update(SysNotice entity) {
        if(entity.getPublishTo() == null || entity.getPublishTo().size() < 1){
            throw new HttpException("缺少参数publishTo");
        }
        int success = super.update(entity);
        List<Long> filesOfNotice = sysNoticeMapper.getFilesOfNotice(entity.getId()).stream().map(SysFile::getFileId).collect(Collectors.toList());
        // 要删除的附件
        List<Long> deleteFiles = new ArrayList<>();
        for (Long sysFileId : filesOfNotice) {
            if(entity.getFiles() == null || !entity.getFiles().contains(sysFileId)){
                deleteFiles.add(sysFileId);
            }
        }
        if(deleteFiles.size() > 0){
            sysFileService.deleteFiles(deleteFiles);
        }
        // 新上传的附件
        if(entity.getFiles() != null && entity.getFiles().size() > 0){
            List<Long> newFiles = new ArrayList<>();
            for (Long fileId : entity.getFiles()) {
                if(!filesOfNotice.contains(fileId)){
                    newFiles.add(fileId);
                }
            }
            if(newFiles.size() > 0){
                sysNoticeMapper.uploadNoticeFile(entity.getId(), newFiles);
            }
        }
        sysNoticeMapper.deleteRoles(entity.getId());
        sysNoticeMapper.publishToRole(entity.getId(), entity.getPublishTo());
        return success;
    }

    /**
     * 获取公告相关附件
     * @param noticeId 公告id
     * @return 附件列表
     */
    public List<SysFile> getFilesOfNotice(Long noticeId){
        return sysNoticeMapper.getFilesOfNotice(noticeId);
    }

    /**
     * 获取公告相关角色
     * @param noticeId 公告id
     */
    public List<SysRole> getRolesOfNotice(Long noticeId){
        return sysNoticeMapper.getRolesOfNotice(noticeId);
    }


    @Override
    public int deleteByIds(List ids) {
        return sysNoticeMapper.deleteByIds(ids);
    }
}

