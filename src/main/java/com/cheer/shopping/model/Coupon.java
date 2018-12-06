package com.cheer.shopping.model;

public class Coupon {
    private Integer couponId;
    private Double full;
    private Double minus;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coupon{");
        sb.append("couponId=").append(couponId);
        sb.append(", full=").append(full);
        sb.append(", minus=").append(minus);
        sb.append('}');
        return sb.toString();
    }

    public Integer getCouponId() {
        return couponId;
    }

    public Coupon setCouponId(Integer couponId) {
        this.couponId = couponId;
        return this;
    }

    public Double getFull() {
        return full;
    }

    public Coupon setFull(Double full) {
        this.full = full;
        return this;
    }

    public Double getMinus() {
        return minus;
    }

    public Coupon setMinus(Double minus) {
        this.minus = minus;
        return this;
    }
}
