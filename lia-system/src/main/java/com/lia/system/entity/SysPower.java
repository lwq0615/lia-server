package com.lia.system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPower {

    private Integer powerId;
    private String name;
    private String url;
    private String key;
    private Long createBy;
    private String createTime;
    private String remark;

}
