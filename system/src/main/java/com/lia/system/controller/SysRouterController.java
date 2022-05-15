package com.lia.system.controller;


import com.lia.system.exception.HttpException;
import com.lia.system.entity.SysRouter;
import com.lia.system.service.SysRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/router")
public class SysRouterController {

    @Autowired
    private SysRouterService sysRouterService;


    /**
     * 获取角色可访问的路由，并转化为树形结构
     * @param roleId 角色ID
     */
    @GetMapping("/getRouterOfRole")
    @PreAuthorize("hasAuthority('system:router:getRouterOfRole')")
    public List<SysRouter> getRouterofRole(Integer roleId)  {
        if(roleId == null){
            throw new HttpException(400, "缺少参数roleId");
        }
        return sysRouterService.findRouterByRoleId(roleId);
    }

}
