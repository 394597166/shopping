package com.cheer.shopping.service;

import com.cheer.shopping.util.OrderItemMapperImpl;

import java.util.List;
import java.util.Map;

public class OrderItemService {
    //创建公用实例对象
    OrderItemMapperImpl orderItemMapperImpl = new OrderItemMapperImpl();
    //插入订单项目
    public void insertOrderItem(Integer orderItemId, String goodsName, Double goodsPrice, Integer goodsId, Integer goodsInventory, Integer orderId) {
        orderItemMapperImpl.insertOrderItem(orderItemId,goodsName,goodsPrice,goodsId,goodsInventory,orderId);
    }
    //删除订单
    public void deleteOrderItem(Integer orderItemId) {
        orderItemMapperImpl.deleteOrderItem(orderItemId);
    }
    //获得商品名
    public String getGoodsName(Integer orderItemId) {
        String goodsName = orderItemMapperImpl.getGoodsName(orderItemId);
        return goodsName;
    }

    //获得商品数量
    public Integer getGoodsInventory(Integer orderItemId) {
        Integer goodsInventory = orderItemMapperImpl.getGoodsInventory(orderItemId);
        return goodsInventory;
    }
    //获得商品名和商品数量
    public List<Map<String, Object>> getNameAndInventory(Integer orderItemId) {
        List<Map<String,Object>> mapList = orderItemMapperImpl.getNameAndInventory(orderItemId);
        return mapList;
    }
}
