package com.lia.system.modules.router;


import com.lia.system.entity.SysRouter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRouterMapper {


    /**
     * 查询路由列表
     * @param router 参数
     * @return 列表数据
     */
    List<SysRouter> findSysRouter(SysRouter router);

    /**
     * 获取角色可访问的路由
     * @param roleId
     * @return
     */
    List<SysRouter> findRouterByRoleId(Integer roleId);



    /**
     * 新增路由
     * @param router
     * @return
     */
    int addSysRouter(SysRouter router);

    /**
     * 编辑路由
     * @param router
     * @return
     */
    int editSysRouter(SysRouter router);


    /**
     * 批量删除路由
     * @param routerIds 用户的id列表
     * @return 删除成功的数量
     */
    int deleteRouters(List<Integer> routerIds);


    /**
     * 根据角色ID获取路由id列表
     * @param roleId
     * @return
     */
    List<Integer> findIdsbyRoleId(Integer roleId);
}
