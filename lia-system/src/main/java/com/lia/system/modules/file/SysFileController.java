package com.lia.system.modules.file;


import com.lia.system.entity.SysFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PreAuthorize("hasAuthority('system:file:getPic')")
    public void getPic(HttpServletResponse response, Long fileId, Boolean comp){
        sysFileService.loadPicByFileId(response, fileId, comp);
    }


    /**
     * 加载文件
     * @param fileId 资源fileId
     */
    @GetMapping("/getFile")
    @PreAuthorize("hasAuthority('system:file:getFile')")
    public void getFile(HttpServletResponse response, Long fileId){
        sysFileService.getFileByFileId(response, fileId);
    }


    /**
     * 上传文件
     */
    @PostMapping("upload")
    @PreAuthorize("hasAuthority('system:file:upload')")
    public SysFile uploadFile(@RequestBody MultipartFile file, String uuid){
        return sysFileService.uploadFile(file, UploadDir.FILE, uuid);
    }


}
