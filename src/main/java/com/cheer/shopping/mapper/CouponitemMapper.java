package com.cheer.shopping.mapper;

import com.cheer.shopping.model.Couponitem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponitemMapper {
    /**
     * 通过用户编号获得优惠券信息
     * @param userid
     * @return
     */
    List<Couponitem> getCouponitem(@Param("userid") Integer userid);

    /**
     * 插入优惠券
     * @param couponitemId
     * @param couponId
     * @param userId
     */
    void insertCouponitem(@Param("couponitemId") Integer couponitemId,@Param("couponId") Integer couponId,@Param("userId") Integer userId);

    /**
     * 删除优惠券
     * @param couponitemI
     */
    void deleteCouponitem(@Param("couponitemId") Integer couponitemI);
}
