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
    private Integer rootRouterId;
    private Long createBy;
    private String createTime;
    private String remark;
    private List<Integer> powers;
    private List<Integer> routers;

}
