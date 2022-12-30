package com.lia.system.modules.dictType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 字典分类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysDictType {

    /**
     * 分类ID
     */
    private Integer typeId;

    /**
     * 类名称
     */
    private String name;

    /**
     * 类标识符
     */
    private String key;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private String createTime;
}
