package com.lia.system.mapper;


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

    List<SysRouter> findRouterByRoleId(Integer roleId);
}
