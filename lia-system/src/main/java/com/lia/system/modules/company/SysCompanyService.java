package com.lia.system.modules.company;

import com.lia.system.exception.HttpException;
import com.lia.system.modules.dictData.SysDictData;
import com.lia.system.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     *
     * @param sysCompany
     * @return
     */
    public String saveSysCompany(SysCompany sysCompany) {
        if(sysCompany.getName() == null || sysCompany.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        if(sysCompany.getPhone() == null || sysCompany.getPhone().equals("")){
            throw new HttpException(400,"缺少参数phone");
        }
        if(sysCompany.getPrincipal() == null || sysCompany.getPrincipal().equals("")){
            throw new HttpException(400,"缺少参数principal");
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
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            return name + "重复";
        }
        return success > 0 ? "success" : "error";
    }


    /**
     * 批量删除
     *
     * @param sysCompanyIds id列表
     * @return 删除成功的数量
     */
    public int deleteSysCompanys(List<Integer> sysCompanyIds) {
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
