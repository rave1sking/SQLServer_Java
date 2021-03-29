package GUI;
import DBCconnection.DBCconnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Deletebuy extends JFrame{
    private static final long serialVersionUID = 1L;
    JTextField Delete_s_no,Delete_g_no,Delete_time;
    JButton sure,exit;
    public Deletebuy(){
        super("删除订货信息");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        Delete_s_no = new JTextField();
        JLabel ss_no=new JLabel("商家编号");

        Delete_g_no = new JTextField();
        JLabel gg_no = new JLabel("货物编号");

        Delete_time = new JTextField();
        JLabel timee = new JLabel("进货时间");



        sure = new JButton("确定");
        exit = new JButton("退出");

        ss_no.setBounds(50, 100, 120, 40);
        ss_no.setFont(new Font("楷体",Font.BOLD,17));
        Delete_s_no.setBounds(180,100,120,30);

        gg_no.setBounds(50, 150, 120, 40);
        gg_no.setFont(new Font("楷体",Font.BOLD,17));
        Delete_g_no.setBounds(180,150,120,30);

        timee.setBounds(50, 200, 120, 40);
        timee.setFont(new Font("楷体",Font.BOLD,17));
        Delete_time.setBounds(180,200,120,30);

        sure.setBounds(50,280,70,30);
        exit.setBounds(200,280,70,30);

        frame.setLayout(null);
        frame.add(ss_no);
        frame.add(Delete_s_no);
        frame.add(gg_no);
        frame.add(Delete_g_no);
        frame.add(timee);
        frame.add(Delete_time);

        frame.add(sure);
        frame.add(exit);

        sure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s1=Delete_s_no.getText().trim();
                String s2=Delete_g_no.getText().trim();
                String s3=Delete_time.getText().trim();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                PreparedStatement pst = null;
                String strSQL1 = "update warehouse set w_remain = w_remain + (select b_number from buy where s_no = '"+s1+"' and g_no = '"+s2+"' and times = '"+s3+"') ";
                try {
                    pst = Jcon.conn.prepareStatement(strSQL1, Statement.RETURN_GENERATED_KEYS);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String strSQL = "delete from dbo.buy where s_no = '"+s1+"' and g_no = '"+s2+"' and times = '"+s3+"' ";

                try {
                    pst = Jcon.conn.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    pst.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //此处有错

                JOptionPane.showMessageDialog(null,"删除成功");
            }
        });

        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                closeThis();
                try {
                    new Management();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public  void closeThis()//关闭当前界面
    {
        this.dispose();
    }

}

