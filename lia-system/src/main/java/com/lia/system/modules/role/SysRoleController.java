package com.lia.system.modules.role;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysDictData;
import com.lia.system.entity.SysRole;
import com.lia.system.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询角色列表
     * @param role 查询参数
     * @param current 当前页码
     * @param size 每页条数
     * @return PageInfo分页信息
     */
    @PostMapping("/getPage")
    @PreAuthorize("hasAuthority('system:role:getPage')")
    public PageInfo<SysRole> getSysRolePage(@RequestBody SysRole role, Integer current, Integer size){
        if(current != null && size != null){
            PageHelper.startPage(current,size);
        }
        return new PageInfo<>(sysRoleService.findSysRole(role));
    }

    /**
     * 新增和编辑
     * @param role 角色数据
     * @return 操作成功的数量
     */
    @PostMapping("/saveRole")
    @PreAuthorize("hasAuthority('system:role:saveRole')")
    public int saveRole(@RequestBody SysRole role){
        return sysRoleService.saveRole(role);
    }



    /**
     * 批量删除
     * @param roleIds 角色的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteRoles")
    @PreAuthorize("hasAuthority('system:role:deleteRoles')")
    public int deleteRoles(@RequestBody List<Integer> roleIds){
        return sysRoleService.deleteRoles(roleIds);
    }


    /**
     * 根据企业ID获取某企业下的角色字典表
     */
    @GetMapping("/getRoleOfCompanyDict")
    @PreAuthorize("hasAuthority('system:role:getRoleOfCompanyDict')")
    public List<SysDictData> sysCompanyDict(Integer companyId){
        return sysRoleService.getRoleOfCompanyDict(companyId);
    }


    /**
     * 获取角色字典表
     */
    @GetMapping("/sysRoleDict")
    public List<SysDictData> sysRoleDict(){
        return sysRoleService.getSysRoleDict();
    }


}
