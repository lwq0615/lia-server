package com.lia.system.modules.router;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRouter {

    private Integer routerId;
    private String label;
    private String path;
    private String element;
    private Integer parent;
    private Integer index;
    private String icon;
    private Long createBy;
    private String createTime;
    private String remark;
    private List<SysRouter> children;


    /**
     * 将路由列表转化为树形结构并指定根节点
     *
     * @param routers 路由列表
     * @param rootId  根节点路由ID
     * @return
     */
    public static ArrayList<SysRouter> asTreeWithRoot(List<SysRouter> routers, Integer rootId) {
        // 用户根路由不存在
        if(rootId == null){
            return null;
        }
        ArrayList<SysRouter> root = new ArrayList<>();
        //将返回结果转换为树形结构
        ArrayList<SysRouter> nullRouters = new ArrayList<>();
        for (SysRouter child : routers) {
            // TODO 最后在进行插入index为null的路由，使这些路由展示在最后面
            if (child.getIndex() == null) {
                nullRouters.add(child);
                continue;
            }
            //如果路由没有父路由，则为最顶层路由，放入res中
            if (child.getRouterId() == rootId) {
                root.add(child);
            }
            //寻找路由的父路由，将自己存入父路由的childred列表中
            else {
                for (SysRouter parent : routers) {
                    if (child.getParent() == parent.getRouterId()) {
                        if (parent.getChildren() == null) {
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(child);
                        break;
                    }
                }
            }
        }
        // 将index为null的路由插入
        for (SysRouter child : nullRouters) {
            //如果路由没有父路由，则为最顶层路由，放入res中
            if (child.getRouterId() == rootId) {
                root.add(child);
            }
            //寻找路由的父路由，将自己存入父路由的childred列表中
            else {
                for (SysRouter parent : routers) {
                    if (child.getParent() == parent.getRouterId()) {
                        if (parent.getChildren() == null) {
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(child);
                        break;
                    }
                }
            }
        }
        return root;
    }

}
