package com.lia.system.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
    @ExcelIgnore
    private Integer companyId;

    /**
     * 企业名称
     */
    @ExcelProperty("企业名称")
    @ColumnWidth(20)
    private String name;

    /**
     * 负责人联系电话
     */
    @ExcelProperty("联系方式")
    @ColumnWidth(15)
    private String phone;

    /**
     * 负责人
     */
    @ExcelProperty("负责人")
    private String principal;

    /**
     * 企业地址
     */
    @ExcelProperty("地址")
    @ColumnWidth(30)
    private String address;

    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    @ColumnWidth(20)
    private String email;

    /**
     * 创建人
     */
    @ExcelIgnore
    private Long creater;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createTime;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    @ColumnWidth(20)
    private String remark;

}
