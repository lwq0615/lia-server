package com.lia.system.modules.file;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/system/file")
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;


    /**
     * 加载图片
     * @param fileId 图片资源fileId
     * @param comp 是否压缩
     */
    @GetMapping("/getPic")
    public void getPic(HttpServletResponse response, Long fileId, Boolean comp){
        sysFileService.loadPicByFileId(response, fileId, comp);
    }




    /**
     * 加载文件
     * @param fileId 资源fileId
     */
    @GetMapping("/getFile")
    public void getFile(HttpServletResponse response, Long fileId){
        sysFileService.getFileByFileId(response, fileId);
    }


}
