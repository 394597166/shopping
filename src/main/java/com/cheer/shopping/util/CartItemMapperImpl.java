package com.cheer.shopping.util;

import com.cheer.shopping.mapper.CartItemMapper;
import com.cheer.shopping.model.CartItem;

import java.util.List;

//购物车产品清单Mapper实现类
public class CartItemMapperImpl extends AbstractMapper implements CartItemMapper {
    @Override
    public List<CartItem> getCartItem(Integer cartId){
        super.Before();
        List<CartItem> cartItemList = super.cartItemMapper.getCartItem(cartId);
        super.After();
        return cartItemList;
    }

    @Override
    public void insertCartItem(Integer itemId, Integer goodsId, Integer cartId, Integer itemAmount) {
        super.Before();
        super.cartItemMapper.insertCartItem(itemId,goodsId,cartId,itemAmount);
        super.After();
    }

    @Override
    public void deleteCartItem(Integer itemId) {
        super.Before();
        super.cartItemMapper.deleteCartItem(itemId);
        super.After();
    }

    @Override
    public void updateCartItem(Integer cartItemAmount,Integer cartItemId) {
        super.Before();
        super.cartItemMapper.updateCartItem(cartItemAmount,cartItemId);
        super.After();
    }
}
