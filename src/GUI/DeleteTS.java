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
public class DeleteTS extends JFrame{
    private static final long serialVersionUID = 1L;
    JTextField Delete_TS_no;
    JButton sure,exit;
    public DeleteTS(){
        super("删除师生信息");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
        this.setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        Delete_TS_no = new JTextField();
        JLabel tss_no=new JLabel("编号");
        sure = new JButton("确定");
        exit = new JButton("退出");

        tss_no.setBounds(50, 100, 80, 40);
        tss_no.setFont(new Font("楷体",Font.BOLD,17));
        Delete_TS_no.setBounds(100,100,120,30);

        sure.setBounds(50,180,70,30);
        exit.setBounds(200,180,70,30);

        frame.setLayout(null);
        frame.add(tss_no);
        frame.add(Delete_TS_no);
        frame.add(sure);
        frame.add(exit);

        sure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s=Delete_TS_no.getText().trim();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                String strSQL = "delete from dbo.TS where Ts_no = '"+s+"'";

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
