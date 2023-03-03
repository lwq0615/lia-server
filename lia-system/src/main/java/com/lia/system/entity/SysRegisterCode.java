
package com.lia.system.entity;

import com.lia.system.crud.anno.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
    private Long id;

    /**
     * 注册码
     */
    @TableField("`code`")
    @Required
    @Like
    private String code;

    /**
     * 该注册码可激活的角色
     */
    @TableField("`role_id`")
    @Required
    private Integer roleId;

    /**
     * 使用该注册码的用户ID
     */
    @TableField("`use_by`")
    private Long useBy;

    /**
     * 是否已使用
     */
    @Pass
    private Boolean used;

    /**
     * 注册码被使用的时间
     */
    @Between
    @TableField("`use_time`")
    private String useTime;

    /**
     * 创建人
     */
    @TableField("`creater`")
    @Creater
    private Long creater;

    /**
     * 创建时间
     */
    @CreateTime
    @TableField("`create_time`")
    private String createTime;

}                                   
    