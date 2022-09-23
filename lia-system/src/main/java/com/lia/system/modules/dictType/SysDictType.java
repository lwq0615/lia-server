package com.lia.system.modules.dictType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictType {
    private Integer typeId;
    private String name;
    private String key;
    private String remark;
    private Long createBy;
    private String createTime;
}
