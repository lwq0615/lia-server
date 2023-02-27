package com.lia.system.entity;


import com.lia.system.utils.ArrayUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * 系统文件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysFile {


    /**
     * 文件资源请求相关路径
     */
    public static final List<String> FILE_REQ_URL = ArrayUtils.asList(
            "/system/file/getPic",
            "/system/file/getFile"
    );

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 原文件名
     */
    private String name;

    /**
     * 文件在服务器的存储路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 上传时间
     */
    private String uploadTime;

    /**
     * 上传者
     */
    private Long uploadUser;


    /**
     * 文件大小set方法
     * @param size 文件大小（不小于0）
     */
    public void setSize(Long size) {
        if(size < 0){
            size = 0L;
        }
        this.size = size;
    }
}
