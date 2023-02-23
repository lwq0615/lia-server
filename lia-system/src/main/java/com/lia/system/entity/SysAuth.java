package com.lia.system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysAuth {


    /**
     * redis中权限url对应的权限名称key
     */
    public static final String AUTH_URL_NAME = "auth:url:";

    /**
     * 权限ID
     */
    private Integer authId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 标识符
     */
    private String key;

    /**
     * 所属路由
     */
    private Integer routerId;

    /**
     * 权限类型（0：接口，1：组件）
     */
    private Character type;

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

}
