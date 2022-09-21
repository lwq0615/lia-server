package com.lia.system.modules.company;

import com.lia.system.modules.dict.SysDict;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysCompanyMapper {


    /**
     * 条件查询
     * @param sysCompany 查询参数
     * @return 查询结果列表
     */
    List<SysCompany> findSysCompany(SysCompany sysCompany);


    /**
     * 新增
     * @param sysCompany
     * @return
     */
    int addSysCompany(SysCompany sysCompany);


    /**
     * 编辑
     * @param sysCompany
     * @return
     */
    int editSysCompany(SysCompany sysCompany);


    /**
     * 批量删除
     * @param sysCompanyIds
     * @return
     */
    int deleteSysCompanys(List<Integer> sysCompanyIds);



    /**
     * 获取企业字典表
     */
    List<SysDict> getSysCompanyDict();

}