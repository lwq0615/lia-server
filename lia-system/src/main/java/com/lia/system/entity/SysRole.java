package com.lia.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * 系统角色
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysRole {


    /**
     * 开发者角色ID
     */
    public static final Integer ADMIN_ROLE_ID = 1;

    /**
     * 测试角色ID
     */
    public static final Integer TEST_ROLE_ID = 2;

    /**
     * 普通用户角色ID
     */
    public static final Integer COMMON_ROLE_ID = 3;



    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识符
     */
    private String key;

    /**
     * 角色所属企业（外键）
     */
    private Integer companyId;

    /**
     * 上级角色（外键）
     */
    private Integer superior;

    /**
     * 根路由（外键）
     */
    private Integer rootRouterId;

    /**
     * 创建人
     */
    private Long creater;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色所拥有的权限ID列表
     */
    private List<Integer> auths;

    /**
     * 角色可访问的路由ID列表
     */
    private List<Integer> routers;

}
