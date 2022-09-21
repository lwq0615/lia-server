package com.lia.system.modules.role;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.exception.HttpException;
import com.lia.system.modules.dict.SysDict;
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
    public String saveRole(@RequestBody SysRole role){
        if(role.getName() == null || role.getName().equals("")){
            throw new HttpException(400,"缺少参数name");
        }
        if(role.getKey() == null || role.getKey().equals("")){
            throw new HttpException(400,"缺少参数key");
        }
        if(role.getRootRouterId() == null){
            throw new HttpException(400,"缺少参数rootRouterId");
        }
        if(role.getCompanyId() == null){
            throw new HttpException(400,"缺少参数companyId");
        }
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
    public List<SysDict> sysCompanyDict(Integer companyId){
        return sysRoleService.getRoleOfCompanyDict(companyId);
    }


    /**
     * 获取角色字典表
     */
    @GetMapping("/sysRoleDict")
    @PreAuthorize("hasAuthority('system:role:sysRoleDict')")
    public List<SysDict> sysRoleDict(){
        return sysRoleService.getSysRoleDict();
    }


}
