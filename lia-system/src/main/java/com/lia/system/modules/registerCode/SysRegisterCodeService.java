
package com.lia.system.modules.registerCode;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysRegisterCode;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.DateUtils;
import com.lia.system.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
     * 编辑注册码信息
     */
    @Override
    public String save(SysRegisterCode registerCode){
        return super.save(registerCode);
    }


    /**
     * 批量生成注册码
     */
    public List<SysRegisterCode> create(Integer roleId, Integer count) {
        if (roleId == null) {
            throw new HttpException(400, "缺少参数roleId");
        }
        if (count == null) {
            throw new HttpException(400, "缺少参数count");
        }
        if (count < 1) {
            return new ArrayList<>();
        }
        List<SysRegisterCode> registerCodes = new ArrayList<>();
        int errorCount = 0;
        String datetime = DateUtils.mysqlDatetime(new Date());
        while (count > 0) {
            if(errorCount > 3){
                throw new HttpException(500, "服务端生成注册码失败");
            }
            registerCodes = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                SysRegisterCode registerCode = new SysRegisterCode();
                registerCode.setCreateBy(LoginUser.getLoginUserId())
                        .setRoleId(roleId)
                        .setCode(StringUtils.ramdomCode(20))
                        .setCreateTime(datetime);
                registerCodes.add(registerCode);
            }
            try{
                count -= sysRegisterCodeMapper.createRegisterCode(registerCodes);
            }catch (DuplicateKeyException e){
                errorCount++;
            }
        }
        return registerCodes;
    }


}                                       

    