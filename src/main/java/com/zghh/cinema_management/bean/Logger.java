package com.zghh.cinema_management.bean;


import javax.persistence.*;

/*操作日志*/
@Entity
@Table
public class Logger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//操作id
    @Column
    private String account;//用户账号
    @Column
    private Integer type;//操作类型(充值操作:0)
    @Column
    private String name;//操作者姓名
    @Column
    private String tel;//操作者手机号
    @Column
    private String time;//操作时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Logger{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
