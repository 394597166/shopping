package com.cheer.shopping.service;

import com.cheer.shopping.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Jframe {
    //创建静态实例user
    public static User user;
    //公用对象
    public static UserService userService = new UserService();
    public static OrderService orderService = new OrderService();
    public static CartService cartService = new CartService();
    public static AddressService addressService = new AddressService();
    public static GoodsService goodsService = new GoodsService();
    public static CartItemService cartItemService = new CartItemService();
    public static OrderItemService orderItemService = new OrderItemService();
    public static CouponService couponService = new CouponService();
    public static CouponItemService couponItemService = new CouponItemService();
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
    /**
     * 主页
     */
    public void homepage(){
        //创建窗口
        JFrame jframeHomepage = new JFrame();
        //设置名称
        jframeHomepage.setTitle("其然购物商城");
        //布局清空
        jframeHomepage.setLayout(null);
        //获得容器
        Container container =jframeHomepage.getContentPane();
        //设置窗口大小
        jframeHomepage.setSize(500,500);
        //定义登陆按钮
        JButton jbLogin = new JButton("登陆");
        //设置按钮位置大小
        jbLogin.setBounds(200,150,100,50);
        //设置按钮单击事件
        jbLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //关闭窗口
                jframeHomepage.setVisible(false);
                //调用登录方法
                userService.login();
            }
        });
        //定义注册按钮
        JButton jbRegister = new JButton("注册");
        //设置按钮位置大小
        jbRegister.setBounds(200,250,100,50);
        //设置按钮单击事件
        jbRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //关闭窗口
                jframeHomepage.setVisible(false);
                //调用注册方法
                userService.register();
            }
        });
        //把按钮添加到容器中
        container.add(jbLogin);
        container.add(jbRegister);
        //显示窗体
        jframeHomepage.setVisible(true);
        //窗体关闭按键生效
        jframeHomepage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jframeHomepage.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                service.shutdown();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }


    /**
     * 主方法
     * @param args
     */
    public static void main(String[] args) {
        new Jframe().homepage();
    }
}
