package com.cheer.shopping.mapper;

import com.cheer.shopping.model.Cart;

/**
 * 购物车Mapper接口
 */
public interface CartMapper {
    /**
     * 通过购物车编号查询购物车信息
     * @param cartId
     * @return
     */
    Cart getCart(Integer cartId);

    /**
     * 创建订单号
     * @param cartId
     */
    void createCart(Integer cartId);
}
