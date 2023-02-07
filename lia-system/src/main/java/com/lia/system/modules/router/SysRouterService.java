package com.lia.system.modules.router;


import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysRouter;
import com.lia.system.modules.role.SysRoleService;
import com.lia.system.result.SysResult;
import com.lia.system.result.exception.HttpException;
import com.lia.system.security.LoginUser;
import com.lia.system.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysRouterService {


    @Autowired
    private SysRouterMapper sysRouterMapper;
    @Autowired
    private SysRoleService sysRoleService;


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
        if(roleId == null){
            throw new HttpException("缺少参数roleId");
        }
        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role = sysRoleService.findSysRole(role).get(0);
        List<SysRouter> routers = sysRouterMapper.findRouterByRoleId(roleId);
        // findRouterByRoleId查询的用户可访问路由表中不包括根节点
        // 如果该角色是以根节点作为根，则需要另外查询根节点路由信息
        if(role.getRootRouterId().equals(SysRouter.ROOT_ROUTER_ID)){
            SysRouter root = new SysRouter();
            root.setRouterId(SysRouter.ROOT_ROUTER_ID);
            root = sysRouterMapper.findSysRouter(root).get(0);
            routers.add(root);
        }
        ArrayList<SysRouter> tree = SysRouter.asTreeWithRoot(routers, role.getRootRouterId());
        if(tree == null || tree.size() == 0 || tree.get(0).getChildren() == null){
            return new ArrayList<>();
        }else{
            return tree.get(0).getChildren();
        }
    }



    /**
     * 查询路由树
     */
    public List<SysRouter> getRouterTree(){
        List<SysRouter> sysRouters = sysRouterMapper.findSysRouter(new SysRouter());
        return SysRouter.asTreeWithRoot(sysRouters, SysRouter.ROOT_ROUTER_ID);
    }



    /**
     * 新增或编辑用户
     * @param router
     * @return
     */
    public int saveRouter(SysRouter router){
        if(router.getPath() == null || router.getPath().equals("")){
            throw new HttpException("缺少参数path");
        }
        if(router.getLabel() == null || router.getLabel().equals("")){
            throw new HttpException("缺少参数label");
        }
        if(router.getPath().contains("/")){
            throw new HttpException("路由地址不能包含'/'");
        }
        if(router.getRouterId() != null && router.getRouterId() == router.getParent()){
            throw new HttpException(SysResult.ROUTER_PARENT_OWN);
        }
        if(!StringUtils.isEmpty(router.getElement())){
            if(!router.getElement().substring(0, 1).equals("/")){
                router.setElement("/"+router.getElement());
            }
            if(router.getElement().substring(router.getElement().length()-1).equals("/")){
                router.setElement(router.getElement().substring(0, router.getElement().length()-1));
            }
        }
        int success = 0;
        try{
            if(router.getRouterId() == null){
                // 新增的用户createBy为当前用户
                router.setCreateBy(LoginUser.getLoginUserId());
                success = sysRouterMapper.addSysRouter(router);
            }else{
                success = sysRouterMapper.editSysRouter(router);
            }
        }catch (DuplicateKeyException e){
            String[] split = e.getCause().getMessage().split(" ");
            String replace = split[split.length - 1].replace("'", "");
            String name = replace.split("\\.")[1].split("-")[1];
            switch (name) {
                case "element":
                    throw new HttpException(SysResult.ROUTER_ELEMENT_EXISTED);
                case "parent,path":
                    throw new HttpException(SysResult.ROUTER_PATH_EXISTED);
            }
        }
        return success;
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
