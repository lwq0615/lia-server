package com.lia.system.modules.company;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysCompany;
import com.lia.system.entity.SysDictData;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/company")
public class SysCompanyController {


    @Autowired
    private SysCompanyService sysCompanyService;


    /**
     * 分页查询
     * @param sysCompany 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:company:getPage')")
    public PageInfo<SysCompany> getSysCompanyPage(@RequestBody SysCompany sysCompany, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysCompanyService.findSysCompany(sysCompany));
    }

    /**
     * 新增和编辑
     * 自增主键为空时为新增，否则为编辑
     * @param sysCompany 参数数据
     * @return 操作成功的数量
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:company:save')")
    public int saveSysCompany(@RequestBody SysCompany sysCompany){
        return sysCompanyService.saveSysCompany(sysCompany);
    }



    /**
     * 批量删除
     * @param sysCompanyIds 要删除的数据id列表
     * @return 删除成功的数量
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('system:company:delete')")
    public int delete(@RequestBody List<Integer> sysCompanyIds){
        return sysCompanyService.deleteSysCompanys(sysCompanyIds);
    }


    /**
     * 获取企业字典表
     */
    @GetMapping("/sysCompanyDict")
    @PreAuthorize("hasAuthority('system:company:sysCompanyDict')")
    public List<SysDictData> sysCompanyDict(){
        return sysCompanyService.getSysCompanyDict();
    }


}