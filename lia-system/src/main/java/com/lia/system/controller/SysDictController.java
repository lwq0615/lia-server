package com.lia.system.controller;


import com.lia.system.entity.SysDict;
import com.lia.system.entity.SysRouter;
import com.lia.system.exception.HttpException;
import com.lia.system.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/system/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;


    /**
     * 获取sys_dict字典表内字典
     * @param type type值作为查询条件
     */
    @GetMapping("/getSysDict")
    @PreAuthorize("hasAuthority('system:dict:getSysDict')")
    public List<SysDict> getSysDict(String type){
        if(type == null || type.equals("")){
            throw new HttpException(400, "缺少参数type");
        }
        return sysDictService.getSysDict(type);
    }


    /**
     * 获取角色字典表
     */
    @GetMapping("/sysRoleDict")
    @PreAuthorize("hasAuthority('system:dict:sysRoleDict')")
    public List<SysDict> sysRoleDict(){
        return sysDictService.getSysRoleDict();
    }


    /**
     * 获取用户字典表
     */
    @GetMapping("/sysUserDict")
    @PreAuthorize("hasAuthority('system:dict:sysUserDict')")
    public List<SysDict> sysUserDict(){
        return sysDictService.getSysUserDict();
    }


    /**
     * 获取路由树形结构字典表
     */
    @GetMapping("/sysRouterDict")
    @PreAuthorize("hasAuthority('system:dict:sysRouterDict')")
    public List<SysRouter> sysRouterDict(){
        return sysDictService.getSysRouterDict();
    }

}
