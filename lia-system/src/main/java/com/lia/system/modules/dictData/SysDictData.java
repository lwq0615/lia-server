package com.lia.system.modules.dictData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictData {

    private Integer dataId;
    // 有时候需要使用某些表的ID作为value,数据类型可能不是String,所以需要使用Object
    private Object value;
    private String label;
    private String typeId;
    private Long createBy;
    private String createTime;
    private String remark;

}
