package com.lia.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDict {

    private Integer dictId;
    private Object value;
    private String label;
    private String type;
    private String remark;

}
