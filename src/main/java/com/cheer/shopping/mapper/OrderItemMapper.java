package com.cheer.shopping.mapper;

import com.cheer.shopping.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单产品清单Mapper接口
 */
public interface OrderItemMapper {
    /**
     * 通过订单编号查询订单信息
     * @param orderid
     * @return
     */
    List<OrderItem> getOrderItem(Integer orderid);

    /**
     * 插入订单信息
     * @param orderItemId
     * @param goodsName
     * @param goodsPrice
     * @param goodsId
     * @param goodsInventory
     * @param orderId
     */
    void insertOrderItem(@Param("orderItemId") Integer orderItemId,@Param("goodsName") String goodsName,@Param("goodsPrice") Double goodsPrice,@Param("goodsId") Integer goodsId,@Param("goodsInventory") Integer goodsInventory,@Param("orderId")Integer orderId);

    /**
     * 通过项目号删除项目
     * @param orderItemId
     */
    void deleteOrderItem(@Param("orderItemId") Integer orderItemId);

    /**
     * 通过订单号获得商品名
     * @param orderItemId
     * @return
     */
    String getGoodsName(@Param("orderItemId") Integer orderItemId);

    /**
     * 通过订单号获得商品数量
     * @param orderItemId
     * @return
     */
    Integer getGoodsInventory(@Param("orderItemId") Integer orderItemId);

    /**
     * 通过订单号获得项目号和商品名称和商品数量
     * @param orderItemId
     * @return
     */
    List<Map<String,Object>> getNameAndInventory(@Param("orderItemId") Integer orderItemId);
}
