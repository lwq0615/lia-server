package com.lia.system.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.lia.system.entity.SysUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

public class ExcelUtils {

    public static void write(HttpServletResponse response, String fileName, Collection data, Class cls){
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
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


/**
 * 处理char类型数据
 */
class CharConverter implements Converter<Character> {

    /**
     * 开启对 Character 类型的支持
     */
    @Override
    public Class<?> supportJavaTypeKey() {
        return Character.class;
    }


    /**
     * 自定义对 Character 类型数据的处理
     * 我这里就拿 String 去包装了下
     */
    @Override
    public WriteCellData<?> convertToExcelData(Character value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<Character>(String.valueOf(value));
    }
}
