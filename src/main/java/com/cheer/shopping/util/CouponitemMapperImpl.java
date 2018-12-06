package com.cheer.shopping.util;


import com.cheer.shopping.mapper.CouponitemMapper;
import com.cheer.shopping.model.Couponitem;

import java.util.List;

//优惠券清单Mapper实现类
public class CouponitemMapperImpl extends AbstractMapper implements CouponitemMapper {
    @Override
    public List<Couponitem> getCouponitem(Integer userid) {
        super.Before();
        List<Couponitem> couponitemList = this.couponitemMapper.getCouponitem(userid);
        super.After();
        return couponitemList;
    }

    @Override
    public void insertCouponitem(Integer couponitemId, Integer couponId, Integer userId) {
        super.Before();
        this.couponitemMapper.insertCouponitem(couponitemId,couponId,userId);
        super.After();
    }

    @Override
    public void deleteCouponitem(Integer couponitemId) {
        super.Before();
        this.couponitemMapper.deleteCouponitem(couponitemId);
        super.After();
    }
}
