package com.cheer.shopping.service;

import com.cheer.shopping.model.Order;
import com.cheer.shopping.model.OrderItem;
import com.cheer.shopping.util.OrderMapperImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;
import java.util.List;

public class OrderService {
    //创建公用实例对象
    OrderMapperImpl orderMapperImpl = new OrderMapperImpl();
    Jframe jframe = new Jframe();
    //插入订单
    public void insertOrder(Integer orderId, String orderCreate, String orderAddress, Double goodsTotal, Double fee, Double priceTotal, Integer userId,String state) {
        orderMapperImpl.insertOrder(orderId,orderCreate,orderAddress,goodsTotal,fee,priceTotal,userId,state);
    }
    //获得所有订单
    public List<Order> getOrder(Integer userId){
        List<Order> orderList =  orderMapperImpl.getOrder(userId);
        return orderList;
    }
    //获得总金额
    public Double getPriceTotal(Integer orderId) {
        Double priceTotal = orderMapperImpl.getPriceTotal(orderId);
        return priceTotal;
    }
    //更新状态
    public void updateState(Integer orderId) {
        orderMapperImpl.updateState(orderId);
    }
    //删除订单
    public void deleteorder(Integer orderId) {
        orderMapperImpl.deleteorder(orderId);
    }
    //获得订单状态
    public String getState(Integer orderId) {
        String state = orderMapperImpl.getState(orderId);
        return state;
    }
    /**
     * 订单管理
     */
    public void order(){
        JFrame jFrameOrder = new JFrame();
        jFrameOrder.setTitle("订单管理");
        jFrameOrder.setLayout(null);
        jFrameOrder.setSize(850,500);
        Container container = jFrameOrder.getContentPane();
        //创建表格列名
        String[] columnNames = { "订单编号", "订单项目编号" ,  "商品编号", "创建时间", "收货地址", "商品名", "商品单价", "商品数量","合计金额", "运费", "总金额","订单状态"};
        Vector<String> columnNameV = new Vector<>();
        for (int column = 0; column < columnNames.length; column++) {
            columnNameV.add(columnNames[column]);
        }
        //创建表格数据
        Vector<Vector<Object>> tableValueV = new Vector<>();
        List<Order> orderList =  getOrder(jframe.user.getUserId());
        Iterator<Order> iterator = orderList.iterator();
        while (iterator.hasNext()){
            Order order = iterator.next();
            Iterator<OrderItem> orderItemIterator = order.getOrderItemList().iterator();
            while (orderItemIterator.hasNext()){
                OrderItem orderItem = orderItemIterator.next();
                Vector<Object> rowV = new Vector<>();
                rowV.add(order.getOrderId());
                rowV.add(orderItem.getOrderItemId());
                rowV.add(orderItem.getGoodsId());
                rowV.add(order.getOrderCreate());
                rowV.add(order.getAddress());
                rowV.add(orderItem.getGoodsName());
                rowV.add(orderItem.getGoodsPrice());
                rowV.add(orderItem.getGoodsInventory());
                rowV.add(order.getGoodsTotal());
                rowV.add(order.getFee());
                rowV.add(order.getPriceTotal());
                rowV.add(order.getState());

                tableValueV.add(rowV);
            }
        }
        JTable table = new JTable(tableValueV, columnNameV){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table.setBounds(10,10,800,200);

        JButton selectAllButton = new JButton("全部选择");
        selectAllButton.setBounds(50,300,100,50);
        selectAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.selectAll();// 选中所有行
            }
        });

        JButton clearSelectionButton = new JButton("取消选择");
        clearSelectionButton.setBounds(200,300,100,50);
        clearSelectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.clearSelection();// 取消所有选中行的选择状态
            }
        });


        //定义删除按钮
        JButton jbdelete = new JButton("删除订单");
        jbdelete.setBounds(350,300,100,50);
        jbdelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要删除的订单","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    List<Integer> listPay = new ArrayList<>();
                    List<Integer> listNopay = new ArrayList<>();
                    for (int i = 0;i<selectedRows.length;i++){
                        //订单状态
                        String state = (String) table.getValueAt(selectedRows[i],11);
                        //统计已支付订单号
                        if (state.equals("pay")){
                            Integer orderId = (int) table.getValueAt(selectedRows[i],0);
                            if (!listPay.contains(orderId)){
                                listPay.add(orderId);
                            }
                            //统计未支付订单号
                        }else if (state.equals("nopay")){
                            Integer orderId = (int) table.getValueAt(selectedRows[i],0);
                            if (!listNopay.contains(orderId)){
                                listNopay.add(orderId);
                            }
                        }
                    }
                    //存放项目号和商品名称和商品数量到Map中
                    List<Map<String,Object>> mapList = new ArrayList<>();
                    Double sum = 0.0;
                    for (int i = 0;i<listPay.size();i++){
                        List<Map<String,Object>> maps = jframe.orderItemService.getNameAndInventory(listPay.get(i));
                        Iterator<Map<String,Object>> mapIterator = maps.iterator();
                        while (mapIterator.hasNext()){
                            Map<String,Object> map = mapIterator.next();
                            mapList.add(map);
                        }
                        Double priceTotal = getPriceTotal(listPay.get(i));
                        //统计已支付总金额
                        sum+=priceTotal;
                    }
                    for (int i = 0;i<listNopay.size();i++){
                        List<Map<String,Object>> maps = jframe.orderItemService.getNameAndInventory(listNopay.get(i));
                        Iterator<Map<String,Object>> mapIterator = maps.iterator();
                        while (mapIterator.hasNext()){
                            Map<String,Object> map = mapIterator.next();
                            mapList.add(map);
                        }
                    }
                    //遍历项目号和商品名称和商品数量
                    Iterator<Map<String,Object>> mapIterator = mapList.iterator();
                    while (mapIterator.hasNext()){
                        Map<String,Object> map = mapIterator.next();
                        String goodsName = (String) map.get("goods_name");
                        Integer goodsInventory = (Integer) map.get("goods_Inventory");
                        Integer orderItemId = (Integer) map.get("orderitem_id");
                        //通过商品名称获得商品库存
                        Integer inventory = jframe.goodsService.getinventory(goodsName);
                        //库存加上商品数量
                        inventory +=goodsInventory;
                        //更新库存
                        jframe.goodsService.updateinventory(inventory,goodsName);
                        //删除订单项目
                        jframe.orderItemService.deleteOrderItem(orderItemId);
                    }
                    //删除已支付订单
                    for (int i = 0;i<listPay.size();i++){
                        deleteorder(listPay.get(i));
                    }
                    //删除未支付订单
                    for (int i = 0;i<listNopay.size();i++){
                        deleteorder(listNopay.get(i));
                    }
                    //判定是否有已支付金额
                    if (sum > 0.0){
                        //获得用户现有金额
                        Double money = jframe.user.getMoney();
                        //合计
                        sum+=money;
                        //退款
                        jframe.userService.updateMoney(sum,jframe.user.getUserId());
                        jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                    }
                    JOptionPane.showMessageDialog(null,"删除成功");
                    jFrameOrder.setVisible(false);
                    order();
                }
            }
        });

        //定义支付按钮
        JButton jbalipay = new JButton("支付");
        jbalipay.setBounds(500,300,100,50);
        jbalipay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                boolean onoff = true;
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要支付的订单","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0;i<selectedRows.length;i++){
                        //订单状态
                        String state = (String) table.getValueAt(selectedRows[i],11);
                        if (state.equals("pay")){
                            JOptionPane.showMessageDialog(null,"选中商品中有已支付订单","错误",JOptionPane.ERROR_MESSAGE );
                            onoff = false;
                        }
                        Integer orderId = (int) table.getValueAt(selectedRows[i],0);
                        if(!list.contains(orderId)){
                            list.add(orderId);
                        }
                    }
                    if (onoff){
                        for (int i = 0;i<list.size();i++){
                            Double priceTotal = getPriceTotal(list.get(i));
                            if (jframe.user.getMoney()>=priceTotal){
                                Double newMoney = jframe.user.getMoney() - priceTotal;
                                jframe.user.setMoney(newMoney);
                                jframe.userService.updateMoney(newMoney,jframe.user.getUserId());
                                updateState(list.get(i));
                            }else{
                                JOptionPane.showMessageDialog(null,"账户余额不支持支付余下订单","错误",JOptionPane.ERROR_MESSAGE );
                                break;
                            }
                        }
                        jFrameOrder.setVisible(false);
                        order();
                    }
                }
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(650,300,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameOrder.setVisible(false);
                jframe.userService.userPage();
            }
        });

        container.add(table);
        container.add(jbreturn);
        container.add(selectAllButton);
        container.add(clearSelectionButton);
        container.add(jbalipay);
        container.add(jbdelete);

        jFrameOrder.setVisible(true);
        jFrameOrder.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameOrder.addWindowListener(new WindowListener() {
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
