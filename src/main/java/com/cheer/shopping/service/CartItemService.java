package com.cheer.shopping.service;

import com.cheer.shopping.model.CartItem;
import com.cheer.shopping.util.CartItemMapperImpl;
import java.util.List;

public class CartItemService {
    //创建公用实例对象
    CartItemMapperImpl cartItemMapperImpl = new CartItemMapperImpl();

    //获得购物车项目
    public List<CartItem> getAllCartItem(Integer cartId){
        List<CartItem> mapList = cartItemMapperImpl.getCartItem(cartId);
        return mapList;
    }
    //删除购物车项目
    public void deleteCartItem(Integer itemId){
        cartItemMapperImpl.deleteCartItem(itemId);
    }
    //更新购物车信息
    public void updateCartItem(Integer cartItemAmount,Integer cartItemId) {
        cartItemMapperImpl.updateCartItem(cartItemAmount,cartItemId);
    }
    //插入购物车项目
    public void insertCartItem(Integer itemId, Integer goodsId, Integer cartId, Integer itemAmount){
        cartItemMapperImpl.insertCartItem(itemId,goodsId,cartId,itemAmount);
    }
}
