package com.lia.system.entity;


import lombok.Data;

@Data
public class SysUser {

    private Long userId;
    private String username;
    private String password;
    private String nick;
    private Integer roleId;
    private Character sex;
    private String phone;
    private String email;
    private String headImg;
    private Character status;
    private Character delFlag;
    private Long createBy;
    private String createTime;
    private String remark;

}
