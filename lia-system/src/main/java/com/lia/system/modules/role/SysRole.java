package com.lia.system.modules.role;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {

    private Integer roleId;
    private String name;
    private String key;
    private Integer companyId;
    private Integer superior;
    private Integer rootRouterId;
    private Long createBy;
    private String createTime;
    private String remark;
    private List<Integer> auths;
    private List<Integer> routers;

}
