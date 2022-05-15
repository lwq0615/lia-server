package com.lia.system.entity;


import lombok.Data;

import java.util.List;

@Data
public class SysRouter {

    private Integer routerId;
    private String label;
    private String path;
    private String element;
    private Integer parent;
    private Integer index;
    private String icon;
    private Integer createBy;
    private String createTime;
    private String remark;
    private List<SysRouter> children;

}
