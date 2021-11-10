package com.zghh.cinema_management.utils;


import java.text.DecimalFormat;
import java.util.Random;

public class Message {
    private String time;//时间戳
    private int code;//状态码(200:成功; 100:提示; 404:警告; 500:错误）
    private String title;//标题
    private String message;//内容

    public Message(String time, int code, String title, String message) {
        this.time = time;
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public Message() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + time +
                " // " + code +
                " // " + title +
                " // " + message +
                '}';
    }
    //获取指定范围的随机数
    public static int RandomNum(int min,int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
    //保留两位小数
    public static double twoDecimalPlaces(double num){
        DecimalFormat df   = new DecimalFormat("######0.00");
        String format = df.format(num);
        Double aDouble = Double.valueOf(format);
        return aDouble;
    }
}
