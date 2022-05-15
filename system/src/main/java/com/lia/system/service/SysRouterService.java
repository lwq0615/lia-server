package com.lia.system.service;


import com.lia.system.entity.SysRouter;
import com.lia.system.mapper.SysRouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysRouterService {


    @Autowired
    private SysRouterMapper sysRouterMapper;


    public List<SysRouter> findRouterByRoleId(Integer roleId){
        List<SysRouter> res = new ArrayList<>();
        //获取当前角色可访问的所有路由
        List<SysRouter> routers = sysRouterMapper.findRouterByRoleId(roleId);
        //将返回结果转换为树形结构
        for (SysRouter child : routers) {
            //如果路由没有父路由，则为最顶层路由，放入res中
            if(child.getParent() == null){
                if(child.getIndex() == null){
                    res.add(child);
                }else{
                    //找到合适的位置插入
                    for(int i = res.size();i>=0;i--){
                        if(i == 0){
                            res.add(child);
                            break;
                        }else if(res.get(i-1).getIndex() < child.getIndex()
                                && res.get(i-1).getIndex() != null){
                            res.add(i,child);
                            break;
                        }
                    }
                }
            }
            //寻找路由的父路由，将自己存入父路由的childred列表中
            else{
                for (SysRouter parent : routers) {
                    if(child.getParent() == parent.getRouterId()){
                        if(parent.getChildren() == null){
                            parent.setChildren(new ArrayList<>());
                        }
                        //如果路由的index不为null，则对其进行排序
                        if(child.getIndex() == null){
                            parent.getChildren().add(child);
                        }else{
                            //找到合适的位置插入
                            for(int i = parent.getChildren().size();i>=0;i--){
                                if(i == 0){
                                    parent.getChildren().add(child);
                                    break;
                                }else if(parent.getChildren().get(i-1).getIndex() < child.getIndex()
                                        && parent.getChildren().get(i-1).getIndex() != null){
                                    parent.getChildren().add(i,child);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return res;
    }

}
