package com.lia.system.modules.dictData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 系统数据字典项
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysDictData {

    /**
     * 字典项ID
     */
    private Integer dataId;

    /**
     * 数据值，有时候需要使用某些表的ID作为value,数据类型可能不是String,所以需要使用Object
     */
    private Object value;

    /**
     * 标签
     */
    private String label;

    /**
     * 字典分类（外键）
     */
    private Integer typeId;

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
