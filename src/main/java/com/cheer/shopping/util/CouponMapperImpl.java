package com.cheer.shopping.util;


import com.cheer.shopping.mapper.CouponMapper;
import com.cheer.shopping.model.Coupon;

import java.util.List;
import java.util.Map;


//优惠券清单Mapper实现类
public class CouponMapperImpl extends AbstractMapper implements CouponMapper {
    @Override
    public Coupon getCoupon(Integer couponId) {
        super.Before();
        Coupon coupon = this.couponMapper.getCoupon(couponId);
        super.After();
        return coupon;
    }

    @Override
    public List<Map<String, Object>> getAllCoupon() {
        super.Before();
        List<Map<String, Object>> mapList = this.couponMapper.getAllCoupon();
        super.After();
        return mapList;
    }

    @Override
    public Integer getcouponId(Double full, Double minus) {
        super.Before();
        Integer couponId = this.couponMapper.getcouponId(full,minus);
        super.After();
        return couponId;
    }
}
