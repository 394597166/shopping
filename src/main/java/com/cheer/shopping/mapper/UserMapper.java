package com.cheer.shopping.mapper;

import com.cheer.shopping.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface UserMapper {
    /**
     * 通过用户编号获得用户名
     * @param userId
     * @return
     */
    String getName(Integer userId);

    /**
     * 通过用户编号获得密码
     * @param userId
     * @return
     */
    String getPassWord(Integer userId);

    /**
     * 通过用户编号获得用户名和密码
     * @param userId
     * @return
     */
    Map<String,Object> getNameAndPassWord(Integer userId);

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    Map<String,Object> getIdAndNameAndPassWordwithName( @Param("userName") String userName);

    /**
     * 更新密码
     * @param userPassWord
     * @param userId
     */
    void updateUserPassWord(@Param("userPassWord") String userPassWord,@Param("userId") Integer userId);

    /**
     * 创建用户
     * @param userId
     * @param userName
     * @param userPassWord
     */
    void createUser(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userPassWord") String userPassWord);

    /**
     * 通过用户编号获得用户
     * @param userId
     * @return
     */
    User getUserAll(Integer userId);

    /**
     * 更新用户的钱
     * @param userId
     */
    void updateMoney(@Param("money")Double money,@Param("userId") Integer userId);
}
