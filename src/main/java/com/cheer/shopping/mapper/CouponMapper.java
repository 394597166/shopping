package com.cheer.shopping.mapper;

import com.cheer.shopping.model.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CouponMapper {
    /**
     * 通过优惠券编号获得优惠券信息
     * @param couponId
     * @return
     */
    Coupon getCoupon(@Param("couponId") Integer couponId);

    /**
     * 获得所有优惠券信息
     * @return
     */
    List<Map<String,Object>> getAllCoupon();

    /**
     * 通过full和minus字段获得couponId
     * @param full
     * @param minus
     * @return
     */
    Integer getcouponId(@Param("full") Double full,@Param("minus") Double minus);
}
