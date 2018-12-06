package com.cheer.shopping.model;


/**
 * 商品类
 */
public class Goods {
    //商品编号
    private Integer goodsId;
    //商品名称
    private String goodsName;
    //商品单价
    private Double goodsPrice;
    //商品库存
    private Integer goodsInventory;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Goods{");
        sb.append("goodsId=").append(goodsId);
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsInventory=").append(goodsInventory);
        sb.append('}');
        return sb.toString();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public Goods setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Goods setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public Goods setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public Integer getGoodsInventory() {
        return goodsInventory;
    }

    public Goods setGoodsInventory(Integer goodsInventory) {
        this.goodsInventory = goodsInventory;
        return this;
    }
}
