package com.zghh.cinema_management.bean;


import javax.persistence.*;

/*会员信息实体类*/
@Entity
@Table(name = "t_members")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//会员id
    @Column
    private String account;//账户
    @Column
    private String avatar;//头像
    @Column
    private String password;//密码
    @Column
    private String nickname;//昵称
    @Column
    private Integer gender;//性别（0-不详 1-男 2-女）
    @Column
    private String birthday;//生日
    @Column
    private String phoneNum;//手机号码
    @Column
    private String email;//邮箱
    @Column
    private Double balance;//余额
    @Column
    private Integer state;//状态（0-禁用 1-启用）

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
