package com.zghh.cinema_management.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*时间格式工具*/
public class TimeUtil {
    //格式化时间
    public static String time(Date date){
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }
    //生成时间戳（17位数字字符串）
    public static String timeTip(Date data1){
        String strDateFormat = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(data1);
    }
    //计算两个时间之间的时间差（分钟差）
    public static int minutesDifference(String time1,String time2){
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fromDate = null;
        Date toDate =null;
        int minutes=-1;
        try {
            fromDate = simpleFormat.parse(time1);
             toDate= simpleFormat.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (fromDate!=null&&toDate!=null) {
            long from = fromDate.getTime();
            long to = toDate.getTime();
            minutes = (int) ((to - from)*1.0 / (1000*60));
            minutes=Math.abs(minutes);
        }
        return minutes;
    }
    //计算两个时间之间的时间差（小时差）
    public static int hoursDifference(String time1,String time2){
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fromDate = null;
        Date toDate =null;
        int hours=-1;
        try {
            fromDate = simpleFormat.parse(time1);
            toDate= simpleFormat.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (fromDate!=null&&toDate!=null) {
            long from = fromDate.getTime();
            long to = toDate.getTime();
            hours = (int) ((to - from)*1.0 / (1000*60*60));
            hours=Math.abs(hours);
        }
        return hours;
    }
    //计算两个时间之间的时间差（天数差）
    public static int daysDifference(String time1,String time2){
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fromDate = null;
        Date toDate =null;
        int days=-1;
        try {
            fromDate = simpleFormat.parse(time1);
            toDate= simpleFormat.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (fromDate!=null&&toDate!=null) {
            long from = fromDate.getTime();
            long to = toDate.getTime();
            days = (int) ((to - from)*1.0 / (1000*60*60*24));
            days=Math.abs(days);
        }
        return days;
    }
}
