package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import DBCconnection.DBCconnection;
public class updateGoods extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public updateGoods() {
        super("修改货物属性");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel j=new JLabel("编号:");  j.setFont(new Font("楷体",Font.PLAIN,20));//设置字体的字体，样子，大小
        JLabel j1=new JLabel("新单价:"); j1.setFont(new Font("楷体",Font.PLAIN,20));
        JLabel j2=new JLabel("新名称:"); j2.setFont(new Font("楷体",Font.PLAIN,20));


        JLabel j8=new JLabel("(温馨提醒：编号不可更改，如需更改请先删除)");
        j8.setFont(new Font("宋体",Font.PLAIN,11));

        JButton aa=new JButton("确定");
        aa.setFont(new Font("楷体",Font.PLAIN,20)); aa.setBackground(Color.GREEN);
        JButton bb=new JButton("重置");
        bb.setFont(new Font("楷体",Font.PLAIN,20));bb.setBackground(Color.RED);


        JTextField g_no=new JTextField(15);
        JTextField g_price=new JTextField(15);
        JTextField g_n=new JTextField(15);



        aa.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    DBCconnection Jcon = new DBCconnection();
                    Jcon.getConnection();
                    Statement st=Jcon.conn.createStatement();
                    String a=g_no.getText().trim();
                    String a1=g_price.getText().trim();
                    String a2=g_n.getText().trim();


                    String  s="(Select * from goods where g_no='"+a+"')";
                    ResultSet r=st.executeQuery(s);

                    if(r.next())
                    {
                        if (!a1.isEmpty())
                        {
                            String  strSQL="update goods set g_price ='"+a1+"' where g_no='"+a+"'";
                            st.executeUpdate(strSQL);
                        }
                        if (!a2.isEmpty())
                        {
                            String  strSQL="update goods set g_n ='"+a2+"' where g_no='"+a+"'";
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
                g_no.setText("");
                g_price.setText("");
                g_n.setText("");
            }
        });

        frame.setLayout(null);//自由布局
        j.setBounds(20, 30,  120, 20);
        g_no.setBounds(150, 30, 200, 25);
        j1.setBounds(20, 70, 120, 20);
        g_price.setBounds(150, 70, 200, 25);
        j2.setBounds(20, 110, 120, 20);
        g_n.setBounds(150, 110, 200, 25);


        aa.setBounds(100, 270, 100, 30);
        bb.setBounds(300, 270, 100, 30);

        j8.setBounds(10, 320, 450, 15);

        frame.add(j);
        frame.add(j1);
        frame.add(j2);
        frame.add(g_n);
        frame.add(g_price);
        frame.add(g_no);
        frame.add(aa);
        frame.add(bb);
        frame.add(j8);

    }

}
