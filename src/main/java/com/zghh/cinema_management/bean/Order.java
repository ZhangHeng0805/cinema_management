package com.zghh.cinema_management.bean;


import javax.persistence.*;

/*订单信息实体类*/
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//订单id
    @Column
    private String orderNum;//订单编号
    @Column
    private Integer accountId;//用户id
    @Column
    private Integer rowpieceId;//排片id
    @Column
    private Integer sitNum;//座位号
    @Column
    private Integer orderState;//订单状态（0-未完成 1-已完成 2-订单失败）
    @Column
    private String orderTime;//订单时间
    @Column
    private Double orderPrice;//订单价格

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRowpieceId() {
        return rowpieceId;
    }

    public void setRowpieceId(Integer rowpieceId) {
        this.rowpieceId = rowpieceId;
    }

    public Integer getSitNum() {
        return sitNum;
    }

    public void setSitNum(Integer sitNum) {
        this.sitNum = sitNum;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
}
