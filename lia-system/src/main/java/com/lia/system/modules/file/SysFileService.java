package com.lia.system.modules.file;

import com.lia.system.exception.HttpException;
import com.lia.system.security.LoginUser;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
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
     *
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
        sysFileMapper.addSysFile(sysFile);
        return sysFile;
    }

    /**
     * 获取文件
     */
    public void getFile(HttpServletResponse response, Long fileId) {
        if (fileId == null) {
            throw new HttpException(400, "缺少参数fileId");
        }
        SysFile sysFile = new SysFile();
        sysFile.setFileId(fileId);
        ArrayList<SysFile> sysFiles = this.getSysFile(sysFile);
        if(sysFiles != null && sysFiles.size() > 0){
            sysFile = sysFiles.get(0);
        }
        if(sysFile.getPath() == null || sysFile.getPath().equals("")) return;
        response.setContentType("application/octet-stream");
        File file = new File(sysFile.getPath());
        try {
            //开启下载
            response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(sysFile.getName(), "utf-8"));
            // 文件不存在
            if (!file.exists()) {
                return;
            }
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[8192];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                response.getOutputStream().write(bytes, 0, len);
            }
            response.getOutputStream().close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载图片
     */
    public void loadPic(HttpServletResponse response, Long fileId, Boolean comp) {
        if (fileId == null) {
            throw new HttpException(400, "缺少参数fileId");
        }
        String path = null;
        SysFile image = new SysFile();
        image.setFileId(fileId);
        ArrayList<SysFile> sysFiles = this.getSysFile(image);
        if(sysFiles != null && sysFiles.size() > 0){
            path = sysFiles.get(0).getPath();
        }
        if(path == null || path.equals("")) return;
        response.setContentType("image/" + path.split("\\.")[path.split("\\.").length - 1]);
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
            } else {
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
     * @param file
     * @return
     */
    public ArrayList<SysFile> getSysFile(SysFile file) {
        return sysFileMapper.findSysFile(file);
    }


    /**
     * 根据Id删除文件
     */
    public int deleteFiles(List<Long> fileIds) {
        ArrayList<SysFile> sysFiles = sysFileMapper.findSysFileByIds(fileIds);
        for (SysFile sysFile : sysFiles) {
            // 删除本地磁盘的文件
            if (sysFile.getPath() != null && !sysFile.getPath().equals("")) {
                File oldFile = new File(sysFile.getPath());
                if (oldFile.exists()) oldFile.delete();
            }
        }
        return sysFileMapper.deleteFiles(fileIds);
    }
}
