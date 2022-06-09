package com.lia.system.controller;


import com.lia.system.entity.SysRouter;
import com.lia.system.entity.SysUser;
import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.service.SysRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<SysRouter> getRouterofRole(Integer roleId)  {
        if(roleId == null){
            throw new HttpException(400, "缺少参数roleId");
        }
        return sysRouterService.findRouterByRoleId(roleId);
    }


    /**
     * 查询路由树
     */
    @GetMapping("/getRouterTree")
    @PreAuthorize("hasAuthority('system:router:getRouterTree')")
    public List<SysRouter> getSysUserPage(){
        return sysRouterService.getRouterTree();
    }



    /**
     * 新增和编辑用户
     * @param router 路由数据，每条数据如果有routerId则为修改，routerId为null则为新增
     * @return 操作成功的数量
     */
    @PostMapping("/saveRouter")
    @PreAuthorize("hasAuthority('system:router:saveRouter')")
    public String saveUser(@RequestBody SysRouter router){
        if(router.getPath() == null || router.getPath().equals("")){
            throw new HttpException(400,"缺少参数path");
        }
        if(router.getLabel() == null || router.getLabel().equals("")){
            throw new HttpException(400,"缺少参数label");
        }
        if(router.getPath().contains("/")){
            throw new HttpException(400,"参数path不可包含字符'/'");
        }
        if(router.getRouterId() != null && router.getRouterId() == router.getParent()){
            throw new HttpException(400,"父路由不可以是自己");
        }
        // 新增的用户createBy为当前用户
        if(router.getRouterId() == null){
            SysUser loginSysUser = LoginUser.getLoginUser();
            router.setCreateBy(loginSysUser.getUserId());
        }
        return sysRouterService.saveRouter(router);
    }


    /**
     * 批量删除炉头
     * @param routerIds 用户的id列表
     * @return 删除成功的数量
     */
    @PostMapping("/deleteRouters")
    @PreAuthorize("hasAuthority('system:router:deleteRouters')")
    public int deleteRouters(@RequestBody List<Integer> routerIds){
        return sysRouterService.deleteRouters(routerIds);
    }


    @GetMapping("/getRouterById")
    @PreAuthorize("hasAuthority('system:router:getRouterById')")
    public SysRouter getRouterById(Integer routerId){
        SysRouter router = new SysRouter();
        router.setRouterId(routerId);
        return sysRouterService.getRouters(router).get(0);
    }

}
