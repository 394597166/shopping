package com.cheer.shopping.util;
import com.cheer.shopping.mapper.CartMapper;
import com.cheer.shopping.model.Cart;


//购物车产品清单Mapper实现类
public class CartMapperImpl extends AbstractMapper implements CartMapper {
    public Cart getCart(Integer cartId){
        super.Before();
        Cart cart = super.cartMapper.getCart(cartId);
        super.After();
        return cart;
    }

    @Override
    public void createCart(Integer cartId) {
        super.Before();
        super.cartMapper.createCart(cartId);
        super.After();
    }
}
