package com.lia.system.service;

import com.lia.system.entity.SysFile;
import com.lia.system.entity.SysRole;
import com.lia.system.entity.SysUser;
import com.lia.system.mapper.SysFileMapper;
import com.lia.system.security.LoginUser;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class SysFileService {

    @Value("${upload.basePath:public}")
    private String basePath;

    @Autowired
    private SysFileMapper sysFileMapper;


    /**
     * 上传文件
     * dirName 文件目录
     * @return 文件信息
     */
    public SysFile uploadFile(MultipartFile file, String dirName) {
        if (file == null) {
            return null;
        }
        // 图片路径格式:public/年月日/uuid.type
        String oldName = file.getOriginalFilename();
        String fileType = oldName.split("\\.")[oldName.split("\\.").length - 1];
        String newName = UUID.randomUUID().toString() + "." + fileType;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String newFilePath = basePath + "/" + dirName + "/" + date + "/" + newName;
        File newFile = new File(newFilePath);
        // 路径不存在则先创建文件目录
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try (
                FileOutputStream outputStream = new FileOutputStream(newFile);
        ) {
            outputStream.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 将上传的文件信息写入数据库并返回
        SysFile sysFile = new SysFile();
        sysFile.setName(oldName);
        sysFile.setPath(newFilePath);
        sysFile.setSize(file.getSize());
        sysFile.setUploadUser(LoginUser.getLoginUserId());
        return sysFile;
    }


    /**
     * 新增或编辑
     * @param file
     * @return
     */
    public SysFile saveFile(SysFile file){
        if(file.getFileId() == null){
            sysFileMapper.addSysFile(file);
        }else{
            sysFileMapper.editSysFile(file);
        }
        return file;
    }


    /**
     * 加载图片
     */
    public void loadPicByPath(HttpServletResponse response, String path, Boolean comp) {
        response.setContentType("image/"+path.split("\\.")[path.split("\\.").length - 1]);
        File file = new File(path);
        //开启下载
        //response.setHeader("Content-Disposition","attachment;filename="+file.getName());
        try {
            // 文件不存在
            if (!file.exists()) {
                return;
            }
            if (comp != null && comp) {
                //压缩图片大小 用于各种缩略图
                Thumbnails.of(file).scale(0.3).outputQuality(0.3).toOutputStream(response.getOutputStream());
            }else{
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[8192];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    response.getOutputStream().write(bytes, 0, len);
                }
                response.getOutputStream().close();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分页查询文件
     *
     * @param file
     * @return
     */
    public ArrayList<SysFile> getSysFile(SysFile file) {
        return sysFileMapper.findSysFile(file);
    }


    /**
     * 根据Id删除文件
     */
    public int deleteFiles(List<Long> fileIds){
        return sysFileMapper.deleteFiles(fileIds);
    }
}
