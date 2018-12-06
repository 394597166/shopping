package com.cheer.shopping.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 地址Mapper接口
 */
public interface AddressMapper {
    /**
     * 获得地址信息
     * @param userId
     * @return
     */
    List<Map<String,Object>> getAddress(Integer userId);

    /**
     * 插入地址信息
     * @param addressId
     * @param addressAddressee
     * @param addressDetailed
     * @param addressPhone
     * @param addressTelephone
     * @param addressMailbox
     * @param addressAlias
     * @param userId
     */
    void insertAddress(@Param("addressId") Integer addressId,@Param("addressAddressee") String addressAddressee,@Param("addressDetailed") String addressDetailed,@Param("addressPhone") String addressPhone,@Param("addressTelephone") String addressTelephone,@Param("addressMailbox") String addressMailbox,@Param("addressAlias") String addressAlias,@Param("userId") Integer userId);

    /**
     * 通过地址编号删除地址信息
     * @param addressId
     */
    void deleteAddress(Integer addressId);

    /**
     * 通过地址编号更新地址
     * @param addressAddressee
     * @param addressDetailed
     * @param addressPhone
     * @param addressTelephone
     * @param addressMailbox
     * @param addressAlias
     * @param addressId
     */
    void updateAddress(@Param("addressAddressee") String addressAddressee,@Param("addressDetailed") String addressDetailed,@Param("addressPhone") String addressPhone,@Param("addressTelephone") String addressTelephone,@Param("addressMailbox") String addressMailbox,@Param("addressAlias") String addressAlias,@Param("addressId") Integer addressId);
}
