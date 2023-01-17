package com.lia.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    /**
     * mysql中datetime的日期格式
     */
    public final static String MYSQL_DATETIME_PATTERN = "YYYY-MM-DD HH:mm:ss";

    /**
     * mysql中date的日期格式
     */
    public final static String MYSQL_DATE_PATTERN = "YYYY-MM-DD HH:mm:ss";


    /**
     * 日期格式转换
     */
    public static String format(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    /**
     * 日期转换为mysql的datetime格式
     */
    public static String mysqlDatetime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATETIME_PATTERN);
        return sdf.format(date);
    }


    /**
     * 日期转换为mysql的date格式
     */
    public static String mysqlDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATE_PATTERN);
        return sdf.format(date);
    }



}
