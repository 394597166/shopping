package com.cheer.shopping.service;

import com.cheer.shopping.model.User;
import com.cheer.shopping.util.CartMapperImpl;
import com.cheer.shopping.util.UserMapperImpl;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.UUID;

public class UserService {
    //创建公用实例对象
    UserMapperImpl userMapperImpl = new UserMapperImpl();
    CartMapperImpl cartMapperImpl = new CartMapperImpl();

    Jframe jframe = new Jframe();
    //登陆用户
    public User logIn(String userName,String userPassWord){
        //获得user数据
        Map<String,Object> stringObjectMap = userMapperImpl.getIdAndNameAndPassWordwithName(userName);
        //如果user数据为空返回null
        if (null == stringObjectMap){
            return null;
        }
        Object userid = stringObjectMap.get("user_id");
        Object name = stringObjectMap.get("user_name");
        Object password = stringObjectMap.get("user_password");
        //如果用户名密码和数据库用户名密码匹配返回user对象
        if (password.equals(userPassWord) && name.equals(userName)){
            User user =refresh((Integer) userid);
            return user;
        }else{//否则返回null
            return null;
        }
    }
    //已登陆修改密码
    public User updateUserPassWord(String userPassWord,Integer userId){
        userMapperImpl.updateUserPassWord(userPassWord,userId);
        User user = refresh(userId);
        return user;
    }
    //生产UUID
    public Integer UUID(){
        Integer uuid = UUID.randomUUID().toString().hashCode();
        if(uuid < 0){
            uuid = -uuid;
        }
        return uuid;
    }
    //创建用户
    public User createUser(String userName,String userPassWord){
        Integer userId = UUID();
        User user = new User();
        //通过用户名获得用户
        Map<String,Object> stringObjectMap = userMapperImpl.getIdAndNameAndPassWordwithName(userName);
        //如果获得的是有数据的则返回null
        if (null == stringObjectMap) {
            user.setUserName(userName);
            user.setUserPassWord(userPassWord);
            user.setUserId(userId);
            userMapperImpl.createUser(userId, userName, userPassWord);
            cartMapperImpl.createCart(userId);
//            user.setCart(cartMapperImpl.getCart(userId));
            user = refresh(user.getUserId());
            return user;
        }else{
            return null;
        }
    }
    //刷新用户
    public User refresh(Integer userid){
        User user =userMapperImpl.getUserAll(userid);
        return user;
    }
    //更新钱
    public void updateMoney(Double money,Integer userId) {
        userMapperImpl.updateMoney(money,userId);
    }

    /**
     * 登录页
     */
    public void login(){
        JFrame jFrameLogin = new JFrame();
        jFrameLogin.setTitle("登陆账号");
        jFrameLogin.setLayout(null);
        jFrameLogin.setSize(500,500);
        Container container = jFrameLogin.getContentPane();
        //定义用户名标签
        JLabel jlUserName = new JLabel("用户名:");
        jlUserName.setBounds(100,150,100,50);

        //定义密码标签
        JLabel jlPassWord = new JLabel("密  码:");
        jlPassWord.setBounds(100,200,100,50);

        //定义用户名文本
        JTextField jtUserName = new JTextField();
        jtUserName.setBounds(200,170,100,25);

        //定义密码文本
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setBounds(200,220,100,25);

        //定义登陆按钮
        JButton jblogin = new JButton("登陆");
        jblogin.setBounds(100,350,100,50);
        jblogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获得文本框数据
                String userName = jtUserName.getText();
                String passWord = new String(jPasswordField.getPassword());

                //判定文本框内容是否为空
                if (userName.length() == 0){
                    //报错
                    JOptionPane.showMessageDialog(null,"用户名不能为空","错误",JOptionPane.ERROR_MESSAGE );
                    //判定文本框内容是否为空
                }else if (passWord.length() == 0){
                    JOptionPane.showMessageDialog(null,"密码不能为空","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    //对密码进行加密
                    String password = DigestUtils.md5Hex(passWord);
                    //给user对象赋值数据，通过name和password
                    jframe.user = logIn(userName,password);
                    //判定user对象是否有数据
                    if (null == jframe.user){
                        JOptionPane.showMessageDialog(null,"用户名不准确或者密码不准确","错误",JOptionPane.ERROR_MESSAGE );
                    }else{
                        JOptionPane.showMessageDialog(null,"登陆成功" );
                        //关闭窗口
                        jFrameLogin.setVisible(false);
                        //调用用户界面方法
                        ui();
                    }
                }
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(300,350,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameLogin.setVisible(false);
                jframe.homepage();
            }
        });

