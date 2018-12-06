package com.cheer.shopping.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CancelOrderTak implements Runnable {
    Jframe jframe = new Jframe();
    private Integer orderId;

    public CancelOrderTak(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        //查询订单状态
        String state = jframe.orderService.getState(orderId);
        //如果是30分钟未支付
        if (state.equals("nopay")){
            List<Map<String,Object>> maps = jframe.orderItemService.getNameAndInventory(orderId);
            //遍历项目号和商品名称和商品数量
            Iterator<Map<String,Object>> mapIterator = maps.iterator();
            while (mapIterator.hasNext()){
                Map<String,Object> map = mapIterator.next();
                String goodsName = (String) map.get("goods_name");
                Integer goodsInventory = (Integer) map.get("goods_Inventory");
                Integer orderItemId = (Integer) map.get("orderitem_id");
                //通过商品名称获得商品库存
                Integer inventory = jframe.goodsService.getinventory(goodsName);
                //库存加上商品数量
                inventory +=goodsInventory;
                //更新库存
                jframe.goodsService.updateinventory(inventory,goodsName);
                //删除订单项目
                jframe.orderItemService.deleteOrderItem(orderItemId);
            }
            //删除订单
            jframe.orderService.deleteorder(orderId);
        }
    }
}
