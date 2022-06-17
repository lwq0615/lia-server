package com.lia.system.mapper;


import com.lia.system.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SysFileMapper {


    /**
     * 上传文件时将文件信息写入文件表
     */
    int addSysFile(SysFile file);


    /**
     * 分页查询文件
     */
    ArrayList<SysFile> findSysFile(SysFile file);


    /**
     * 根据id删除文件
     * @param fileIds
     * @return
     */
    int deleteFiles(List<Long> fileIds);


    /**
     * 编辑文件信息
     */
    int editSysFile(SysFile file);
}
