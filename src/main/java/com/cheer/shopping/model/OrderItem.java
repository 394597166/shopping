package com.cheer.shopping.model;

/**
 * 订单产品清单类
 */
public class OrderItem {
    //订单产品编号
    private Integer orderItemId;
    //商品名称
    private String goodsName;
    //商品单价
    private Double goodsPrice;
    //商品编号
    private Integer goodsId;
    //产品数量
    private Integer goodsInventory;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("orderItemId=").append(orderItemId);
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsInventory=").append(goodsInventory);
        sb.append('}');
        return sb.toString();
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public OrderItem setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public OrderItem setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public OrderItem setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public OrderItem setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public Integer getGoodsInventory() {
        return goodsInventory;
    }

    public OrderItem setGoodsInventory(Integer goodsInventory) {
        this.goodsInventory = goodsInventory;
        return this;
    }


}
