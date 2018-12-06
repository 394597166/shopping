package com.cheer.shopping.model;

public class Couponitem {
    private Integer couponitemId;
    private Coupon coupon;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Couponitem{");
        sb.append("couponitemId=").append(couponitemId);
        sb.append(", coupon=").append(coupon);
        sb.append('}');
        return sb.toString();
    }

    public Integer getCouponitemId() {
        return couponitemId;
    }

    public Couponitem setCouponitemId(Integer couponitemId) {
        this.couponitemId = couponitemId;
        return this;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Couponitem setCoupon(Coupon coupon) {
        this.coupon = coupon;
        return this;
    }
}
