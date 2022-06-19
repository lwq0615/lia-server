package com.lia.system.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lia.system.entity.SysFile;
import com.lia.system.exception.HttpException;
import com.lia.system.service.SysFileService;
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
     * @param path 图片资源路径
     * @param comp 是否压缩
     */
    @GetMapping("/getPic")
    public void getPic(HttpServletResponse response, String path, Boolean comp){
        if(path == null || path.equals("")){
            throw new HttpException(400, "缺少参数path");
        }
        sysFileService.loadPicByPath(response,path,comp);
    }


}
