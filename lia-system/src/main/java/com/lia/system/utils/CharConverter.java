package com.lia.system.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author zxb 2022/4/12 17:52
 */
public class CharConverter implements Converter<Character> {

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
