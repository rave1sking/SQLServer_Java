package GUI;
import DBCconnection.DBCconnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Addgoods extends JFrame{
    JTextField g_no,g_price,g_n;
    JButton sure, exit;
    public Addgoods() {
            super("添加货物记录");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(300, 400);
            this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
            setVisible(true);

            JPanel frame = new JPanel();
            frame.setBackground(Color.PINK);
            this.add(frame);

            JLabel gg_no = new JLabel("编号");
            JLabel gg_price = new JLabel("单价");
            JLabel gg_n = new JLabel("名称");
            g_no = new JTextField();
            g_price = new JTextField();
            g_n = new JTextField();

            sure = new JButton("确定");
            exit = new JButton("退出");

            gg_no.setBounds(48, 46, 60, 40);
            gg_no.setFont(new Font("楷体", Font.BOLD, 17));
            g_no.setBounds(100, 50, 120, 30);

            gg_price.setBounds(44, 100, 120, 30);
            gg_price.setFont(new Font("楷体", Font.BOLD, 17));
            g_price.setBounds(100, 100, 120, 30);

            gg_n.setBounds(48,150,120,30);
            gg_n.setFont(new Font("楷体",Font.BOLD,17));
            g_n.setBounds(100,150,120,30);
            sure.setBounds(50, 280, 70, 30);
            exit.setBounds(200, 280, 70, 30);

            frame.setLayout(null);
            frame.add(gg_no);
            frame.add(g_no);
            frame.add(gg_price);
            frame.add(g_price);
            frame.add(gg_n);
            frame.add(g_n);
            frame.add(sure);
            frame.add(exit);

            sure.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String s1 = g_no.getText().trim();
                    String s2 = g_price.getText().trim();
                    String s3 = g_n.getText().trim();
                    DBCconnection Jcon = new DBCconnection();
                    Jcon.getConnection();
                    //创建SQL语句执行对象
                    String strSQL = "insert into dbo.goods(g_no,g_price,g_n) values('" + s1 + "','" + s2 + "','" + s3 + "') ";

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
