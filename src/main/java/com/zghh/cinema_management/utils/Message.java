package com.zghh.cinema_management.utils;


public class Message {
    private String time;//时间戳
    private int code;//状态码(200:成功 100:数据为Null 500:错误）
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
}
