package com.lia.system.modules.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lia.system.crud.*;
import lombok.Data;


@Data
public class SysParam {


    @TableId(type = IdType.AUTO)
    @TableField("param_id")
    private Integer paramId;

    @TableField("name")
    @Required
    private String name;

    @TableField("value")
    private Object value;

    @TableField("mean")
    @Like
    private String mean;

    @TableField("create_by")
    @CreateBy
    private Long createBy;

    @TableField("create_time")
    @CreateTime
    private String createTime;

    @TableField("remark")
    @Like
    private String remark;


}
