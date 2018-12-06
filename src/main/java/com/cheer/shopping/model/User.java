package com.cheer.shopping.model;

import java.util.List;
import java.util.Map;

/**
 * 用户类
 */
public class User {
    //用户名
    private String userName;
    //用户密码
    private String userPassWord;
    //用户编号
    private Integer userId;
    //购物车
    private Cart cart;
    //用户地址清单
    private List<Map<String,Object>> addressList;
    //用户订单清单
    private List<Order> orderList;
    //余额
    private Double money;
    //优惠券
    private List<Couponitem> couponitemList;

    public List<Couponitem> getCouponitemList() {
        return couponitemList;
    }

    public User setCouponitemList(List<Couponitem> couponitemList) {
        this.couponitemList = couponitemList;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", userPassWord='").append(userPassWord).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", cart=").append(cart);
        sb.append(", addressList=").append(addressList);
        sb.append(", orderList=").append(orderList);
        sb.append(", money=").append(money);
        sb.append(", couponitemList=").append(couponitemList);
        sb.append('}');
        return sb.toString();
    }

    public String getUserName() {
        return userName;
    }

    public Double getMoney() {
        return money;
    }

    public User setMoney(Double money) {
        this.money = money;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public User setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Cart getCart() {
        return cart;
    }

    public User setCart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public List<Map<String,Object>> getAddressList() {
        return addressList;
    }

    public User setAddressList(List<Map<String,Object>> addressList) {
        this.addressList = addressList;
        return this;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public User setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        return this;
    }

}
