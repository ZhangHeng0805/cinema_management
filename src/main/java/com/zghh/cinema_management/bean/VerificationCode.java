package com.zghh.cinema_management.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//验证码实体
@Entity
@Table
public class VerificationCode {
    @Id
    private String id;
    @Column
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
