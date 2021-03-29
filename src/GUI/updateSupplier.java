package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import DBCconnection.DBCconnection;
public class updateSupplier extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public updateSupplier() {
        super("修改供应商信息");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel j=new JLabel("编号:");  j.setFont(new Font("楷体",Font.PLAIN,20));//设置字体的字体，样子，大小
        JLabel j1=new JLabel("新名称:"); j1.setFont(new Font("楷体",Font.PLAIN,20));
        JLabel j2=new JLabel("新负责人"); j2.setFont(new Font("楷体",Font.PLAIN,20));
        JLabel j3=new JLabel("新简介:"); j3.setFont(new Font("楷体",Font.PLAIN,20));


        JLabel j8=new JLabel("(温馨提醒：供应商编号不可更改，如需更改请先删除)");
        j8.setFont(new Font("宋体",Font.PLAIN,11));

        JButton aa=new JButton("确定");
        aa.setFont(new Font("楷体",Font.PLAIN,20)); aa.setBackground(Color.GREEN);
        JButton bb=new JButton("重置");
        bb.setFont(new Font("楷体",Font.PLAIN,20));bb.setBackground(Color.RED);


        JTextField s_no=new JTextField(15);
        JTextField s_n=new JTextField(15);
        JTextField s_dir=new JTextField(15);
        JTextField s_info=new JTextField(15);


        aa.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    DBCconnection Jcon = new DBCconnection();
                    Jcon.getConnection();
                    Statement st=Jcon.conn.createStatement();
                    String a=s_no.getText().trim();
                    String a2=s_n.getText().trim();
                    String a3=s_dir.getText().trim();
                    String a4=s_info.getText().trim();

                    String  s="(Select * from Supplier where s_no='"+a+"')";
                    ResultSet r=st.executeQuery(s);

                    if(r.next())
                    {
                        if (!a2.isEmpty())
                        {
                            String  strSQL="update dbo.Supplier set s_n='"+a2+"' where s_no='"+a+"'";
                            st.executeUpdate(strSQL);
                        }
                        if (!a3.isEmpty())
                        {
                            String  strSQL="update dbo.Supplier set s_dir='"+a3+"' where S_no='"+a+"'";
                            st.executeUpdate(strSQL);
                        }
                        if (!a4.isEmpty())
                        {
                            String  strSQL="update dbo.Supplier set introduction ='"+a4+"' where S_no='"+a+"'";
                            st.executeUpdate(strSQL);
                        }

                        JOptionPane.showMessageDialog(null,"修改成功哦~");
                    }

                    Jcon.conn.close();
                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        //重置清零
        bb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                s_no.setText("");
                s_n.setText("");
                s_dir.setText("");
                s_info.setText("");
            }
        });

        frame.setLayout(null);//自由布局
        j.setBounds(20, 30,  120, 20);
        s_no.setBounds(150, 30, 200, 25);
        j1.setBounds(20, 70, 120, 20);
        s_n.setBounds(150, 70, 200, 25);
        j2.setBounds(20, 110, 120, 30);
        s_dir.setBounds(150, 110, 200, 25);
        j3.setBounds(20, 150, 120, 30);
        s_info.setBounds(150, 150, 200, 25);


        aa.setBounds(100, 270, 100, 30);
        bb.setBounds(300, 270, 100, 30);

        j8.setBounds(10, 320, 450, 15);



        frame.add(j);
        frame.add(j1);
        frame.add(j2);
        frame.add(j3);
        frame.add(s_no);
        frame.add(s_n);
        frame.add(s_dir);
        frame.add(s_info);
        frame.add(aa);
        frame.add(bb);
        frame.add(j8);

    }

}