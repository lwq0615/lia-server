package com.lia.system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统路由
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysRouter {


    /**
     * 跟路由的ID
     */
    public static final Integer ROOT_ROUTER_ID = 1;

    /**
     * 路由ID
     */
    private Integer routerId;

    /**
     * 路由标签
     */
    private String label;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String element;

    /**
     * 上级路由（外键）
     */
    private Integer parent;

    /**
     * 排序
     */
    private Integer index;

    /**
     * 按钮样式
     */
    private String icon;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子路由
     */
    private List<SysRouter> children;


    /**
     * 将路由列表转化为树形结构并指定根节点
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
            // 最后在进行插入index为null的路由，使这些路由展示在最后面
            if (child.getIndex() == null) {
                nullRouters.add(child);
                continue;
            }
            //如果路由没有父路由，则为最顶层路由，放入res中
            if (child.getRouterId().equals(rootId)) {
                root.add(child);
            }
            //寻找路由的父路由，将自己存入父路由的childred列表中
            else {
                for (SysRouter parent : routers) {
                    if (child.getParent().equals(parent.getRouterId())) {
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
            if (child.getRouterId().equals(rootId)) {
                root.add(child);
            }
            //寻找路由的父路由，将自己存入父路由的childred列表中
            else {
                for (SysRouter parent : routers) {
                    if (child.getParent().equals(parent.getRouterId())) {
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
