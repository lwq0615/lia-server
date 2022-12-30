package com.lia.system.modules.auth;


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
