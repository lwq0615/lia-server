package com.lia.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lia.system.crud.anno.Creater;
import com.lia.system.crud.anno.Like;
import com.lia.system.crud.anno.Required;
import com.lia.system.crud.anno.UpdateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysParam {

    /**
     * 参数ID
     */
    @TableId(value = "`param_id`", type = IdType.AUTO)
    private Integer paramId;

    /**
     * 参数名
     */
    @TableField("name")
    @Required
    private String name;

    /**
     * 参数值
     */
    @TableField("value")
    private Object value;

    /**
     * 说明
     */
    @TableField("mean")
    @Like
    private String mean;

    /**
     * 创建人
     */
    @TableField("creater")
    @Creater
    private Long creater;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @UpdateTime
    private String createTime;

    /**
     * 备注
     */
    @TableField("remark")
    @Like
    private String remark;


}
