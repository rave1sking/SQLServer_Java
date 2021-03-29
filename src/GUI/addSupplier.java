package GUI;
import DBCconnection.DBCconnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class addSupplier extends JFrame{
    private static final long serialVersionUID = 1L;
    JTextField s_no,s_n,s_dir,s_introduction;
    JButton sure,exit;
    public addSupplier()
    {
        super("添加供应商记录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel ss_no=new JLabel("编号");
        JLabel ss_n=new JLabel("名称");
        JLabel ss_dir=new JLabel("负责人");
        JLabel ss_introduction=new JLabel("简介");

        s_no = new JTextField();
        s_n = new JTextField();
        s_dir = new JTextField();
        s_introduction = new JTextField();

        sure = new JButton("确定");
        exit = new JButton("退出");

        ss_no.setBounds(48, 46, 60, 40);
        ss_no.setFont(new Font("楷体",Font.BOLD,17));
        s_no.setBounds(100,50,120,30);

        ss_n.setBounds(44,100,120,30);
        ss_n.setFont(new Font("楷体",Font.BOLD,17));
        s_n.setBounds(100,100,120,30);

        ss_dir.setBounds(44,150,120,30);
        ss_dir.setFont(new Font("楷体",Font.BOLD,17));
        s_dir.setBounds(100,150,120,30);

        ss_introduction.setBounds(44,200,120,30);
        ss_introduction.setFont(new Font("楷体",Font.BOLD,17));
        s_introduction.setBounds(100,200,120,30);

        sure.setBounds(150,380,70,30);
        exit.setBounds(250,380,70,30);


        frame.setLayout(null);
        frame.add(ss_no);
        frame.add(s_no);

        frame.add(ss_n);
        frame.add(s_n);

        frame.add(ss_dir);
        frame.add(s_dir);

        frame.add(ss_introduction);
        frame.add(s_introduction);

        frame.add(sure);
        frame.add(exit);

        sure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s1=s_no.getText().trim();
                String s2=s_n.getText().trim();
                String s3=s_dir.getText().trim();
                String s4=s_introduction.getText();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                String strSQL = "insert into dbo.supplier(s_no,s_n,s_dir,introduction) values('"+s1+"','"+s2+"','"+s3+"', '"+s4+"')";

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
                JOptionPane.showMessageDialog(null,"添加成功");


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
