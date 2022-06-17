package com.lia.system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysFile {

    private Long fileId;
    private String name;
    private String path;
    private Long size;
    private String uploadTime;
    private Long uploadUser;

}
