package com.cheer.shopping.service;


import com.cheer.shopping.model.Couponitem;
import com.cheer.shopping.util.CouponitemMapperImpl;

import java.util.List;

public class CouponItemService {
    //创建公用实例对象
    CouponitemMapperImpl couponitemMapperImpl = new CouponitemMapperImpl();
    //获得优惠券信息
    public List<Couponitem> getCouponitem(Integer userid) {
        List<Couponitem> couponitemList = couponitemMapperImpl.getCouponitem(userid);
        return couponitemList;
    }

    public void insertCouponitem(Integer couponitemId, Integer couponId, Integer userId) {
        couponitemMapperImpl.insertCouponitem(couponitemId,couponId,userId);
    }

    public void deleteCouponitem(Integer couponitemId) {
        couponitemMapperImpl.deleteCouponitem(couponitemId);
    }
}
