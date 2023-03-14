package com.lia.system.utils;

import com.alibaba.excel.EasyExcel;
import com.lia.system.entity.SysUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

public class ExcelUtils {

    public static void write(HttpServletResponse response, String fileName, Collection data, Class cls){
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel
                    .write(response.getOutputStream(), cls)
                    .registerConverter(new CharConverter())
                    .sheet("系统用户")
                    .doWrite(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
