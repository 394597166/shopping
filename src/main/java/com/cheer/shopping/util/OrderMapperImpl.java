package com.cheer.shopping.util;

import com.cheer.shopping.mapper.OrderMapper;
import com.cheer.shopping.model.Order;
import java.util.List;

//订单Mapper实现类
public class OrderMapperImpl extends AbstractMapper implements OrderMapper {
    public  List<Order> getOrder(Integer userId){
        super.Before();
        List<Order> orderList =  this.orderMapper.getOrder(userId);
        super.After();
        return orderList;
    }

    @Override
    public void insertOrder(Integer orderId, String orderCreate, String orderAddress, Double goodsTotal, Double fee, Double priceTotal, Integer userId,String state) {
        super.Before();
        this.orderMapper.insertOrder(orderId,orderCreate,orderAddress,goodsTotal,fee,priceTotal,userId,state);
        super.After();
    }

    @Override
    public Double getPriceTotal(Integer orderId) {
        super.Before();
        Double priceTotal = this.orderMapper.getPriceTotal(orderId);
        super.After();
        return priceTotal;
    }

    @Override
    public void updateState(Integer orderId) {
        super.Before();
        this.orderMapper.updateState(orderId);
        super.After();
    }

    @Override
    public void deleteorder(Integer orderId) {
        super.Before();
        this.orderMapper.deleteorder(orderId);
        super.After();
    }

    @Override
    public String getState(Integer orderId) {
        super.Before();
        String state = this.orderMapper.getState(orderId);
        super.After();
        return state;
    }
}
