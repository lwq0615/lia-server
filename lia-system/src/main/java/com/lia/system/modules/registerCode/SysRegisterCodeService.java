
package com.lia.system.modules.registerCode;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysRegisterCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SysRegisterCodeService extends BaseService<SysRegisterCode> {

    @Autowired
    private SysRegisterCodeMapper sysRegisterCodeMapper;


}                                       

    