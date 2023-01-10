
package com.lia.system.modules.registerCode;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysRegisterCode;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysRegisterCodeService extends BaseService<SysRegisterCode> {

    @Autowired
    private SysRegisterCodeMapper sysRegisterCodeMapper;



    /**
     * 批量生成注册码
     */
    public List<SysRegisterCode> create(Integer roleId, Integer count){
        if(roleId == null){
            throw new HttpException(400, "缺少参数roleId");
        }
        if(count == null){
            throw new HttpException(400, "缺少参数count");
        }
        if(count < 1){
            return new ArrayList<>();
        }
        List<SysRegisterCode> codes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            SysRegisterCode registerCode = new SysRegisterCode();
            registerCode.setCreateBy(LoginUser.getLoginUserId())
                    .setCreateTime(DateUtils.format(new Date(), "YYYY-MM-DD HH:mm:ss"))
                    .setRoleId(roleId)
                    .setCode("i"+i);
            codes.add(registerCode);
        }
        sysRegisterCodeMapper.createRegisterCode(codes);
        return codes;
    }


}                                       

    