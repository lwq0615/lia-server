package com.lia.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public final static String MYSQL_DATETIME_PATTERN = "YYYY-MM-DD HH:mm:ss";

    public static String format(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
