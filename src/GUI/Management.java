package GUI;

import DBCconnection.DBCconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.Vector;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Management extends JFrame {
    private static final long serialVersionUID = 1L;
    JTabbedPane jtbp;
    JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7;
    public Management() throws SQLException,ClassNotFoundException
    {
        super("后勤物资管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        setVisible(true);

        MenuBar bar = new MenuBar();
        bar.setFont(new Font("楷体",Font.PLAIN,30));

        Menu fileMenu = new Menu("FILE");
        MenuItem open = new MenuItem("OPEN");
        MenuItem exit = new MenuItem("EXIT");

        Menu help = new Menu("HELP");
        help.setFont(new Font("楷体",Font.PLAIN,30));
        MenuItem print = new MenuItem("PRINT");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Users();
                closeThis();
            }
        });

        fileMenu.add(print);
        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        bar.add(fileMenu);
        bar.add(help);
        setMenuBar(bar);

        //创建组件
        jp1= new JPanel();
        jp2= new JPanel();
        jp3= new JPanel();
        jp4= new JPanel();
        jp5= new JPanel();
        jp6= new JPanel();
        jp7= new JPanel();
        jp1.setBackground(Color.WHITE);
        jp2.setBackground(Color.WHITE);
        jp3.setBackground(Color.WHITE);
        jp4.setBackground(Color.WHITE);
        jp5.setBackground(Color.WHITE);
        jp6.setBackground(Color.WHITE);
        jp7.setBackground(Color.WHITE);

        //设置jp1的内容
        String[] titles1 = { "供应商编号", "供应商名称","负责人","简介"};
        String[][] datas1 = {};
        //myModel存放表格数据
        DefaultTableModel myModel1 = new DefaultTableModel(datas1, titles1);
        JTable table1 = new JTable(myModel1);
        //设置表格对象JTable table1的规模
        table1.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane1 = new JScrollPane(table1);
        //行高
        table1.setRowHeight(20);

        DBCconnection Jcon = new DBCconnection();
        Statement st= Jcon.conn.createStatement();

        //查询语句：
        String strSQL = "(Select * from dbo.supplier)";
        ResultSet rs = st.executeQuery(strSQL);
        while (rs.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs.getString(1));
            ve.addElement(rs.getString(2));
            ve.addElement(rs.getString(3));
            ve.addElement(rs.getString(4));
            myModel1.addRow(ve);
        }

        //刷新按钮
        JButton again=new JButton("刷新~");
        again.setContentAreaFilled(false);
        again.setFont(new Font("楷体",Font.BOLD,16));
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection conn = null;
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //加载对应的jdbc驱动
                    String url = "jdbc:sqlserver://localhost\\\\DESKTOP-GCILCN4\\\\SQLSERVER2019:1433;DatabaseName=logisticsIMS";
                    //配置连接字符串
                    String user = "sa";//sa超级管理员
                    String password = "sa";//密码
                    conn = DriverManager.getConnection(url, user, password);
                    System.out.println("连接成功");
                    //创建数据库连接对象
                    Statement st = conn.createStatement();
                    //创建SQL语句执行对象
                    while (myModel1.getRowCount() > 0) {
                        myModel1.removeRow(myModel1.getRowCount() - 1);
                    }
                    String s1 = "(Select * from dbo.supplier)";
                    ResultSet rs1 = st.executeQuery(s1);
                    while (rs1.next()) {
                        Vector<String> ve1 = new Vector<String>();
                        ve1.addElement(rs1.getString(1));
                        ve1.addElement(rs1.getString(2));
                        ve1.addElement(rs1.getString(3));
                        ve1.addElement(rs1.getString(4));
                        myModel1.addRow(ve1);
                    }
                    conn.close();
                }
                catch (ClassNotFoundException ex) {
                    System.out.println("没有找到对应的数据库驱动类");
                } catch (SQLException ex) {
                    System.out.println("数据库连接或者是数据库操作失败");
                }
            }
        });

        JButton add1=new JButton("添加~");
        add1.setContentAreaFilled(false);
        add1.setFont(new Font("楷体",Font.BOLD,16));
        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addSupplier();
                closeThis();
            }
        });

        JButton delete1 =new JButton("删除~");
        delete1.setContentAreaFilled(false);
        delete1.setFont(new Font("楷体",Font.BOLD,16));
        delete1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new deleteSupplier();
                closeThis();
            }
        });
        JButton query=new JButton("查询~");
        query.setContentAreaFilled(false);
        query.setFont(new Font("楷体",Font.BOLD,16));
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new querySupplier();
                closeThis();
            }
        });


        JButton Modify = new JButton("修改~");
        Modify.setContentAreaFilled(false);
        Modify.setFont(new Font("楷体",Font.BOLD,16));
        Modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new updateSupplier();
               closeThis();
            }
        });
        ImageIcon im =new ImageIcon("symbol.jpg");
        im.setImage(im.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a=new JLabel(im);

        jp1.setLayout(null);//自由布局
        //jp1中组件的位置
        scrollPane1.setBounds(50, 320, 550, 100);
        add1.setBounds(50,50,80,30);
        delete1.setBounds(50,100,80,30);
        again.setBounds(50, 150, 80, 30);
        query.setBounds(50,200,80,30);
        Modify.setBounds(50,250,80,30);
        a.setBounds(250,50,200,200);
        jp1.add(scrollPane1);
        jp1.add(add1);
        jp1.add(again);
        jp1.add(delete1);
        jp1.add(query);
        jp1.add(Modify);
        jp1.add(a);
        //jp1中组件的位置

        //jp2
        //设置jp2的内容
        String[] titles2 = { "师生编号", "师生姓名"};
        String[][] datas2 = {};
        DefaultTableModel myModel2 = new DefaultTableModel(datas2, titles2);
        JTable table2 = new JTable(myModel2);
        table2.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane2 = new JScrollPane(table2);
        //行高
        table2.setRowHeight(20);


        //查询语句：
        String strSQL2 = "(Select * from dbo.TS)";
        ResultSet rs2 = st.executeQuery(strSQL2);
        while (rs2.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs2.getString(1));
            ve.addElement(rs2.getString(2));
            myModel2.addRow(ve);
        }
        //Jcon.conn.close();

        JButton delete2 =new JButton("删除~");
        delete2.setContentAreaFilled(false);
        delete2.setFont(new Font("楷体",Font.BOLD,16));
        delete2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteTS();
                closeThis();
            }
        });
        JButton add2 = new JButton("添加~");
        add2.setContentAreaFilled(false);
        add2.setFont(new Font("楷体",Font.BOLD,16));
        add2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTS();
                closeThis();
            }
        });
        JButton Modify2 = new JButton("修改~");
        Modify2.setContentAreaFilled(false);
        Modify2.setFont(new Font("楷体",Font.BOLD,16));
        Modify2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     new updateTS();
            }
        });


        JButton query2 = new JButton("查询~");
        query2.setContentAreaFilled(false);
        query2.setFont(new Font("楷体",Font.BOLD,16));
        query2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new queryTS();
            }
        });

        ImageIcon im2 =new ImageIcon("symbol.jpg");
        im2.setImage(im2.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a2=new JLabel(im2);
        a2.setBounds(250,50,200,200);

        jp2.setLayout(null);//自由布局
        //jp2中组件的位置
        scrollPane2.setBounds(50, 290, 550, 100);
        add2.setBounds(50,100,80,30);
        delete2.setBounds(50,50,80,30);
        Modify2.setBounds(50,150,80,30);
        query2.setBounds(50,200,80,30);


        jp2.add(scrollPane2);
        jp2.add(add2);
        jp2.add(delete2);
        jp2.add(Modify2);
        jp2.add(query2);
        jp2.add(a2);


        //jp3
        //设置jp3的内容
        String[] titles3 = { "物资编号", "单价","名称"};
        String[][] datas3 = {};
        DefaultTableModel myModel3 = new DefaultTableModel(datas3, titles3);
        JTable table3 = new JTable(myModel3);
        table3.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane3 = new JScrollPane(table3);
        //行高
        table3.setRowHeight(20);


        //查询语句：
        String strSQL3 = "(Select * from dbo.goods)";
        ResultSet rs3 = st.executeQuery(strSQL3);
        while (rs3.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs3.getString(1));
            ve.addElement(rs3.getString(2));
            ve.addElement(rs3.getString(3));
            myModel3.addRow(ve);
        }
        //Jcon.conn.close();

        JButton delete3 =new JButton("删除~");
        delete3.setContentAreaFilled(false);
        delete3.setFont(new Font("楷体",Font.BOLD,16));
        delete3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Deletegoods();
                closeThis();
            }
        });
        JButton add3 = new JButton("添加~");
        add3.setContentAreaFilled(false);
        add3.setFont(new Font("楷体",Font.BOLD,16));
        add3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Addgoods();
                closeThis();
            }
        });

        JButton query3 = new JButton("查询~");
        query3.setContentAreaFilled(false);
        query3.setFont(new Font("楷体",Font.BOLD,16));
        query3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new queryGoods();
            }
        });

        JButton Modify3  = new JButton("修改~");
        Modify3.setContentAreaFilled(false);
        Modify3.setFont(new Font("楷体",Font.BOLD,16));
        Modify3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new updateGoods();
            }
        });
        ImageIcon im3 =new ImageIcon("symbol.jpg");
        im3.setImage(im3.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a3=new JLabel(im3);
        a3.setBounds(250,50,200,200);

        jp3.setLayout(null);//自由布局
        //jp3中组件的位置
        scrollPane3.setBounds(50, 290, 550, 100);

        add3.setBounds(50,50,80,30);
        delete3.setBounds(50,100,80,30);
        query3.setBounds(50,150,80,30);
        Modify3.setBounds(50,200,80,30);
        a3.setBounds(250,50,200,200);

        jp3.add(scrollPane3);
        jp3.add(add3);
        jp3.add(delete3);
        jp3.add(query3);
        jp3.add(Modify3);
        jp3.add(a3);



        //jp4
        //设置jp4的内容
        String[] titles4 = { "供货商编号", "物资编号","时间","单价","数量"};
        String[][] datas4 = {};
        DefaultTableModel myModel4 = new DefaultTableModel(datas4, titles4);
        JTable table4 = new JTable(myModel4);
        table4.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane4 = new JScrollPane(table4);
        //行高
        table4.setRowHeight(20);

        //查询语句：
        String strSQL4 = "(Select * from dbo.buy)";
        ResultSet rs4 = st.executeQuery(strSQL4);
        while (rs4.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs4.getString(1));
            ve.addElement(rs4.getString(2));
            ve.addElement(rs4.getString(3));
            ve.addElement(rs4.getString(4));
            ve.addElement(rs4.getString(5));
            myModel4.addRow(ve);
        }
        //Jcon.conn.close();
        JButton add4 =new JButton("进货");
        add4.setContentAreaFilled(false);
        add4.setFont(new Font("楷体",Font.BOLD,16));
        add4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Addbuy();
                closeThis();
            }
        });

        JButton delete4 =new JButton("删除");
        delete4.setContentAreaFilled(false);
        delete4.setFont(new Font("楷体",Font.BOLD,16));
        delete4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Deletebuy();
                closeThis();
            }
        });

        JButton query4 =new JButton("查询");
        query4.setContentAreaFilled(false);
        query4.setFont(new Font("楷体",Font.BOLD,16));
        query4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new queryBuy();
                closeThis();
            }
        });

        ImageIcon im4 =new ImageIcon("symbol.jpg");
        im4.setImage(im4.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a4=new JLabel(im3);
        a4.setBounds(100,50,200,200);

        jp4.setLayout(null);//自由布局
        scrollPane4.setBounds(10, 290, 550, 100);
        add4.setBounds(450,50,80,30);
        delete4.setBounds(450,100,80,30);
        query4.setBounds(450,150,80,30);

        jp4.add(scrollPane4);
        jp4.add(add4);
        jp4.add(delete4);
        jp4.add(query4);
        jp4.add(a4);


        //jp5
        String[] titles5 = { "师生编号", "物资编号","时间","单价","数量"};
        String[][] datas5 = {};
        DefaultTableModel myModel5 = new DefaultTableModel(datas5, titles5);
        JTable table5 = new JTable(myModel5);
        table5.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane5 = new JScrollPane(table5);
        //行高
        table5.setRowHeight(20);

        //查询语句：
        String strSQL5 = "(Select * from dbo.sales)";
        ResultSet rs5 = st.executeQuery(strSQL5);
        while (rs5.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs5.getString(1));
            ve.addElement(rs5.getString(2));
            ve.addElement(rs5.getString(3));
            ve.addElement(rs5.getString(4));
            ve.addElement(rs5.getString(5));
            myModel5.addRow(ve);
        }
        //Jcon.conn.close();
        JButton add5 =new JButton("售货");
        add5.setContentAreaFilled(false);
        add5.setFont(new Font("楷体",Font.BOLD,16));
        add5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Sales();
                closeThis();
            }
        });

        JButton delete5 =new JButton("删除");
        delete5.setContentAreaFilled(false);
        delete5.setFont(new Font("楷体",Font.BOLD,16));
        delete5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteSales();
                closeThis();
            }
        });
        ImageIcon im5 =new ImageIcon("symbol.jpg");
        im5.setImage(im5.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a5=new JLabel(im5);
        a5.setBounds(100,50,200,200);

        JButton query5 =new JButton("查询");
        query5.setContentAreaFilled(false);
        query5.setFont(new Font("楷体",Font.BOLD,16));
        query5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new querySales();
                closeThis();
            }
        });

        jp5.setLayout(null);//自由布局
        scrollPane5.setBounds(10, 290, 550, 100);
        add5.setBounds(450,40,80,30);
        delete5.setBounds(450,100,80,30);
        query5.setBounds(450,160,80,30);
        jp5.add(scrollPane5);
        jp5.add(add5);
        jp5.add(a5);
        jp5.add(query5);
        //jp5.add(delete5);


        //jp6内容
        String[] titles6 = { "仓库编号", "仓库名","规模","剩余容量"};
        String[][] datas6 = {};
        DefaultTableModel myModel6 = new DefaultTableModel(datas6, titles6);
        JTable table6 = new JTable(myModel6);
        table6.setPreferredScrollableViewportSize(new Dimension(550, 120));
        //设置滚动面板
        JScrollPane scrollPane6 = new JScrollPane(table6);
        //行高
        table6.setRowHeight(20);


        //查询语句：
        String strSQL6 = "(Select * from dbo.warehouse)";
        ResultSet rs6 = st.executeQuery(strSQL6);
        while (rs6.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs6.getString(1));
            ve.addElement(rs6.getString(2));
            ve.addElement(rs6.getString(3));
            ve.addElement(rs6.getString(4));
            myModel6.addRow(ve);
        }

        String[] titles62 = { "物资编号","数量"};
        String[][] datas62 = {};
        DefaultTableModel myModel62 = new DefaultTableModel(datas62, titles62);
        JTable table62 = new JTable(myModel62);
        table62.setPreferredScrollableViewportSize(new Dimension(300, 120));
        //设置滚动面板
        JScrollPane scrollPane62 = new JScrollPane(table62);
        //行高
        table62.setRowHeight(20);


        //查询语句：
        String strSQL62 = "Select g_no, sum(num) as a from dbo.store group by(g_no) having(sum(num)>0)";
        ResultSet rs62 = st.executeQuery(strSQL62);
        while (rs62.next())
        {
            Vector<String> ve = new Vector<String>();
            ve.addElement(rs62.getString(1));
            ve.addElement(rs62.getString(2));
            myModel62.addRow(ve);
        }


        //Jcon.conn.close();
        JButton add6 = new JButton("添加~");
        add6.setContentAreaFilled(false);
        add6.setFont(new Font("楷体",Font.BOLD,16));
        add6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Addwarehouse();
                closeThis();
            }
        });

        JButton delete6 =new JButton("删除~");
        delete6.setContentAreaFilled(false);
        delete6.setFont(new Font("楷体",Font.BOLD,16));
        delete6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteWarehouse();
                closeThis();
            }
        });

        ImageIcon im6 =new ImageIcon("symbol.jpg");
        im6.setImage(im6.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT));
        JLabel a6=new JLabel(im6);
        a6.setBounds(210,350,150,150);

        jp6.setLayout(null);//自由布局
        //jp3中组件的位置
        scrollPane6.setBounds(50, 200, 550, 100);
        scrollPane62.setBounds(50, 50, 300, 100);
        add6.setBounds(450,50,80,30);
        delete6.setBounds(450,100,80,30);
        jp6.add(scrollPane6);
        jp6.add(add6);
        jp6.add(delete6);
        jp6.add(scrollPane62);
        jp6.add(a6);


        //设置jp7的内容
        ///

        JLabel start_inputt = new JLabel("输入开始时间：");
        JTextField start_input = new JTextField();
        JLabel end_inputt = new JLabel("输入结束时间：");
        JTextField end_input = new JTextField();

        start_inputt.setFont(new Font("楷体",Font.BOLD,16));
        start_inputt.setBounds(50,50,120,30);
        start_input.setBounds(170,50,120,30);

        end_inputt.setFont(new Font("楷体",Font.BOLD,16));
        end_inputt.setBounds(50,100,120,30);
        end_input.setBounds(170,100,120,30);

        JLabel show11 = new JLabel("收入");
        JLabel show22 = new JLabel("支出");
        JTextField show1 = new JTextField();
        JTextField show2 = new JTextField();

        show11.setFont(new Font("楷体",Font.BOLD,16));
        show11.setBounds(350,50,60,30);
        show1.setBounds(420,50,60,30);

        show22.setFont(new Font("楷体",Font.BOLD,16));
        show22.setBounds(350,100,60,30);
        show2.setBounds(420,100,60,30);

        JButton select = new JButton("账务查询");
        select.setFont(new Font("楷体",Font.BOLD,14));
        select.setBounds(230,150,150,40);


        String s1 = start_input.getText().trim();
        String s2 = end_input.getText().trim();


        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs71 =null;
                ResultSet rs72 =null;
                String s1 = start_input.getText().trim();
                String s2 = end_input.getText().trim();
                String strSQL71 = "select sum(sale_price*sale_number) from Sales where times>='"+s1+"' and times<= '"+s2+"'";
                String strSQL72 = "select sum(b_price*b_number) from buy where times>='"+s1+"' and times<= '"+s2+"' ";
                try{
                    Connection conn = null;
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //加载对应的jdbc驱动
                    String url = "jdbc:sqlserver://localhost\\\\DESKTOP-GCILCN4\\\\SQLSERVER2019:1433;DatabaseName=logisticsIMS";
                    //配置连接字符串
                    String user = "sa";//sa超级管理员
                    String password = "sa";//密码
                    conn = DriverManager.getConnection(url, user, password);
                    System.out.println("连接成功");
                    //创建数据库连接对象
                    Statement st = conn.createStatement();
                    //创建SQL语句执行对象
                    rs71 = st.executeQuery(strSQL71);
                    rs71.next();
                    show1.setText(rs71.getString(1));
                    rs72 = st.executeQuery(strSQL72);
                    rs72.next();
                    show2.setText(rs72.getString(1));
                    conn.close();
                }
                catch (ClassNotFoundException ex) {
                    System.out.println("没有找到对应的数据库驱动类");
                } catch (SQLException ex) {
                    System.out.println("数据库连接或者是数据库操作失败");
                }

            }

        });
        ImageIcon im7 =new ImageIcon("symbol.jpg");
        im7.setImage(im7.getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
        JLabel a7=new JLabel(im7);

        a7.setBounds(200,230,200,200);

        jp7.setLayout(null);//自由布局
        jp7.add(start_input);
        jp7.add(start_inputt);
        jp7.add(end_input);
        jp7.add(end_inputt);
        jp7.add(select);
        //jp7.add(scrollPane7);
        jp7.add(show1);
        jp7.add(show11);
        jp7.add(show2);
        jp7.add(show22);
        jp7.add(a7);


        ///
        jtbp=new JTabbedPane(JTabbedPane.LEFT); //创建选项卡并使选项卡垂直排列
        jtbp.add("供应商管理",jp1);
        jtbp.add("师生管理",jp2);
        jtbp.add("货物管理",jp3);
        jtbp.add("采购管理",jp4);
        jtbp.add("销售管理",jp5);
        jtbp.add("库存管理",jp6);
        jtbp.add("财务管理",jp7);
        jtbp.setFont(new Font("楷体",Font.PLAIN,30));

        this.add(jtbp);
    }

    public  void closeThis()
    {
        this.dispose();
    }
}
