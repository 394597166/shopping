package com.cheer.shopping.mapper;

import com.cheer.shopping.model.CartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车产品清单接口
 */
public interface CartItemMapper {
    /**
     * 获取购物车清单
     * @param cartId
     * @return
     */
     List<CartItem>getCartItem(Integer cartId);

    /**
     * 插入购物车数据
     * @param itemId
     * @param goodsId
     * @param cartId
     * @param itemAmount
     */
    void insertCartItem(@Param("itemId") Integer itemId,@Param("goodsId") Integer goodsId, @Param("cartId") Integer cartId,@Param("itemAmount") Integer itemAmount);

    /**
     * 通过项目编号删除数据
     * @param itemId
     */
    void deleteCartItem(Integer itemId);

    /**
     * 通过项目编号更改数据
     * @param cartItemAmount
     * @param cartItemId
     */
    void updateCartItem(@Param("cartItemAmount") Integer cartItemAmount,@Param("cartItemId") Integer cartItemId);
}
