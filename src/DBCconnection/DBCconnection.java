package DBCconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCconnection {
    public static Connection conn = null;
    public Connection getConnection()
    {
        try {
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
        } catch (ClassNotFoundException ex) {
            System.out.println("没有找到对应的数据库驱动类");
        } catch (SQLException ex) {
            System.out.println("数据库连接或者是数据库操作失败");
        }
        return conn;
    }
    public void closeConnection() throws SQLException
    {
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
