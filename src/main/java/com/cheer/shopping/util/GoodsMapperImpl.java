package com.cheer.shopping.util;

import com.cheer.shopping.mapper.GoodsMapper;
import com.cheer.shopping.model.Goods;

import java.util.List;
import java.util.Map;

//商品Mapper实现类
public class GoodsMapperImpl  extends AbstractMapper implements GoodsMapper {
    public Map<String,Object> getGoods(Integer goodId){
        super.Before();
        Map<String,Object> map =  this.goodsMapper.getGoods(goodId);
        super.After();
        return map;
    }

    @Override
    public List<Goods> getGoodsWithLikeName(String goodsName) {
        super.Before();
        String name = "%"+goodsName+"%";
        List<Goods> goodsList = this.goodsMapper.getGoodsWithLikeName(name);
        super.After();
        return goodsList;
    }

    @Override
    public List<Goods> getAllGoods() {
        super.Before();
        List<Goods> goodsList = this.goodsMapper.getAllGoods();
        super.After();
        return goodsList;
    }

    @Override
    public void updateGoods(Integer goodsInventory,Integer goodsId) {
        super.Before();
        this.goodsMapper.updateGoods(goodsInventory,goodsId);
        super.After();
    }

    @Override
    public void updateinventory(Integer goodsInventoyr, String goodsName) {
        super.Before();
        this.goodsMapper.updateinventory(goodsInventoyr,goodsName);
        super.After();
    }

    @Override
    public Integer getinventory(String goodsName) {
        super.Before();
        Integer goodsInventory = this.goodsMapper.getinventory(goodsName);
        super.After();
        return goodsInventory;
    }
}
