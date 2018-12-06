package com.cheer.shopping.util;

import com.cheer.shopping.mapper.OrderItemMapper;
import com.cheer.shopping.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//订单产品清单Mapper实现类
public class OrderItemMapperImpl extends AbstractMapper implements OrderItemMapper {
    public List<OrderItem> getOrderItem(Integer orderId){
        super.Before();
        List<OrderItem> orderItemList =  this.orderItemMapper.getOrderItem(orderId);
        super.After();
        return orderItemList;
    }

    @Override
    public void insertOrderItem(Integer orderItemId, String goodsName, Double goodsPrice, Integer goodsId, Integer goodsInventory, Integer orderId) {
        super.Before();
        this.orderItemMapper.insertOrderItem(orderItemId,goodsName,goodsPrice,goodsId,goodsInventory,orderId);
        super.After();
    }

    @Override
    public void deleteOrderItem(Integer orderItemId) {
        super.Before();
        this.orderItemMapper.deleteOrderItem(orderItemId);
        super.After();
    }

    @Override
    public String getGoodsName(Integer orderItemId) {
        super.Before();
        String goodsName = this.orderItemMapper.getGoodsName(orderItemId);
        super.After();
        return goodsName;
    }

    @Override
    public List<Map<String, Object>> getNameAndInventory(Integer orderItemId) {
        super.Before();
        List<Map<String,Object>> mapList = this.orderItemMapper.getNameAndInventory(orderItemId);
        super.After();
        return mapList;
    }

    @Override
    public Integer getGoodsInventory(Integer orderItemId) {
        super.Before();
        Integer goodsInventory = this.orderItemMapper.getGoodsInventory(orderItemId);
        super.After();
        return goodsInventory;
    }
}
