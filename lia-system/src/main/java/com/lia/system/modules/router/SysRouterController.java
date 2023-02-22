package com.lia.system.modules.router;


import com.lia.system.entity.SysRouter;
import com.lia.system.result.HttpResult;
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
    public int saveUser(@RequestBody SysRouter router){
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


    /**
     * 根据id获取路由
     * @param routerId
     * @return
     */
    @GetMapping("/getRouterById")
    @PreAuthorize("hasAuthority('system:router:getRouterById')")
    public SysRouter getRouterById(Integer routerId){
        SysRouter router = new SysRouter();
        router.setRouterId(routerId);
        return sysRouterService.getRouters(router).get(0);
    }


    /**
     * 路由重新排序
     * @param list
     * @return
     */
    @PostMapping("/reloadIndex")
    @PreAuthorize("hasAuthority('system:router:reloadIndex')")
    public int reloadIndex(@RequestBody List<List<Integer>> list){
        return sysRouterService.reloadIndex(list);
    }

}
