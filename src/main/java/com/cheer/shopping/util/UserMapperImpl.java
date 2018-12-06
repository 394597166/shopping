package com.cheer.shopping.util;

import com.cheer.shopping.mapper.UserMapper;
import com.cheer.shopping.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

//用户Mapper实现类
public class UserMapperImpl extends AbstractMapper implements UserMapper {
    public  String getName(Integer userId){
        super.Before();
        String name =  this.userMapper.getName(userId);
        super.After();
        return name;
    }

    @Override
    public String getPassWord(Integer userId) {
        super.Before();
        String passWord = this.userMapper.getPassWord(userId);
        super.After();
        return passWord;
    }

    @Override
    public Map<String,Object> getNameAndPassWord(Integer userId) {
        super.Before();
        Map<String,Object> stringObjectMap = this.userMapper.getNameAndPassWord(userId);
        super.After();
        return stringObjectMap;
    }

    @Override
    public void createUser(Integer userId, String userName, String userPassWord) {
        super.Before();
        this.userMapper.createUser(userId,userName,userPassWord);
        super.After();
    }

    @Override
    public void updateUserPassWord(String userPassWord,Integer userId) {
        super.Before();
        this.userMapper.updateUserPassWord(userPassWord,userId);
        super.After();
    }

    @Override
    public Map<String, Object> getIdAndNameAndPassWordwithName(String userName) {
        super.Before();
        Map<String,Object> stringObjectMap = this.userMapper.getIdAndNameAndPassWordwithName(userName);
        super.After();
        return stringObjectMap;
    }

    @Override
    public User getUserAll(Integer userId) {
        super.Before();
        User user = this.userMapper.getUserAll(userId);
        super.After();
        return user;
    }

    @Override
    public void updateMoney(Double money,Integer userId) {
        super.Before();
        this.userMapper.updateMoney(money,userId);
        super.After();
    }
}
