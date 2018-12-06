package com.cheer.shopping.mapper;

import com.cheer.shopping.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 */
public interface OrderMapper {
    /**
     * 通过用户编号生成订单
     * @param userId
     * @return
     */
    List<Order> getOrder(@Param("userId") Integer userId);

    /**
     * 插入订单信息
     * @param orderId
     * @param orderCreate
     * @param orderAddress
     * @param goodsTotal
     * @param fee
     * @param priceTotal
     * @param userId
     */
    void insertOrder(@Param("orderId") Integer orderId,@Param("orderCreate") String orderCreate,@Param("orderAddress") String orderAddress,@Param("goodsTotal") Double goodsTotal,@Param("fee") Double fee,@Param("priceTotal") Double priceTotal,@Param("userId") Integer userId,@Param("state") String state);

    /**
     * 获得订单总金额
     * @param orderId
     * @return
     */
    Double getPriceTotal(@Param("orderId")Integer orderId);

    /**
     * 通过订单号更新订单状态
     * @param orderId
     */
    void updateState(@Param("orderId")Integer orderId);

    /**
     * 通过订单号删除订单
     * @param orderId
     */
    void deleteorder(@Param("orderId")Integer orderId);

    /**
     * 通过订单号获得订单状态
     * @param orderId
     * @return
     */
    String getState(@Param("orderId")Integer orderId);
}
