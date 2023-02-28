package com.lia.system.modules.notice;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysFile;
import com.lia.system.entity.SysNotice;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysNoticeService extends BaseService<SysNotice> {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;


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
        super.insert(entity, true);
        if(entity.getFiles() != null && entity.getFiles().size() > 0){
            sysNoticeMapper.uploadNoticeFile(entity.getId(), entity.getFiles());
        }
        return sysNoticeMapper.publishToRole(entity.getId(), entity.getPublishTo());
    }

    /**
     * 获取公告相关附件
     * @param noticeId 公告id
     * @return 附件列表
     */
    public List<SysFile> getFilesOfNotice(Long noticeId){
        return sysNoticeMapper.getFilesOfNotice(noticeId);
    }

}

