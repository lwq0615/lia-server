package com.lia.system.modules.notice;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysNoticeService extends BaseService<SysNotice> {

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    /**
     * 发布公告
     */
    @Override
    public int insert(SysNotice entity) {
        super.insert(entity, true);
        return sysNoticeMapper.publishToRole(entity.getId(), entity.getPublishTo());
    }
}

