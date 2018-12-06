package com.cheer.shopping.util;

import com.cheer.shopping.mapper.*;
import com.cheer.shopping.model.Coupon;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 * Mapper类
 */
public abstract class AbstractMapper {
    InputStream in = null;
    SqlSession sqlSession = null;
    GoodsMapper goodsMapper = null;
    CartItemMapper cartItemMapper = null;
    CartMapper cartMapper = null;
    OrderItemMapper orderItemMapper = null;
    OrderMapper orderMapper = null;
    AddressMapper addressMapper = null;
    UserMapper userMapper = null;
    CouponMapper couponMapper = null;
    CouponitemMapper couponitemMapper = null;
    //读取数据库之前调用
    public void Before(){
        try {
            //得到输入流
            in = Resources.getResourceAsStream("mybatis-config.xml");
            //得到SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            //设置事务自动提交
            sqlSession =sqlSessionFactory.openSession(true);
            //得到goodsMapper对象
            goodsMapper = sqlSession.getMapper(GoodsMapper.class);
            //得到cartItemMapper对象
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class);
            //得到cartMapper对象
            cartMapper = sqlSession.getMapper(CartMapper.class);
            //得到orderItemMapper对象
            orderItemMapper = sqlSession.getMapper(OrderItemMapper.class);
            //得到orderMapper对象
            orderMapper = sqlSession.getMapper(OrderMapper.class);
            //得到addressMapper对象
            addressMapper = sqlSession.getMapper(AddressMapper.class);
            //得到userMapper对象
            userMapper = sqlSession.getMapper(UserMapper.class);
            //得到couponMapper对象
            couponMapper = sqlSession.getMapper(CouponMapper.class);
            //得到couponitemMapper对象
            couponitemMapper = sqlSession.getMapper(CouponitemMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   //读取数据库之后调用
    public void After(){
        //手动提交事务
        //sqlSession.commit();
        try {
            if (null != in) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != sqlSession) {
            sqlSession.close();
        }
    }
}
