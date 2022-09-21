package com.lia.system.modules.company;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysCompany {

    private Integer companyId;
    private String name;
    private String phone;
    private String principal;
    private String address;
    private String email;
    private Long createBy;
    private String createTime;
    private String remark;

}
