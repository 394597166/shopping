package com.cheer.shopping.service;

import com.cheer.shopping.model.Goods;
import com.cheer.shopping.util.GoodsMapperImpl;
import java.util.List;

public class GoodsService {
    //创建公用实例对象
    GoodsMapperImpl goodsMapperImpl = new GoodsMapperImpl();
    //模糊查找商品
    public List<Goods> getGoodsWithLikeName(String goodsName){
        List<Goods> goodsList = goodsMapperImpl.getGoodsWithLikeName(goodsName);
        return goodsList;
    }
    //获得所有商品
    public List<Goods> getAllGoods() {
        List<Goods> goodsList = goodsMapperImpl.getAllGoods();
        return goodsList;
    }
    //更新商品库存
    public void updateGoods(Integer goodsInventory,Integer goodsId) {
        goodsMapperImpl.updateGoods(goodsInventory,goodsId);
    }
    //增加商品库存
    public void updateinventory(Integer goodsInventoyr, String goodsName) {
        goodsMapperImpl.updateinventory(goodsInventoyr,goodsName);
    }
    //获得商品数量
    public Integer getinventory(String goodsName) {
        Integer goodsInventory = goodsMapperImpl.getinventory(goodsName);
        return goodsInventory;
    }
}
