package com.lia.system.modules.power;


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
    private Integer routerId;
    private Long createBy;
    private String createTime;
    private String remark;

}
