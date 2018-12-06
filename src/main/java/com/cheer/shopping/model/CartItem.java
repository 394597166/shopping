package com.cheer.shopping.model;

import java.util.List;
import java.util.Map;

/**
 * 购物车产品清单类
 */
public class CartItem {
    //产品清单编号
    private Integer cartItemId;
    //产品对象
    private Goods goods;
    //产品数量
    private Integer cartItemAmount;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CartItem{");
        sb.append("cartItemId=").append(cartItemId);
        sb.append(", goods=").append(goods);
        sb.append(", cartItemAmount=").append(cartItemAmount);
        sb.append('}');
        return sb.toString();
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public CartItem setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
        return this;
    }

    public Goods getGoods() {
        return goods;
    }

    public CartItem setGoods(Goods goods) {
        this.goods = goods;
        return this;
    }

    public Integer getCartItemAmount() {
        return cartItemAmount;
    }

    public CartItem setCartItemAmount(Integer cartItemAmount) {
        this.cartItemAmount = cartItemAmount;
        return this;
    }
}
