package com.cheer.shopping.service;

import com.cheer.shopping.model.Address;
import com.cheer.shopping.model.CartItem;
import com.cheer.shopping.model.Couponitem;
import com.cheer.shopping.model.Goods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartService {
    Jframe jframe = new Jframe();
    /**
     * 购物车管理
     */
    public void cartPage(){
        JFrame jFrameCartPage = new JFrame();
        jFrameCartPage.setTitle("购物车管理");
        jFrameCartPage.setLayout(null);
        jFrameCartPage.setSize(800,500);
        Container container = jFrameCartPage.getContentPane();

        //表格标题
        String[] columnNames = { "项目编号", "商品编号", "购物车编号", "库存数量", "商品名称", "单价" ,"购买数量"};
        Vector<String> columnNameV = new Vector<>();
        for (int column = 0; column < columnNames.length; column++) {
            columnNameV.add(columnNames[column]);
        }
        //创建集合
        Vector<Vector<Object>> tableValueV = new Vector<>();
        //获得购物车数据
        java.util.List<CartItem> mapItemList =  jframe.cartItemService.getAllCartItem(jframe.user.getUserId());
        //遍历购物车数据
        Iterator<CartItem> iterator = mapItemList.iterator();
        while (iterator.hasNext()){
            CartItem cartItem = iterator.next();
            //存放单行数据
            Vector<Object> rowV = new Vector<>();

            rowV.add(cartItem.getCartItemId());
            rowV.add(cartItem.getGoods().getGoodsId());
            rowV.add(jframe.user.getCart().getCartId());
            rowV.add(cartItem.getGoods().getGoodsInventory());
            rowV.add(cartItem.getGoods().getGoodsName());
            rowV.add(cartItem.getGoods().getGoodsPrice());
            rowV.add(cartItem.getCartItemAmount());
            tableValueV.add(rowV);
        }
        //打印表格，控制第6列可以修改
        JTable table = new JTable(tableValueV, columnNameV){
            public boolean isCellEditable(int row,int column){
                if (column == 6){
                    return true;
                }else{
                    return false;
                }
            }
        };
        table.setBounds(10,10,800,200);

        JButton insertGoods = new JButton("添加商品");
        insertGoods.setBounds(50,300,100,50);
        insertGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameCartPage.setVisible(false);
                insertCartItem(null);
            }
        });

        JButton deleteGoods = new JButton("删除商品");
        deleteGoods.setBounds(200,300,100,50);
        deleteGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获得选中行数
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要删除的商品数据","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    //遍历行数
                    for (int i = 0;i<selectedRows.length;i++){
                        //获得第一列数据
                        Integer itemId = (int) table.getValueAt(selectedRows[i],0);
                        jframe.cartItemService.deleteCartItem(itemId);
                    }
                    Jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                    JOptionPane.showMessageDialog(null,"删除成功" );
                    jFrameCartPage.setVisible(false);
                    cartPage();
                }
            }
        });

        JButton updateAmount = new JButton("修改数量");
        updateAmount.setBounds(350,400,100,50);
        updateAmount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要修改的商品","错误",JOptionPane.ERROR_MESSAGE );
                }else if (selectedRows.length > 1){
                    JOptionPane.showMessageDialog(null,"修改数据一次只能一条","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    //获得选中行数据
                    Integer itemId = (Integer) table.getValueAt(selectedRows[0],0);
                    Integer inventory = (Integer) table.getValueAt(selectedRows[0],3);
                    Integer amount = (Integer) table.getValueAt(selectedRows[0],6);
                    if (amount <= 0){
                        JOptionPane.showMessageDialog(null,"数量不能为0或者小于0","错误",JOptionPane.ERROR_MESSAGE );
                    }else if (amount>inventory){
                        JOptionPane.showMessageDialog(null,"数量不能库存","错误",JOptionPane.ERROR_MESSAGE );
                    }else{
                        jframe.cartItemService.updateCartItem(amount,itemId);
                        jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                        JOptionPane.showMessageDialog(null,"修改成功" );
                        jFrameCartPage.setVisible(false);
                        cartPage();
                    }
                }
            }
        });

        JButton jborder = new JButton("生成订单");
        jborder.setBounds(500,300,100,50);
        jborder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要购买的商品数据","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    Integer orderId = jframe.userService.UUID();
                    //获取当前的日期
                    Date date = new Date();
                    //设置要获取到什么样的时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //获取String类型的时间
                    String orderCreate = sdf.format(date);
                    //获得地址信息
                    String orderAddress;
                    java.util.List<String> tableValueV = new ArrayList<>();
                    java.util.List<Map<String, Object>> mapList =  jframe.addressService.getAddress(jframe.user.getUserId());
                    //判断如果地址数据为空则需要新建
                    if (mapList == null || mapList.size() == 0){
                        JOptionPane.showMessageDialog(null,"地址为空");
                        jFrameCartPage.setVisible(false);
                        jframe.addressService.insertAddress();
                    }else{
                        //获得地址别名
                        Iterator<Map<String,Object>> iterator = mapList.iterator();
                        while (iterator.hasNext()){
                            Map<String,Object> map = iterator.next();
                            tableValueV.add((String) map.get("address_alias"));
                        }
                        //生成下拉菜单选择地址
                        String[] addresss = new String[tableValueV.size()];
                        addresss=tableValueV.toArray(addresss);
                        orderAddress = (String) JOptionPane.showInputDialog(null, "请选择一个地址", "选择输入",
                                JOptionPane.INFORMATION_MESSAGE, null,
                                addresss,addresss[0]);
                        if (orderAddress == null){
                            orderAddress = "公司";
                        }
                        double goodsTotal = 0;
                        double fee = 5;
                        //计算价格
                        for (int i = 0;i<selectedRows.length;i++){
                            //商品单价
                            Double goodsPrice = (Double) table.getValueAt(selectedRows[i],5);
                            //购买数量
                            Integer cartItemAmount = (Integer) table.getValueAt(selectedRows[i],6);
                            goodsTotal += goodsPrice * cartItemAmount;
                        }
                        if (goodsTotal > 78){
                            fee = 0;
                        }
                        double priceTotal = goodsTotal + fee;

                        List<Couponitem> couponitemList = jframe.couponItemService.getCouponitem(jframe.user.getUserId());
                        if (couponitemList.size() != 0 || null != couponitemList){
                            String[] coupons = new String[couponitemList.size()];
                            int k = 0;
                            Iterator<Couponitem> couponitemIterator = couponitemList.iterator();
                            while (couponitemIterator.hasNext()){
                                Couponitem couponitem = couponitemIterator.next();
                                //如果需要支付总金额超过优惠券要求
                                if (priceTotal >= couponitem.getCoupon().getFull()){
                                    coupons[k] ="满"+couponitem.getCoupon().getFull()+"减"+couponitem.getCoupon().getMinus();
                                    k++;
                                }
                            }
                            if (coupons[0] != null){
                                //弹出下拉菜单
                                String coupon = (String) JOptionPane.showInputDialog(null, "请优惠券类型", "选择输入",
                                        JOptionPane.INFORMATION_MESSAGE, null,
                                        coupons,coupons[0]);
                                if (coupon != null){
                                    char[] a = coupon.toCharArray();
                                    int m=coupon.indexOf('满');
                                    int j=coupon.indexOf('减');

                                    StringBuffer numone=new StringBuffer();
                                    StringBuffer numtwo=new StringBuffer();

                                    while(m+1<j){
                                        numone.append(a[m+1]);
                                        m++;
                                    }
                                    while(j+1<a.length){
                                        numtwo.append(a[j+1]);
                                        j++;
                                    }
                                    Double full =  Double.parseDouble(String.valueOf(numone));
                                    Double minus = Double.parseDouble(String.valueOf(numtwo));
                                    priceTotal -= minus;
                                    int o = 0;
                                    List<Couponitem> couponitemList1 = jframe.couponItemService.getCouponitem(jframe.user.getUserId());
                                    Iterator<Couponitem> couponitemIterator1 = couponitemList1.iterator();
                                    while (couponitemIterator1.hasNext()){
                                        Couponitem couponitem = couponitemIterator1.next();
                                        if (o==0 && full.equals(couponitem.getCoupon().getFull())&&minus.equals(couponitem.getCoupon().getMinus())){
                                            jframe.couponItemService.deleteCouponitem(couponitem.getCouponitemId());
                                            o++;
                                        }
                                    }
                                }
                            }
                        }

                        Integer userId = jframe.user.getUserId();
                        //设置订单状态
                        String state = "nopay";
                        //插入订单数据库
                        jframe.orderService.insertOrder(orderId,orderCreate,orderAddress,goodsTotal,fee,priceTotal,userId,state);
                        //循环插入订单项目
                        for (int i = 0;i<selectedRows.length;i++){
                            //订单项目编号
                            Integer orderItemId = jframe.userService.UUID();
                            //商品名
                            String goodsName = (String) table.getValueAt(selectedRows[i],4);
                            //商品单价
                            Double goodsPrice = (Double) table.getValueAt(selectedRows[i],5);
                            //购物车编号
                            Integer itemId = (Integer) table.getValueAt(selectedRows[i],0);
                            //库存数量
                            Integer goodsInventory = (Integer) table.getValueAt(selectedRows[i],3);
                            //购买数量
                            Integer cartItemAmount = (Integer) table.getValueAt(selectedRows[i],6);
                            //商品编号
                            Integer goodsId = (Integer) table.getValueAt(selectedRows[i],1);
                            goodsTotal += goodsPrice * cartItemAmount;
                            jframe.orderItemService.insertOrderItem(orderItemId,goodsName,goodsPrice,goodsId,cartItemAmount,orderId);
                            jframe.cartItemService.deleteCartItem(itemId);
                            Integer amount = goodsInventory - cartItemAmount;
                            jframe.goodsService.updateGoods(amount,goodsId);
                        }
                        //调用线程，超过30分钟不买就删除待支付订单
                        jframe.service.schedule(new CancelOrderTak(orderId),30, TimeUnit.MINUTES);
                        JOptionPane.showMessageDialog(null,"订单生成成功" );
                        jFrameCartPage.setVisible(false);
                        cartPage();
                    }
                }
            }
        });

        JButton jborderpay = new JButton("立即购买");
        jborderpay.setBounds(350,300,100,50);
        jborderpay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要购买的商品数据","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    Integer orderId = jframe.userService.UUID();
                    //获取当前的日期
                    Date date = new Date();
                    //设置要获取到什么样的时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //获取String类型的时间
                    String orderCreate = sdf.format(date);

                    String orderAddress;
                    java.util.List<String> tableValueV = new ArrayList<>();
                    List<Map<String, Object>> mapList =  jframe.addressService.getAddress(jframe.user.getUserId());
                    if (mapList == null || mapList.size() == 0){
                        JOptionPane.showMessageDialog(null,"地址为空");
                        jFrameCartPage.setVisible(false);
                        jframe.addressService.insertAddress();
                    }else{
                        Iterator<Map<String,Object>> iterator = mapList.iterator();
                        while (iterator.hasNext()){
                            Map<String,Object> map = iterator.next();
                            tableValueV.add((String) map.get("address_alias"));
                        }
                        String[] addresss = new String[tableValueV.size()];
                        addresss=tableValueV.toArray(addresss);
                        orderAddress = (String) JOptionPane.showInputDialog(null, "请选择一个地址", "选择输入",
                                JOptionPane.INFORMATION_MESSAGE, null,
                                addresss,addresss[0]);
                        if (orderAddress == null){
                            orderAddress = "公司";
                        }
                        double goodsTotal = 0;
                        double fee = 5;

                        for (int i = 0;i<selectedRows.length;i++){
                            //商品单价
                            Double goodsPrice = (Double) table.getValueAt(selectedRows[i],5);
                            //购买数量
                            Integer cartItemAmount = (Integer) table.getValueAt(selectedRows[i],6);
                            goodsTotal += goodsPrice * cartItemAmount;
                        }
                        if (goodsTotal > 78){
                            fee = 0;
                        }
                        double priceTotal = goodsTotal + fee;


                        //判定用户的钱是否够
                        if (jframe.user.getMoney()>=priceTotal){
                            List<Couponitem> couponitemList = jframe.couponItemService.getCouponitem(jframe.user.getUserId());
                            if (couponitemList.size() != 0 || null != couponitemList){
                                String[] coupons = new String[couponitemList.size()];
                                int k = 0;
                                Iterator<Couponitem> couponitemIterator = couponitemList.iterator();
                                while (couponitemIterator.hasNext()){
                                    Couponitem couponitem = couponitemIterator.next();
                                    //如果需要支付总金额超过优惠券要求
                                    if (priceTotal >= couponitem.getCoupon().getFull()){
                                        coupons[k] ="满"+couponitem.getCoupon().getFull()+"减"+couponitem.getCoupon().getMinus();
                                        k++;
                                    }
                                }
                                if (coupons[0] != null){
                                    //弹出下拉菜单
                                    String coupon = (String) JOptionPane.showInputDialog(null, "请优惠券类型", "选择输入",
                                            JOptionPane.INFORMATION_MESSAGE, null,
                                            coupons,coupons[0]);
                                    if (coupon != null){
                                        char[] a = coupon.toCharArray();
                                        int m=coupon.indexOf('满');
                                        int j=coupon.indexOf('减');

                                        StringBuffer numone=new StringBuffer();
                                        StringBuffer numtwo=new StringBuffer();

                                        while(m+1<j){
                                            numone.append(a[m+1]);
                                            m++;
                                        }
                                        while(j+1<a.length){
                                            numtwo.append(a[j+1]);
                                            j++;
                                        }
                                        Double full =  Double.parseDouble(String.valueOf(numone));
                                        Double minus = Double.parseDouble(String.valueOf(numtwo));
                                        priceTotal -= minus;
                                        int o = 0;
                                        List<Couponitem> couponitemList1 = jframe.couponItemService.getCouponitem(jframe.user.getUserId());
                                        Iterator<Couponitem> couponitemIterator1 = couponitemList1.iterator();
                                        while (couponitemIterator1.hasNext()){
                                            Couponitem couponitem = couponitemIterator1.next();
                                            if (o==0 && full.equals(couponitem.getCoupon().getFull())&&minus.equals(couponitem.getCoupon().getMinus())){
                                                jframe.couponItemService.deleteCouponitem(couponitem.getCouponitemId());
                                                o++;
                                            }
                                        }
                                    }
                                }
                            }

                            Double newMoney = jframe.user.getMoney() - priceTotal;
                            jframe.userService.updateMoney(newMoney,jframe.user.getUserId());
                            jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                            Integer userId = jframe.user.getUserId();
                            String state = "pay";
                            jframe.orderService.insertOrder(orderId,orderCreate,orderAddress,goodsTotal,fee,priceTotal,userId,state);
                            for (int i = 0;i<selectedRows.length;i++){
                                //订单项目编号
                                Integer orderItemId = jframe.userService.UUID();
                                //商品名
                                String goodsName = (String) table.getValueAt(selectedRows[i],4);
                                //商品单价
                                Double goodsPrice = (Double) table.getValueAt(selectedRows[i],5);
                                //购物车编号
                                Integer itemId = (Integer) table.getValueAt(selectedRows[i],0);
                                //库存数量
                                Integer goodsInventory = (Integer) table.getValueAt(selectedRows[i],3);
                                //购买数量
                                Integer cartItemAmount = (Integer) table.getValueAt(selectedRows[i],6);
                                //商品编号
                                Integer goodsId = (Integer) table.getValueAt(selectedRows[i],1);
                                goodsTotal += goodsPrice * cartItemAmount;
                                jframe.orderItemService.insertOrderItem(orderItemId,goodsName,goodsPrice,goodsId,cartItemAmount,orderId);
                                jframe.cartItemService.deleteCartItem(itemId);
                                Integer amount = goodsInventory - cartItemAmount;
                                jframe.goodsService.updateGoods(amount,goodsId);
                            }
                            JOptionPane.showMessageDialog(null,"订单生成成功" );
                            jFrameCartPage.setVisible(false);
                            cartPage();
                        }else{
                            JOptionPane.showMessageDialog(null,"账户余额不支持支付该笔订单，请先充值","错误",JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        });

        JButton jbCoupon = new JButton("领取优惠券");
        jbCoupon.setBounds(650,300,100,50);
        jbCoupon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Map<String,Object>> mapList = jframe.couponService.getAllCoupon();
                Iterator<Map<String,Object>> mapIterator = mapList.iterator();
                String[] coupons = new String[mapList.size()];
                int i = 0;
                while (mapIterator.hasNext()){
                    Map<String,Object> map = mapIterator.next();
                    coupons[i] = "满"+map.get("full")+"减"+map.get("minus");
                    i++;
                }
                //弹出下拉菜单
                String coupon = (String) JOptionPane.showInputDialog(null, "请优惠券类型", "选择输入",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        coupons,coupons[0]);
                //判定是否取消
                if (coupon != null){
                  char[] a = coupon.toCharArray();
                    int m=coupon.indexOf('满');
                    int j=coupon.indexOf('减');

                    StringBuffer numone=new StringBuffer();
                    StringBuffer numtwo=new StringBuffer();

                    while(m+1<j){
                        numone.append(a[m+1]);
                        m++;
                    }
                    while(j+1<a.length){
                        numtwo.append(a[j+1]);
                        j++;
                    }
                    Double full =  Double.parseDouble(String.valueOf(numone));
                    Double minus = Double.parseDouble(String.valueOf(numtwo));

                    Integer couponitemId = jframe.userService.UUID();
                    Integer couponId = jframe.couponService.getcouponId(full,minus);
                    Integer userId = jframe.user.getUserId();

                    jframe.couponItemService.insertCouponitem(couponitemId,couponId,userId);
                    JOptionPane.showMessageDialog(null,"领取成功");
                }else{
                    JOptionPane.showMessageDialog(null,"领取失败","错误",JOptionPane.ERROR_MESSAGE );
                }
            }
        });

        JButton selectAllButton = new JButton("全部选择");
        selectAllButton.setBounds(50,400,100,50);
        selectAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.selectAll();// 选中所有行
            }
        });

        JButton clearSelectionButton = new JButton("取消选择");
        clearSelectionButton.setBounds(200,400,100,50);
        clearSelectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.clearSelection();// 取消所有选中行的选择状态
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(500,400,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameCartPage.setVisible(false);
                jframe.userService.ui();
            }
        });

        container.add(table);
        container.add(insertGoods);
        container.add(deleteGoods);
        container.add(updateAmount);
        container.add(jbCoupon);
        container.add(jborder);
        container.add(selectAllButton);
        container.add(clearSelectionButton);
        container.add(jbreturn);
        container.add(jborderpay);

        jFrameCartPage.setVisible(true);
        jFrameCartPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameCartPage.addWindowListener(new WindowListener() {
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
     * 添加购物车
     * @param goodsName
     */
    public void insertCartItem(String goodsName){
        JFrame jFrameInsertCartItem = new JFrame();
        jFrameInsertCartItem.setTitle("添加商品");
        jFrameInsertCartItem.setLayout(null);
        jFrameInsertCartItem.setSize(800,500);
        Container container = jFrameInsertCartItem.getContentPane();
        //遍历列名
        String[] columnNames = { "商品编号", "商品名称", "商品单价", "商品库存"};
        Vector<String> columnNameV = new Vector<>();
        for (int column = 0; column < columnNames.length; column++) {
            columnNameV.add(columnNames[column]);
        }
        //遍历商品数据
        Vector<Vector<Object>> tableValueV = new Vector<>();
        List<Goods> mapList;
        if (goodsName == null){
            mapList =  jframe.goodsService.getAllGoods();
        }else{
            mapList =  jframe.goodsService.getGoodsWithLikeName(goodsName);
        }
        Iterator<Goods> iterator = mapList.iterator();
        while (iterator.hasNext()){
            Goods goods = iterator.next();
            Vector<Object> rowV = new Vector<>();
            rowV.add(goods.getGoodsId());
            rowV.add(goods.getGoodsName());
            rowV.add(goods.getGoodsPrice());
            rowV.add(goods.getGoodsInventory());
            tableValueV.add(rowV);
        }
        JTable table = new JTable(tableValueV, columnNameV){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table.setBounds(10,10,800,200);

        JButton insertCartItem = new JButton("加入购物车");
        insertCartItem.setBounds(50,400,100,50);
        insertCartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                int itemId = 0;
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要购买的商品","错误",JOptionPane.ERROR_MESSAGE );
                }else if (selectedRows.length > 1){
                    JOptionPane.showMessageDialog(null,"购买商品一次只能一条","错误",JOptionPane.ERROR_MESSAGE );
                }else {
                    //用户输入的数量
                    String num = JOptionPane.showInputDialog("请输入购买的数量:");
                    if (num == null) {
                        JOptionPane.showMessageDialog(null, "购物取消", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //商品库存的数量
                        Integer goodsInventory = (Integer) table.getValueAt(selectedRows[0], 3);
                        Integer goodsId = (Integer) table.getValueAt(selectedRows[0], 0);
                        Integer amount = Integer.parseInt(num);
                        if (amount <= 0) {
                            JOptionPane.showMessageDialog(null, "购买的数量不能低于或者等于0", "错误", JOptionPane.ERROR_MESSAGE);
                            //判定输入的数量是否超过商品库存的数量
                        } else if (amount > goodsInventory) {
                            JOptionPane.showMessageDialog(null, "购买的数量超过库存", "错误", JOptionPane.ERROR_MESSAGE);
                        } else {
                            List<CartItem> mapItemList = jframe.cartItemService.getAllCartItem(jframe.user.getUserId());
                            //判定用户下面是否有购物车商品
                            if (mapItemList.size() > 0) {
                                Iterator<CartItem> iterator = mapItemList.iterator();
                                int sumamount = 0;
                                while (iterator.hasNext()) {
                                    CartItem cartItem = iterator.next();
                                    int id = cartItem.getGoods().getGoodsId();
                                    //判定购物车中的商品编号和需要购买的商品编号是否相同，如果相同则计算合计数量
                                    if (id == goodsId) {
                                        sumamount += cartItem.getCartItemAmount();
                                        itemId = cartItem.getCartItemId();
                                    }
                                }
                                //如果合计数量大于0，则表示购物车有这个商品
                                if (sumamount > 0) {
                                    //购物车商品的数量加上需要购物的商品数量
                                    sumamount += amount;
                                    //如果超过商品库存
                                    if (sumamount > goodsInventory) {
                                        //则更新购物车商品数量达到库存上限
                                        jframe.cartItemService.updateCartItem(goodsInventory, itemId);
                                        JOptionPane.showMessageDialog(null, "加入购物车成功,数量超过商品库存上限，默认最大值");
                                        jFrameInsertCartItem.setVisible(false);
                                        insertCartItem(null);
                                    } else {
                                        //不超过商品库存就更新商品数量
                                        jframe.cartItemService.updateCartItem(sumamount, itemId);
                                        JOptionPane.showMessageDialog(null, "加入购物车成功");
                                        jFrameInsertCartItem.setVisible(false);
                                        insertCartItem(null);
                                    }
                                } else {
                                    //如果购物车没有该商品则创建
                                    Integer uitemId = jframe.userService.UUID();
                                    Integer cartId = jframe.user.getCart().getCartId();
                                    jframe.cartItemService.insertCartItem(uitemId, goodsId, cartId, amount);
                                    JOptionPane.showMessageDialog(null, "加入购物车成功");
                                    jFrameInsertCartItem.setVisible(false);
                                    insertCartItem(null);
                                }
                            } else {
                                //如果购物车没有商品则创建
                                Integer uitemId = jframe.userService.UUID();
                                Integer cartId = jframe.user.getCart().getCartId();
                                jframe.cartItemService.insertCartItem(uitemId, goodsId, cartId, amount);
                                JOptionPane.showMessageDialog(null, "加入购物车成功");
                                jFrameInsertCartItem.setVisible(false);
                                insertCartItem(null);
                            }
                        }
                    }
                }
            }
        });

        JButton findGoods = new JButton("查找商品");
        findGoods.setBounds(200,400,100,50);
        findGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goodsName = JOptionPane.showInputDialog("请输入商品名");
                jFrameInsertCartItem.setVisible(false);
                insertCartItem(goodsName);
            }
        });

        JButton allGoods = new JButton("显示所有商品商品");
        allGoods.setBounds(350,400,100,50);
        allGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameInsertCartItem.setVisible(false);
                insertCartItem(null);
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(500,400,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameInsertCartItem.setVisible(false);
                cartPage();
            }
        });


        container.add(table);
        container.add(insertCartItem);
        container.add(findGoods);
        container.add(allGoods);
        container.add(jbreturn);

        jFrameInsertCartItem.setVisible(true);
        jFrameInsertCartItem.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameInsertCartItem.addWindowListener(new WindowListener() {
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
