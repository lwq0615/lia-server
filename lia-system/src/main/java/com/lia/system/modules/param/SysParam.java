package com.lia.system.modules.param;

import lombok.Data;


@Data
public class SysParam {


    private Long paramId;
    private String name;
    private Object value;
    private Long createBy;
    private String createTime;
    private String remark;


}
