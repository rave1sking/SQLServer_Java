package GUI;
import DBCconnection.DBCconnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Sales extends JFrame{
    JTextField s_no,g_no,time,b_price,b_number;
    JButton sure, exit;
    public Sales() {
        super("添加进货记录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
        setVisible(true);

        JPanel frame = new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel ss_no = new JLabel("师生编号");
        JLabel gg_no = new JLabel("物资编号");
        JLabel timee = new JLabel("时间");
        JLabel bb_price =new JLabel("单价");
        JLabel bb_number = new JLabel("数量");

        s_no = new JTextField();
        g_no = new JTextField();
        time = new JTextField();
        b_price = new JTextField();
        b_number = new JTextField();

        sure = new JButton("确定");
        exit = new JButton("退出");

        ss_no.setBounds(30, 50, 120, 40);
        ss_no.setFont(new Font("楷体", Font.BOLD, 17));
        s_no.setBounds(160, 50, 150, 30);

        gg_no.setBounds(30, 100, 120, 40);
        gg_no.setFont(new Font("楷体", Font.BOLD, 17));
        g_no.setBounds(160, 100, 150, 30);

        timee.setBounds(30, 150, 120, 40);
        timee.setFont(new Font("楷体", Font.BOLD, 17));
        time.setBounds(160, 150, 150, 30);

        bb_price.setBounds(30,200,120,30);
        bb_price.setFont(new Font("楷体",Font.BOLD,17));
        b_price.setBounds(160,200,150,30);

        bb_number.setBounds(30,250,120,30);
        bb_number.setFont(new Font("楷体",Font.BOLD,17));
        b_number.setBounds(160,250,150,30);


        sure.setBounds(50, 300, 70, 30);
        exit.setBounds(200, 300, 70, 30);

        frame.setLayout(null);
        frame.add(ss_no);
        frame.add(s_no);
        frame.add(gg_no);
        frame.add(g_no);
        frame.add(timee);
        frame.add(time);
        frame.add(bb_price);
        frame.add(b_price);
        frame.add(bb_number);
        frame.add(b_number);

        frame.add(sure);
        frame.add(exit);

        sure.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s1 = s_no.getText().trim();
                String s2 = g_no.getText().trim();
                String s3 = time.getText().trim();
                String s4 = b_price.getText().trim();
                String s5 = b_number.getText().trim();

                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                String strSQL = "insert into dbo.sales(ts_no,g_no,times,sale_price,sale_number) values('" + s1 + "','" + s2 + "','" + s3 + "','" + s4 + "','" + s5 + "') ";
                String strSQL2 = "update dbo.warehouse set w_remain =  w_remain + "+s5+" ";

                PreparedStatement pst = null;
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
                try {
                    pst = Jcon.conn.prepareStatement(strSQL2, Statement.RETURN_GENERATED_KEYS);
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

                /////
                try {
                    DBCconnection Jcon1 = new DBCconnection();
                    Statement st= Jcon1.conn.createStatement();

                    String strSQL4 = "update store set num = num - "+s5+" where g_no = "+s2+" ";
                    st.executeUpdate(strSQL4);
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                /////
                JOptionPane.showMessageDialog(null, "添加成功");

            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
    public void closeThis ()//关闭当前界面
    {
        this.dispose();
    }
}
