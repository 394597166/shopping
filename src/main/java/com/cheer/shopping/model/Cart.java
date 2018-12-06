package com.cheer.shopping.model;

import java.util.List;

/**
 * 购物车类
 */
public class Cart {
    //购物车中存放一堆待购物商品项目
    private List<CartItem> cartItemList;
    //购物车编号
    private Integer cartId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("cartItemList=").append(cartItemList);
        sb.append(", cartId=").append(cartId);
        sb.append('}');
        return sb.toString();
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Cart setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
        return this;
    }

    public Integer getCartId() {
        return cartId;
    }

    public Cart setCartId(Integer cartId) {
        this.cartId = cartId;
        return this;
    }

    //删除一个项目商品
    public void deleteCartItem(CartItem cartItem){

    }

    //清空购物车
    public void emptyCart(){

    }
    //计算总数
    public Integer getProductAmount(){
        return null;
    }
    //计算商品总金额
    public Double getProductPrice(){
        return null;
    }
    //计算邮费
    public Double getFee(){
        return null;
    }
    //计算总金额
    public Double getTotalPrice(){
        return null;
    }

}
