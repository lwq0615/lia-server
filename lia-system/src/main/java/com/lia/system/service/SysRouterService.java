package com.lia.system.service;


import com.lia.system.entity.SysRouter;
import com.lia.system.mapper.SysRouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysRouterService {


    @Autowired
    private SysRouterMapper sysRouterMapper;


    /**
     * 查询路由列表
     */
    public List<SysRouter> getRouters(SysRouter router){
        return sysRouterMapper.findSysRouter(router);
    }


    /**
     * 获取角色可访问的路由，并转化为树形结构
     * @param roleId 角色ID
     */
    public List<SysRouter> findRouterByRoleId(Integer roleId){
        List<SysRouter> routers = sysRouterMapper.findRouterByRoleId(roleId);
        return SysRouter.asTree(routers);
    }



    /**
     * 查询路由树
     */
    public List<SysRouter> getRouterTree(){
        return SysRouter.asTree(sysRouterMapper.findSysRouter(new SysRouter()));
    }



    /**
     * 新增或编辑用户
     * @param router
     * @return
     */
    public String saveRouter(SysRouter router){
        int success;
        try{
            if(router.getRouterId() == null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = dateFormat.format(new Date());
                router.setCreateTime(date);
                success = sysRouterMapper.addSysRouter(router);
            }else{
                success = sysRouterMapper.editSysRouter(router);
            }
        }catch (DuplicateKeyException e){
            return "重复的路径";
        }
        return success > 0 ? "success" : "error";
    }



    /**
     * 批量删除路由
     * @param routerIds 用户的id列表
     * @return 删除成功的数量
     */
    public int deleteRouters(List<Integer> routerIds){
        if(routerIds.size() == 0){
            return 0;
        }
        return sysRouterMapper.deleteRouters(routerIds);
    }


}
