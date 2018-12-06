package com.cheer.shopping.util;

import com.cheer.shopping.mapper.AddressMapper;

import java.util.List;
import java.util.Map;

//订单Mapper实现类
public class AddressMapperImpl extends AbstractMapper implements AddressMapper {
    public  List<Map<String, Object>> getAddress(Integer userId){
        super.Before();
        List<Map<String, Object>> addressList =  this.addressMapper.getAddress(userId);
        super.After();
        return addressList;
    }

    @Override
    public void insertAddress(Integer addressId,String addressAddressee,String addressDetailed,String addressPhone,String addressTelephone,String addressMailbox,String addressAlias,Integer userId) {
        super.Before();
        this.addressMapper.insertAddress(addressId, addressAddressee, addressDetailed, addressPhone, addressTelephone,addressMailbox,addressAlias,userId);
        super.After();
    }

    @Override
    public void deleteAddress(Integer addressId) {
        super.Before();
        this.addressMapper.deleteAddress(addressId);
        super.After();
    }

    @Override
    public void updateAddress(String addressAddressee,String addressDetailed,String addressPhone,String addressTelephone,String addressMailbox,String addressAlias,Integer addressId) {
        super.Before();
        this.addressMapper.updateAddress(addressAddressee,addressDetailed,addressPhone,addressTelephone,addressMailbox,addressAlias,addressId);
        super.After();
    }
}
