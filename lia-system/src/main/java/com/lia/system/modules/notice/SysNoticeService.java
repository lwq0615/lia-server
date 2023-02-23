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


}