        container.add(jlUserName);
        container.add(jlPassWord);
        container.add(jtUserName);
        container.add(jPasswordField);
        container.add(jblogin);
        container.add(jbreturn);

        jFrameLogin.setVisible(true);
        jFrameLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameLogin.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                jframe.service.shutdown();
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
     * 用户界面
     */
    public void ui(){
        JFrame jframeUi = new JFrame();
        jframeUi.setTitle("用户界面");
        jframeUi.setLayout(null);
        Container container =jframeUi.getContentPane();
        jframeUi.setSize(500,500);
        //定义用户管理按钮
        JButton jbUser = new JButton("用户管理");
        jbUser.setBounds(200,100,100,50);
        jbUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframeUi.setVisible(false);
                userPage();
            }
        });

        //定义购物车管理按钮
        JButton jbCart = new JButton("购物车管理");
        jbCart.setBounds(200,200,100,50);
        jbCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframeUi.setVisible(false);
                jframe.cartService.cartPage();
            }
        });

        //定义退出按钮
        JButton jbReturn = new JButton("退出");
        jbReturn.setBounds(200,300,100,50);
        jbReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframeUi.setVisible(false);
                jframe.homepage();
            }
        });

        container.add(jbUser);
        container.add(jbCart);
        container.add(jbReturn);
        jframeUi.setVisible(true);
        jframeUi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jframeUi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                jframe.service.shutdown();
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
     * 注册页
     */
    public void register(){
        JFrame jFrameLogin = new JFrame();
        jFrameLogin.setTitle("注册账号");
        jFrameLogin.setLayout(null);
        jFrameLogin.setSize(500,500);
        Container container = jFrameLogin.getContentPane();
        //定义用户名标签
        JLabel jlUserName = new JLabel("用户名:");
        jlUserName.setBounds(100,100,100,50);

        //定义密码1标签
        JLabel jlPassWord1 = new JLabel("密  码:");
        jlPassWord1.setBounds(100,150,100,50);

        //定义密码2标签
        JLabel jlPassWord2 = new JLabel("密  码:");
        jlPassWord2.setBounds(100,200,100,50);

        //定义用户名文本
        JTextField jtUserName = new JTextField();
        jtUserName.setBounds(200,120,100,25);

        //定义密码1文本
        JPasswordField jPasswordField1 = new JPasswordField();
        jPasswordField1.setBounds(200,160,100,25);

        //定义密码2文本
        JPasswordField jPasswordField2 = new JPasswordField();
        jPasswordField2.setBounds(200,210,100,25);

        //定义登陆按钮
        JButton jblogin = new JButton("确认");
        jblogin.setBounds(100,350,100,50);
        jblogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获得文本数据
                String userName = jtUserName.getText();
                String passWord1 = new String(jPasswordField1.getPassword());
                String passWord2 = new String(jPasswordField2.getPassword());

                //如果文本框没有数据返回错误
                if (userName.length() == 0){
                    JOptionPane.showMessageDialog(null,"用户名不能为空","错误",JOptionPane.ERROR_MESSAGE );
                    //如果文本框没有数据返回错误
                }else if (passWord1.length() == 0 || passWord2.length() == 0){
                    JOptionPane.showMessageDialog(null,"密码不能为空","错误",JOptionPane.ERROR_MESSAGE );
                    //如果密码输入两次不同返回错误
                }else if(!passWord1.equals(passWord2)){
                    JOptionPane.showMessageDialog(null,"输入的两次密码不相同","错误",JOptionPane.ERROR_MESSAGE );
                }else {
                    //控制输入的数据规范
                    String nameregEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
                    String passwordregEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
                    if(!userName.matches(nameregEx)){
                        JOptionPane.showMessageDialog(null,"用户名（汉字、字母、数字的组合）","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(!passWord1.matches(passwordregEx)){
                        JOptionPane.showMessageDialog(null,"密码（6-16位数字和字母的组合）","错误",JOptionPane.ERROR_MESSAGE );
                    }else {
                        String password = DigestUtils.md5Hex(passWord1);
                        //通过用户名和密码获得user对象
                        jframe.user = createUser(userName,password);
                        //如果已存在用户名则无法注册
                        if (null == jframe.user){
                            JOptionPane.showMessageDialog(null,"用户名已存在","错误",JOptionPane.ERROR_MESSAGE );
                        }else{
                            JOptionPane.showMessageDialog(null,"注册成功" );
                            jFrameLogin.setVisible(false);
                            jframe.homepage();
                        }
                    }
                }
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(300,350,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameLogin.setVisible(false);
                jframe.homepage();
            }
        });

        container.add(jlUserName);
        container.add(jlPassWord1);
        container.add(jlPassWord2);
        container.add(jtUserName);
        container.add(jPasswordField1);
        container.add(jPasswordField2);
        container.add(jblogin);
        container.add(jbreturn);

        jFrameLogin.setVisible(true);
        jFrameLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameLogin.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                jframe.service.shutdown();
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
     * 用户管理
     */
    public void userPage(){
        JFrame jFrameUserPage = new JFrame();
        jFrameUserPage.setTitle("用户管理");
        jFrameUserPage.setLayout(null);
        jFrameUserPage.setSize(500,600);
        Container container = jFrameUserPage.getContentPane();

        //定义修改按钮
        JButton jbUpdataPassWord = new JButton("修改密码");
        jbUpdataPassWord.setBounds(200,50,100,50);
        jbUpdataPassWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获得旧密码
                String oldPassword = JOptionPane.showInputDialog("请输入旧密码:");
                if (null == oldPassword ){
                    JOptionPane.showMessageDialog(null,"密码修改失败","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    //对旧密码进行加密
                    String oldpassword = DigestUtils.md5Hex(oldPassword);
                    if (!jframe.user.getUserPassWord().equals(oldpassword)){
                        JOptionPane.showMessageDialog(null,"旧密码错误","错误",JOptionPane.ERROR_MESSAGE );
                    }else{
                        String passWord1 = JOptionPane.showInputDialog("请输入密码:");
                        String passWord2 = JOptionPane.showInputDialog("请再次输入密码:");
                        if(null == passWord1){
                            JOptionPane.showMessageDialog(null,"密码不能为空","错误",JOptionPane.ERROR_MESSAGE );
                        }else if(null == passWord2){
                            JOptionPane.showMessageDialog(null,"密码不能为空","错误",JOptionPane.ERROR_MESSAGE );
                        }else if (!passWord1.equals(passWord2)){
                            JOptionPane.showMessageDialog(null,"密码不相同","错误",JOptionPane.ERROR_MESSAGE );
                        }else{
                            //对新密码进行规范
                            String passwordregEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
                            if(!passWord1.matches(passwordregEx)){
                                JOptionPane.showMessageDialog(null,"密码（6-16位数字和字母的组合）","错误",JOptionPane.ERROR_MESSAGE );
                            }else{
                                //加密新密码
                                String password = DigestUtils.md5Hex(passWord1);
                                //插入数据库并刷新user
                                jframe.user = updateUserPassWord(password,jframe.user.getUserId());
                                JOptionPane.showMessageDialog(null,"密码修改成功" );
                            }
                        }
                    }
                }
            }
        });

        //定义地址管理按钮
        JButton jbAddress = new JButton("地址管理");
        jbAddress.setBounds(200,150,100,50);
        jbAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUserPage.setVisible(false);
                jframe.addressService.userAddress();
            }
        });
        //定义订单查询按钮
        JButton jbOrder = new JButton("订单查询");
        jbOrder.setBounds(200,250,100,50);
        jbOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUserPage.setVisible(false);
                jframe.orderService.order();
            }
        });

        //定义账户充值按钮
        JButton jbPay = new JButton("账户充值");
        jbPay.setBounds(200,350,100,50);
        jbPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //定义充值数组
                Double[] money = {100.00,1000.00,5000.00,10000.00,20000.00,50000.00,100000.00};
                //弹出下拉菜单
                Double payMoney = (Double) JOptionPane.showInputDialog(null, "请选择充值金额", "选择输入",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        money,money[0]);
                //判定是否取消
                if (payMoney != null){
                    Double newMoney = jframe.user.getMoney()+payMoney;
                    jframe.user.setMoney(newMoney);
                    updateMoney(newMoney,jframe.user.getUserId());
                    JOptionPane.showMessageDialog(null,"充值"+payMoney+"成功");
                }else{
                    JOptionPane.showMessageDialog(null,"充值失败","错误",JOptionPane.ERROR_MESSAGE );
                }
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(200,450,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUserPage.setVisible(false);
                ui();
            }
        });

        container.add(jbUpdataPassWord);
        container.add(jbAddress);
        container.add(jbOrder);
        container.add(jbreturn);
        container.add(jbPay);

        jFrameUserPage.setVisible(true);
        jFrameUserPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameUserPage.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                jframe.service.shutdown();
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
}
