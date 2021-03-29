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

public class Register extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ValidCode vcode = new ValidCode();
    JTextField user,pass,id_input,ph,co;
    JButton register,exit;

    public Register()
    {
        super("注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null); //此窗口将置于屏幕的中央
        setVisible(true);

        JPanel frame=new JPanel();
        frame.setBackground(Color.PINK);
        this.add(frame);

        JLabel user_no=new JLabel("编号");
        JLabel user_pw=new JLabel("密码");
        JLabel user_phone=new JLabel("手机号码");
        JLabel id=new JLabel("邀请码");
        JLabel code=new JLabel("验证码");
        user=new JTextField();
        pass=new JTextField();
        co=new JTextField();
        ph=new JTextField();
        id_input = new JTextField();
        register=new JButton("注  册");
        exit=new JButton("退  出");

        user_no.setBounds(48, 46, 60, 40);
        user_no.setFont(new Font("楷体",Font.BOLD,17));
        user.setBounds(100,50,120,30);

        user_pw.setBounds(44,100,120,30);
        user_pw.setFont(new Font("楷体",Font.BOLD,17));
        pass.setBounds(100,100,120,30);

        id.setBounds(44,150,120,30);
        id.setFont(new Font("楷体",Font.BOLD,17));
        id_input.setBounds(100,150,120,30);

        user_phone.setBounds(22,200,120,30);
        user_phone.setFont(new Font("楷体",Font.BOLD,17));
        ph.setBounds(100,200,120,30);

        //验证码
        code.setBounds(41,250,120,30);
        code.setFont(new Font("楷体",Font.BOLD,17));
        co.setBounds(100,250,120,30);

        vcode.setBounds(112, 300, 100, 40);

        register.setBounds(150,380,70,30);
        exit.setBounds(250,380,70,30);


        frame.setLayout(null);
        frame.add(user_no);
        frame.add(user);

        frame.add(user_pw);
        frame.add(pass);

        frame.add(id);
        frame.add(id_input);

        frame.add(user_phone);
        frame.add(ph);

        frame.add(code);
        frame.add(co);

        frame.add(register);
        frame.add(exit);
        frame.add(vcode);





        register.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String user1=user.getText().trim();
                String pass1=pass.getText().trim();
                String ph1=ph.getText().trim();
                String co1=co.getText();
                DBCconnection Jcon = new DBCconnection();
                Jcon.getConnection();
                try {
                    Statement st=Jcon.conn.createStatement();
                    //创建SQL语句执行对象

                    String  strSQL="(Select * from  dbo.users where user_no='"+user1+"' )";
                    ResultSet rs=st.executeQuery(strSQL);

                    Md5 md5 = new Md5();
                    String newString = md5.EncoderByMd5(pass1);

                    if(co1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "请输入验证码!","提示消息",JOptionPane.WARNING_MESSAGE);
                    }
                    else
                    {
                        if(!isValidCodeRight()) {
                            JOptionPane.showMessageDialog(null, "验证码错误,请重新输入!","提示消息",JOptionPane.WARNING_MESSAGE);
                        }
                        else {

                            if(rs.next())
                            {
                                JOptionPane.showMessageDialog(null,"用户名已存在","错误!", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                String sql = "insert into dbo.users(user_no,user_pw,user_phone) values('"+user1+"','"+newString+"','"+ph1+"') ";
                                PreparedStatement pst = Jcon.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                                pst.executeUpdate();
                                pst.close();
                                JOptionPane.showMessageDialog(null,"注册成功");
                            }
                            Jcon.conn.close();
                            //关闭数据库连接
                        }
                    }
                }

                catch (NoSuchAlgorithmException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException | SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });

        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                closeThis();
                new Users();
            }
        });
    }
    public boolean isValidCodeRight() {

        if(co == null) {
            return false;
        }else if(vcode == null) {
            return true;
        }else if(vcode.getCode() .equals(co.getText())) {
            return true;
        }else
            return false;

    }


    public  void closeThis()//关闭当前界面
    {
        this.dispose();
    }

}
