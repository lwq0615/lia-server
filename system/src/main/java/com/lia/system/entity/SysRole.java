package com.lia.system.entity;


import lombok.Data;

@Data
public class SysRole {

    private Integer roleId;
    private String name;
    private String key;
    private Long createBy;
    private String createTime;
    private String remark;

}
