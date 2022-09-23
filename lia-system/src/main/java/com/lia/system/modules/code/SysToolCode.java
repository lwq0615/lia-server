package com.lia.system.modules.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysToolCode {

    private Long codeId;
    private String columns;
    private String tableName;
    private String primaryKey;
    private String httpUrl;
    private Long createBy;
    private String createTime;

}
