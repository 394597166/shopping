package com.cheer.shopping.mapper;

import com.cheer.shopping.model.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper接口
 */
public interface GoodsMapper {
    /**
     * 通过商品编号获得商品信息
     * @param goodsId
     * @return
     */
    Map<String,Object> getGoods(Integer goodsId);

    /**
     * 通过商品名称模糊查找
     * @param goodsName
     * @return
     */
    List<Goods> getGoodsWithLikeName(@Param("goodsName") String goodsName);

    /**
     * 查询所有商品
     * @return
     */
    List<Goods> getAllGoods();

    /**
     * 通过商品编号减少数量
     * @param goodsInventory
     * @param goodsId
     */
    void updateGoods(@Param("goodsInventory") Integer goodsInventory,@Param("goodsId") Integer goodsId);
    /**
     * 通过商品名增加数量
     * @param goodsInventoyr
     * @param goodsName
     */
    void updateinventory(@Param("goodsInventoyr")Integer goodsInventoyr,@Param("goodsName") String goodsName);
    Integer getinventory(@Param("goodsName") String goodsName);
}
