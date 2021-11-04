package com.zghh.cinema_management.bean;


import lombok.Data;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

/*管理员实体类*/

@Entity
@Table(name = "t_administrator")

public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//管理员id
    @Column
    private String account;//账户
    @Column
    private String password;//密码
    @Column
    private String name;//姓名
    @Column(name = "[phoneNum]")
    private String phoneNum;//手机号
    @Column
    private Integer state;//账号状态(0-禁用 1-正常)

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
