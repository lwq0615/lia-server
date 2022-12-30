package com.lia.system.modules.company;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统企业
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysCompany {

    /**
     * 企业ID
     */
    private Integer companyId;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 负责人联系电话
     */
    private String phone;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 邮箱
     */
    private String email;

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
