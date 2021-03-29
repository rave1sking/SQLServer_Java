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
public class AddTS extends JFrame{
    JTextField Ts_no,Ts_name;
    JButton sure,exit;
    public AddTS()
    {
        super("添加师生记录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel Tss_no=new JLabel("编号");
        JLabel Tss_name=new JLabel("名称");

        Ts_no = new JTextField();
        Ts_name = new JTextField();

        sure = new JButton("确定");
        exit = new JButton("退出");

        Tss_no.setBounds(48, 46, 60, 40);
        Tss_no.setFont(new Font("楷体",Font.BOLD,17));
        Ts_no.setBounds(100,50,120,30);

        Tss_name.setBounds(44,100,120,30);
        Tss_name.setFont(new Font("楷体",Font.BOLD,17));
        Ts_name.setBounds(100,100,120,30);

        sure.setBounds(50,180,70,30);
        exit.setBounds(200,180,70,30);

        frame.setLayout(null);
        frame.add(Tss_name);
        frame.add(Ts_name);
        frame.add(Tss_no);
        frame.add(Ts_no);
        frame.add(sure);
        frame.add(exit);

        sure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s1=Ts_no.getText().trim();
                String s2=Ts_name.getText().trim();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                String strSQL = "insert into dbo.TS(TS_no,TS_name) values('"+s1+"','"+s2+"')";

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
