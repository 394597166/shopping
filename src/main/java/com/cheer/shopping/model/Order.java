package com.cheer.shopping.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 订单类
 */
public class Order {
    //订单编号
    private Integer orderId;
    //订单创建日期
    private String orderCreate;
    //订单商品信息
    private List<OrderItem> orderItemList;
    //订单地址
    private String address;
    //订单商品合计金额
    private Double goodsTotal;
    //订单运费
    private Double fee;
    //订单总金额
    private Double priceTotal;
    //状态
    private String state;

    public String getState() {
        return state;
    }

    public Order setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderCreate='").append(orderCreate).append('\'');
        sb.append(", orderItemList=").append(orderItemList);
        sb.append(", address=").append(address);
        sb.append(", goodsTotal=").append(goodsTotal);
        sb.append(", fee=").append(fee);
        sb.append(", priceTotal=").append(priceTotal);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Order setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getOrderCreate() {
        return orderCreate;
    }

    public Order setOrderCreate(String orderCreate) {
        this.orderCreate = orderCreate;
        return this;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public Order setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getGoodsTotal() {
        return goodsTotal;
    }

    public Order setGoodsTotal(Double goodsTotal) {
        this.goodsTotal = goodsTotal;
        return this;
    }

    public Double getFee() {
        return fee;
    }

    public Order setFee(Double fee) {
        this.fee = fee;
        return this;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public Order setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
        return this;
    }
}
