package com.cheer.shopping.service;

import com.cheer.shopping.util.AddressMapperImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AddressService {
    //创建公用实例对象
    AddressMapperImpl addressMapperImpl = new AddressMapperImpl();
    Jframe jframe = new Jframe();

    //添加地址
    public void insertAddress(String addressAddressee,String addressDetailed,String addressPhone,String addressTelephone,String addressMailbox,String addressAlias,Integer userId){
        Integer uuid = jframe.userService.UUID();
        addressMapperImpl.insertAddress(uuid, addressAddressee, addressDetailed, addressPhone, addressTelephone,addressMailbox,addressAlias,userId);
    }
    //更新地址
    public void updateAddress(String addressAddressee,String addressDetailed,String addressPhone,String addressTelephone,String addressMailbox,String addressAlias,Integer addressId){
        addressMapperImpl.updateAddress(addressAddressee,addressDetailed,addressPhone,addressTelephone,addressMailbox,addressAlias,addressId);
    }
    //删除地址
    public void deleteAddress(Integer addressId){
        addressMapperImpl.deleteAddress(addressId);
    }
    public List<Map<String, Object>> getAddress(Integer userId){
        List<Map<String, Object>> addressList = addressMapperImpl.getAddress(userId);
        return addressList;
    }

    /**
     * 地址管理
     */
    public void userAddress(){
        JFrame jFrameUserAddress = new JFrame();
        jFrameUserAddress.setTitle("地址管理");
        jFrameUserAddress.setLayout(null);
        jFrameUserAddress.setSize(800,500);
        Container container = jFrameUserAddress.getContentPane();
        //遍历表格标题
        String[] columnNames = { "地址编号",  "收件人", "详细地址", "手机号码", "座机号码", "邮箱" , "别名"};
        Vector<String> columnNameV = new Vector<>();
        for (int column = 0; column < columnNames.length; column++) {
            columnNameV.add(columnNames[column]);
        }
        //遍历表格内容
        Vector<Vector<Object>> tableValueV = new Vector<>();
        List<Map<String, Object>> mapList =  getAddress(jframe.user.getUserId());
        Iterator<Map<String,Object>> iterator = mapList.iterator();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            Vector<Object> rowV = new Vector<>();
            rowV.add(map.get("address_id"));
            rowV.add(map.get("address_addressee"));
            rowV.add( map.get("address_detailed"));
            rowV.add( map.get("address_phone"));
            rowV.add(map.get("address_telephone"));
            rowV.add(map.get("address_mailbox"));
            rowV.add( map.get("address_alias"));
            tableValueV.add(rowV);
        }
        //控制第一列不能更改
        JTable table = new JTable(tableValueV, columnNameV){
            public boolean isCellEditable(int row,int column){
                if (column == 0){
                    return false;
                }else{
                    return true;
                }
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

        JButton createAddress = new JButton("添加地址");
        createAddress.setBounds(350,300,100,50);
        createAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUserAddress.setVisible(false);
                insertAddress();
            }
        });

        JButton deleteAddress = new JButton("删除地址");
        deleteAddress.setBounds(50,400,100,50);
        deleteAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要删除的地址数据","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    for (int i = 0;i<selectedRows.length;i++){
                        Integer addressId = (int) table.getValueAt(selectedRows[i],0);
                        deleteAddress(addressId);
                    }
                    jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                    JOptionPane.showMessageDialog(null,"删除成功");
                    jFrameUserAddress.setVisible(false);
                    userAddress();
                }
            }
        });

        JButton updateAddress = new JButton("修改地址");
        updateAddress.setBounds(200,400,100,50);
        updateAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length == 0){
                    JOptionPane.showMessageDialog(null,"请先选择需要修改的地址数据","错误",JOptionPane.ERROR_MESSAGE );
                }else if (selectedRows.length > 1){
                    JOptionPane.showMessageDialog(null,"修改数据一次只能一条","错误",JOptionPane.ERROR_MESSAGE );
                }else{
                    int addressId = (int) table.getValueAt(selectedRows[0],0);
                    String addressee = (String) table.getValueAt(selectedRows[0],2);
                    String detailed = (String) table.getValueAt(selectedRows[0],3);
                    String phone = (String) table.getValueAt(selectedRows[0],4);
                    String telephone= (String) table.getValueAt(selectedRows[0],5);
                    String mailbox = (String) table.getValueAt(selectedRows[0],6);
                    String alias = (String) table.getValueAt(selectedRows[0],7);
                    //控制输入的数据
                    String detailedregex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
                    String phoneregex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
                    String mailboxregex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                    String telephoneregex = "^[0][1-9]{2,3}-[0-9]{5,10}$";
                    if (addressee.length() == 0){
                        JOptionPane.showMessageDialog(null,"收件人不能为空","错误",JOptionPane.ERROR_MESSAGE );
                    }else if (detailed.length() == 0){
                        JOptionPane.showMessageDialog(null,"详细地址收件人不能为空","错误",JOptionPane.ERROR_MESSAGE );
                    }else if (phone.length() == 0 && telephone.length() == 0){
                        JOptionPane.showMessageDialog(null,"电话号码至少填写一种","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(!detailed.matches(detailedregex)){
                        JOptionPane.showMessageDialog(null,"地址信息错误，以省市区为单位","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(phone.length() != 0 && !phone.matches(phoneregex)){
                        JOptionPane.showMessageDialog(null,"手机号码错误","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(mailbox.length() != 0 && !mailbox.matches(mailboxregex)){
                        JOptionPane.showMessageDialog(null,"邮件信息错误","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(telephone.length() != 0 && !telephone.matches(telephoneregex)){
                        JOptionPane.showMessageDialog(null,"座机号码错误","错误",JOptionPane.ERROR_MESSAGE );
                    }else if(alias.length() == 0){
                        alias = addressee;
                        updateAddress(addressee,detailed,phone,telephone,mailbox,alias,addressId);
                        jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                        JOptionPane.showMessageDialog(null,"修改成功" );
                        jFrameUserAddress.setVisible(false);
                        userAddress();
                    }else{
                        updateAddress(addressee,detailed,phone,telephone,mailbox,alias,addressId);
                        jframe.user = jframe.userService.refresh(jframe.user.getUserId());
                        JOptionPane.showMessageDialog(null,"修改成功" );
                        jFrameUserAddress.setVisible(false);
                        userAddress();
                    }
                }
            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(350,400,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameUserAddress.setVisible(false);
                jframe.userService.userPage();
            }
        });

        container.add(table);
        container.add(selectAllButton);
        container.add(clearSelectionButton);
        container.add(createAddress);
        container.add(deleteAddress);
        container.add(updateAddress);
        container.add(jbreturn);

        jFrameUserAddress.setVisible(true);
        jFrameUserAddress.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameUserAddress.addWindowListener(new WindowListener() {
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
     * 添加地址
     */
    public void insertAddress(){
        JFrame jFrameInsertAddress = new JFrame();
        jFrameInsertAddress.setTitle("添加地址");
        jFrameInsertAddress.setLayout(null);
        jFrameInsertAddress.setSize(500,500);
        Container container = jFrameInsertAddress.getContentPane();

        //定义收件人标签
        JLabel jladdressee = new JLabel("收 件 人:");
        jladdressee.setBounds(100,30,100,50);

        //定义详细地址标签
        JLabel jldetailed = new JLabel("详细地址:");
        jldetailed.setBounds(100,80,100,50);

        //定义手机号码标签
        JLabel jlphone = new JLabel("手机号码:");
        jlphone.setBounds(100,130,100,50);

        //定义座机号码标签
        JLabel jltelephone = new JLabel("座机号码:");
        jltelephone.setBounds(100,180,100,50);

        //定义邮箱标签
        JLabel jlmailbox = new JLabel("邮    箱:");
        jlmailbox.setBounds(100,230,100,50);

        //定义别名标签
        JLabel jlalias = new JLabel("别    名:");
        jlalias.setBounds(100,280,100,50);

        //定义收件人文本
        JTextField jtaddressee = new JTextField();
        jtaddressee.setBounds(200,45,200,25);

        //定义详细地址文本
        JTextField jtdetailed = new JTextField();
        jtdetailed.setBounds(200,95,200,25);

        //定义手机号码文本
        JTextField jtphone = new JTextField();
        jtphone.setBounds(200,145,200,25);

        //定义座机号码文本
        JTextField jttelephone= new JTextField();
        jttelephone.setBounds(200,195,200,25);

        //定义邮箱文本
        JTextField jtmailbox = new JTextField();
        jtmailbox.setBounds(200,245,200,25);

        //定义别名文本
        JTextField jtalias = new JTextField();
        jtalias.setBounds(200,295,200,25);

        //定义添加按钮
        JButton jbcreate = new JButton("添加");
        jbcreate.setBounds(100,400,100,50);
        jbcreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addressee = jtaddressee.getText();
                String detailed = jtdetailed.getText();
                String phone = jtphone.getText();
                String telephone= jttelephone.getText();
                String mailbox = jtmailbox.getText();
                String alias = jtalias.getText();
                //控制输入的数据
                String detailedregex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
                String phoneregex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
                String mailboxregex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                String telephoneregex = "^[0][1-9]{2,3}-[0-9]{5,10}$";
                if (addressee.length() == 0){
                    JOptionPane.showMessageDialog(null,"收件人不能为空","错误",JOptionPane.ERROR_MESSAGE );
                }else if (detailed.length() == 0){
                    JOptionPane.showMessageDialog(null,"详细地址收件人不能为空","错误",JOptionPane.ERROR_MESSAGE );
                }else if (phone.length() == 0 && telephone.length() == 0){
                    JOptionPane.showMessageDialog(null,"电话号码至少填写一种","错误",JOptionPane.ERROR_MESSAGE );
                }else if(!detailed.matches(detailedregex)){
                    JOptionPane.showMessageDialog(null,"地址信息错误，以省市区为单位","错误",JOptionPane.ERROR_MESSAGE );
                }else if(phone.length() != 0 && !phone.matches(phoneregex)){
                    JOptionPane.showMessageDialog(null,"手机号码错误","错误",JOptionPane.ERROR_MESSAGE );
                }else if(mailbox.length() != 0 && !mailbox.matches(mailboxregex)){
                    JOptionPane.showMessageDialog(null,"邮件信息错误","错误",JOptionPane.ERROR_MESSAGE );
                }else if(telephone.length() != 0 && !telephone.matches(telephoneregex)){
                    JOptionPane.showMessageDialog(null,"座机号码错误","错误",JOptionPane.ERROR_MESSAGE );
                }else if(alias.length() == 0){
                    alias = addressee;
                    insertAddress(addressee,detailed,phone,telephone,mailbox,alias,jframe.user.getUserId());
                    JOptionPane.showMessageDialog(null,"添加成功");
                    jFrameInsertAddress.setVisible(false);
                    userAddress();
                }else{
                    insertAddress(addressee,detailed,phone,telephone,mailbox,alias,jframe.user.getUserId());
                    JOptionPane.showMessageDialog(null,"添加成功");
                    jFrameInsertAddress.setVisible(false);
                    userAddress();
                }

            }
        });

        //定义返回按钮
        JButton jbreturn = new JButton("返回");
        jbreturn.setBounds(300,400,100,50);
        jbreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameInsertAddress.setVisible(false);
                userAddress();
            }
        });

        container.add(jldetailed);
        container.add(jladdressee);
        container.add(jlphone);
        container.add(jltelephone);
        container.add(jlmailbox);
        container.add(jlalias);

        container.add(jtdetailed);
        container.add(jtaddressee);
        container.add(jtphone);
        container.add(jttelephone);
        container.add(jtmailbox);
        container.add(jtalias);

        container.add(jbcreate);
        container.add(jbreturn);

        jFrameInsertAddress.setVisible(true);
        jFrameInsertAddress.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //关闭线程
        jFrameInsertAddress.addWindowListener(new WindowListener() {
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
