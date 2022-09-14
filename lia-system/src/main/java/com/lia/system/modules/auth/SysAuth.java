package com.lia.system.modules.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAuth {

    private Integer authId;
    private String name;
    private String url;
    private String key;
    private Integer routerId;
    private Long createBy;
    private String createTime;
    private String remark;

}
