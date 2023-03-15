
package com.lia.system.modules.registerCode;

import com.lia.system.crud.BaseService;
import com.lia.system.entity.SysRegisterCode;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.modules.role.SysRoleService;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.DateUtils;
import com.lia.system.utils.ExcelUtils;
import com.lia.system.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysRegisterCodeService extends BaseService<SysRegisterCode> {

    @Autowired
    private SysRegisterCodeMapper sysRegisterCodeMapper;
    @Autowired
    private SysRoleService sysRoleService;


    @Override
    public List<SysRegisterCode> selectList(SysRegisterCode entity, boolean desc) {
        return sysRegisterCodeMapper.getPage(entity);
    }

    /**
     * 编辑注册码角色信息
     */
    public int editCodeRole(Long id, Integer roleId){
        if(id == null){
            throw new HttpException("缺少参数id");
        }
        if(roleId == null){
            throw new HttpException("缺少参数roleId");
        }
        SysRegisterCode code = sysRegisterCodeMapper.selectById(id);
        if(code.getUseBy() != null){
            throw new HttpException(SysResult.REGISTER_USED);
        }
        SysRegisterCode newCode = new SysRegisterCode().setRoleId(roleId).setId(id);
        return sysRegisterCodeMapper.updateById(newCode);
    }


    /**
     * 批量生成注册码
     */
    public List<SysRegisterCode> create(Integer roleId, Integer count, Long expireTime) {
        if (roleId == null) {
            throw new HttpException("缺少参数roleId");
        }
        if (count == null) {
            throw new HttpException("缺少参数count");
        }
        if(roleId.equals(SysRole.COMMON_ROLE_ID)){
            throw new HttpException(SysResult.COMMON_NOT_CREATE);
        }
        if (count < 1) {
            return new ArrayList<>();
        }
        List<SysRegisterCode> registerCodes = new ArrayList<>();
        int errorCount = 0;
        String datetime = DateUtils.mysqlDatetime(new Date());
        while (count > 0) {
            if(errorCount > 3){
                throw new HttpException("服务端生成注册码失败");
            }
            registerCodes = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                SysRegisterCode registerCode = new SysRegisterCode();
                registerCode.setCreater(LoginUser.getLoginUserId())
                        .setRoleId(roleId)
                        .setCode(StrUtils.ramdomCode(20))
                        .setExpireTime(expireTime)
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


    /**
     * 导出excel
     * @param response
     */
    public void excel(HttpServletResponse response, SysRegisterCode code) {
        List<SysRegisterCode> codes = sysRegisterCodeMapper.getPage(code);
        List<SysRole> roles = sysRoleService.findSysRole(new SysRole());
        Map<Integer, String> roleMap = roles.stream().collect(Collectors.toMap(SysRole::getRoleId, SysRole::getName));
        for (SysRegisterCode cItem : codes) {
            cItem.setRoleName(roleMap.get(cItem.getRoleId()));
        }
        ExcelUtils.write(response, "注册码", codes, SysRegisterCode.class);
    }


}                                       

    