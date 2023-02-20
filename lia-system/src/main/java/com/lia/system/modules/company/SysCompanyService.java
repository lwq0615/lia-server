package com.lia.system.modules.company;

import com.lia.system.entity.SysCompany;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.entity.SysDictData;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class SysCompanyService {


    @Autowired
    private SysCompanyMapper sysCompanyMapper;


    /**
     * 分页查询
     * @param sysCompany
     * @return
     */
    public List<SysCompany> findSysCompany(SysCompany sysCompany) {
        return sysCompanyMapper.findSysCompany(sysCompany);
    }


    /**
     * 新增或编辑
     * @param sysCompany
     * @return
     */
    public int saveSysCompany(SysCompany sysCompany) {
        if(sysCompany.getName() == null || sysCompany.getName().equals("")){
            throw new HttpException("缺少参数name");
        }
        if(sysCompany.getPhone() == null || sysCompany.getPhone().equals("")){
            throw new HttpException("缺少参数phone");
        }
        if(sysCompany.getPrincipal() == null || sysCompany.getPrincipal().equals("")){
            throw new HttpException("缺少参数principal");
        }
        // 校验手机号
        if(!StrUtils.isEmpty(sysCompany.getPhone())){
            String regex = "^[1]([3-9])[0-9]{9}$";
            if(sysCompany.getPhone().length() != 11 || !Pattern.matches(regex, sysCompany.getPhone())){
                throw new HttpException("手机号格式错误");
            }
        }
        int success;
        try {
            if (sysCompany.getCompanyId() == null) {
                // 新增
                sysCompany.setCreateBy(LoginUser.getLoginUserId());
                success = sysCompanyMapper.addSysCompany(sysCompany);
            } else {
                // 编辑
                success = sysCompanyMapper.editSysCompany(sysCompany);
            }
        } catch (DuplicateKeyException e) {
            throw new HttpException(SysResult.COMPANY_NAME_EXISTED);
        }
        return success;
    }


    /**
     * 批量删除
     * @param sysCompanyIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysCompanys(List<Integer> sysCompanyIds) {
        // 不允许删除开发者所在企业
        if(sysCompanyIds.contains(1)){
            sysCompanyIds.remove(sysCompanyIds.indexOf(1));
        }
        if (sysCompanyIds.size() == 0) {
            return 0;
        }
        return sysCompanyMapper.deleteSysCompanys(sysCompanyIds);
    }

    /**
     * 获取企业字典表
     */
    public List<SysDictData> getSysCompanyDict(){
        return sysCompanyMapper.getSysCompanyDict();
    }

}
