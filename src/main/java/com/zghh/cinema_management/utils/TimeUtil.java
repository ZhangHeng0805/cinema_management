package com.zghh.cinema_management.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*时间格式工具*/
public class TimeUtil {
    public static String time(Date date){
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }
    public static String timeTip(Date data1){
        String strDateFormat = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(data1);
    }
}
