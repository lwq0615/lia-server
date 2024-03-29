
package com.lia.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.lia.system.crud.anno.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_register_code")
public class SysRegisterCode {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    /**
     * 注册码
     */
    @TableField("`code`")
    @Required
    @Like
    @ExcelProperty("注册码")
    @ColumnWidth(25)
    private String code;

    /**
     * 该注册码可激活的角色
     */
    @TableField("`role_id`")
    @Required
    @ExcelIgnore
    private Integer roleId;

    /**
     * 角色名称
     */
    @ExcelProperty("角色")
    @ColumnWidth(15)
    @Pass
    @TableField(select = false)
    private String roleName;

    /**
     * 有效期
     */
    @ExcelIgnore
    @TableField("`expire_time`")
    private Long expireTime;

    /**
     * 使用该注册码的用户ID
     */
    @TableField("`use_by`")
    @ExcelIgnore
    private Long useBy;

    /**
     * 是否已使用
     */
    @Pass
    @ExcelIgnore
    @TableField(select = false)
    private Boolean used;

    /**
     * 是否已过期
     */
    @Pass
    @ExcelIgnore
    @TableField(select = false)
    private Boolean expired;

    /**
     * 注册码被使用的时间
     */
    @Between
    @TableField("`use_time`")
    @ExcelProperty("使用时间")
    @ColumnWidth(20)
    private String useTime;

    /**
     * 创建人
     */
    @TableField("`creater`")
    @Creater
    @ExcelIgnore
    private Long creater;

    /**
     * 创建时间
     */
    @CreateTime
    @TableField("`create_time`")
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private String createTime;

}                                   
    