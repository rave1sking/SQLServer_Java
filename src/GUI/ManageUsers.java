package GUI;

import DBCconnection.DBCconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageUsers extends JFrame {
    private static final long serialVersionUID = 1L;
    JTextField Delete_s_no;
    JButton sure,exit;
    public ManageUsers() throws SQLException {
        super("管理用户");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,500);
        this.setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        Delete_s_no = new JTextField();
        JLabel ss_no=new JLabel("编号");
        sure = new JButton("确定");
        exit = new JButton("退出");

        ss_no.setBounds(50, 50, 80, 40);
        ss_no.setFont(new Font("楷体",Font.BOLD,17));
        Delete_s_no.setBounds(150,50,120,30);

        sure.setBounds(60,100,70,30);
        exit.setBounds(200,100,70,30);

        ///
        String[] titles = { "用户编号"};
        String[][] datas = {};
        //myModel存放表格数据
        DefaultTableModel myModel = new DefaultTableModel(datas, titles);
        JTable table = new JTable(myModel);
        //设置表格对象JTable table1的规模
        table.setPreferredScrollableViewportSize(new Dimension(250, 150));
        //设置滚动面板
        JScrollPane scrollPane1 = new JScrollPane(table);
        //行高
        table.setRowHeight(20);
        String strSQL = "(Select user_no from dbo.Users)";
        DBCconnection Jcon = new DBCconnection();
        Jcon.getConnection();
        Statement st = Jcon.conn.createStatement();
        ResultSet rs = st.executeQuery(strSQL);
        while (rs.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs.getString(1));
            myModel.addRow(ve);
        }

        scrollPane1.setBounds(65, 200, 150, 150);
        ///

        frame.setLayout(null);
        frame.add(ss_no);
        frame.add(Delete_s_no);
        frame.add(sure);
        frame.add(exit);
        frame.add(scrollPane1);

        sure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s=Delete_s_no.getText().trim();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                String strSQL = "delete from dbo.users where user_no = '"+s+"'";

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
