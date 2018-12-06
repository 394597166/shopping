package com.cheer.shopping.service;

import com.cheer.shopping.model.Coupon;
import com.cheer.shopping.util.CouponMapperImpl;

import java.util.List;
import java.util.Map;

public class CouponService {
    //创建公用实例对象
    CouponMapperImpl couponMapperImpl = new CouponMapperImpl();

    public Coupon getCoupon(Integer couponId) {
        Coupon coupon = couponMapperImpl.getCoupon(couponId);
        return coupon;
    }

    public List<Map<String, Object>> getAllCoupon() {
        List<Map<String, Object>> mapList = couponMapperImpl.getAllCoupon();
        return mapList;
    }

    public Integer getcouponId(Double full, Double minus) {
        Integer couponId = couponMapperImpl.getcouponId(full,minus);
        return couponId;
    }
}
