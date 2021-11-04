package com.zghh.cinema_management.bean;

import javax.persistence.*;

/*影厅管理实体类*/
@Entity
@Table(name = "t_screens")
public class Screens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//影厅id
    @Column
    private String screensName;//影厅名称
    @Column
    private String type;//影厅类型
    @Column
    private Integer seatingNum;//座位数
    @Column(columnDefinition = "TEXT")
    private String seatingInfo;//座位信息
    @Column
    private Integer state;//影厅状态（0-未启用 1-启用）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScreensName() {
        return screensName;
    }

    public void setScreensName(String screensName) {
        this.screensName = screensName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeatingNum() {
        return seatingNum;
    }

    public void setSeatingNum(Integer seatingNum) {
        this.seatingNum = seatingNum;
    }

    @Lob
    @Column(columnDefinition = "TEXT")
    public String getSeatingInfo() {
        return seatingInfo;
    }

    public void setSeatingInfo(String seatingInfo) {
        this.seatingInfo = seatingInfo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
