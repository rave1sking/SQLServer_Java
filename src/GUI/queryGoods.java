package GUI;
import DBCconnection.DBCconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class queryGoods extends JFrame {
    private static final long serialVersionUID = 1L;
    JTextField s_no;
    JButton sure, exit;

    public queryGoods() {
        super("查询货物记录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(550, 250);
        this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
        setVisible(true);

        JPanel frame = new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel ss_no = new JLabel("编号");

        s_no = new JTextField();


        sure = new JButton("确定");
        exit = new JButton("重置");


        ss_no.setBounds(48, 46, 60, 40);
        ss_no.setFont(new Font("楷体", Font.BOLD, 17));
        s_no.setBounds(100, 50, 120, 30);


        sure.setBounds(250, 50, 70, 30);
        exit.setBounds(330, 50, 70, 30);


        frame.setLayout(null);
        frame.add(ss_no);
        frame.add(s_no);

        frame.add(sure);
        frame.add(exit);

        String[] titles = {"货物编号", "单价","名称"};
        String[][] datas = {};
        DefaultTableModel myModel = new DefaultTableModel(datas, titles);
        JTable table = new JTable(myModel);
        table.setPreferredScrollableViewportSize(new Dimension(550, 40));
        table.setBounds(50, 120, 400, 40);
        //行高
        table.setRowHeight(40);

        frame.add(table);
        sure.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s1 = s_no.getText().trim();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                //创建SQL语句执行对象
                try {
                    Statement st = Jcon.conn.createStatement();
                    String strSQL = "select * from goods where g_no = '" + s1 + "' ";
                    ResultSet rs = st.executeQuery(strSQL);
                    while (rs.next()) {
                        Vector<String> ve = new Vector<String>();
                        ve.addElement(rs.getString(1));
                        ve.addElement(rs.getString(2));
                        ve.addElement(rs.getString(3));
                        myModel.addRow(ve);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                while (myModel.getRowCount() > 0) {
                    myModel.removeRow(myModel.getRowCount() - 1);
                }
                s_no.setText(null);
            }
        });
    }

    public void closeThis()//关闭当前界面
    {
        this.dispose();
    }
}