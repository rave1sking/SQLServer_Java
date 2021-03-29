package example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class sql_java {

    static PreparedStatement pstmt = null;
    static Connection conn = null; // 保存数据库连接的成员变量
    static Statement stmt = null;
    static ResultSet rs = null;
    static Statement stmt2 = null;
    public class stedent {
        int   xh;
        String  xm;
        int   cj;
        String  dj;
    }
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=jxgl","sa","sasasasa");
            //conn = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS2014:1433;DatabaseName=jxgl","sa","sasasasa");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost\\DESKTOP-GCILCN4\\SQLSERVER2019:1433;DatabaseName=jxgl","sa","sa");

            System.out.println("SQL Server Connected Success!");
            // below connect to MySQL
            //Class.forName("com.mysql.jdbc.Driver");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jxgl?user=root&password=root&useUnicode=true&characterEncoding=utf-8");
            //System.out.println("Mysql Connected Success!");
        }catch (ClassNotFoundException e) {
            System.out.println("driver failure!"); //驱动没有成功的加载时抛出的异常进行处理
        } catch (SQLException e) {
            System.out.println("connection failure!"+e.toString()); //连接失败时抛出的异常进行处理
        }
        if (check_username_password()==0){
            for(;;){
                System.out.println("Please select one function to execute:");
                System.out.println("  0--exit.");
                System.out.println("  1--创建学生表      7--修改学生记录    d--按学号查学生      i--统计某学生成绩 ");
                System.out.println("  2--创建课程表      8--修改课程记录    e--显示学生记录      j--学生成绩统计表 ");
                System.out.println("  3--创建成绩表      9--修改成绩记录    f--显示课程记录      k--课程成绩统计表 ");
                System.out.println("  4--添加学生记录   a--删除学生记录   g--显示成绩记录     l--通用统计功能   ");
                System.out.println("  5--添加课程记录   b--删除课程记录   h--学生课程成绩表   m--数据库用户表名 ");
                System.out.println("  6--添加成绩记录   c--删除成绩记录   o--创建教师表                   n--动态执行SQL命令");
                System.out.println("\n");
                Scanner s = new Scanner(System.in);
                String str = s.next();
                if (str.equals("0")) System.exit(0);
                if (str.equals("1")) create_student_table();
                if (str.equals("2")) create_course_table();
                if (str.equals("3")) create_sc_table();
                if (str.equals("4")) insert_rows_into_student_table();
                if (str.equals("5")) insert_rows_into_course_table();
                if (str.equals("6")) insert_rows_into_sc_table();
                if (str.equals("7")) current_of_update_for_student();
                if (str.equals("8")) current_of_update_for_course();
                if (str.equals("9")) current_of_update_for_sc();
                if (str.equals("a")) current_of_delete_for_student();
                if (str.equals("b")) current_of_delete_for_course();
                if (str.equals("c")) current_of_delete_for_sc();
                if (str.equals("d")) sel_student_by_sno();
                if (str.equals("e")) using_cursor_to_list_student();
                if (str.equals("f")) using_cursor_to_list_course();
                if (str.equals("g")) using_cursor_to_list_sc();
                if (str.equals("h")) using_cursor_to_list_s_sc_c();
                if (str.equals("i")) sel_student_total_grade_by_sno();
                if (str.equals("j")) using_cursor_to_total_s_sc();
                if (str.equals("k")) using_cursor_to_total_c_sc();
                if (str.equals("l")) using_cursor_to_total_ty();
                if (str.equals("m")) using_cursor_to_list_table_names();
                if (str.equals("n")) dynamic_exec_sql_command();
                if (str.equals("o")) create_teacher_table();
                pause();
            }
        }else {
            System.out.println("Your name or password is error,you can not be logined in the system!");
        }
        if(rs != null){
            rs.close();
        }
        if(stmt != null){
            stmt.close();
        }
        if(stmt2 != null){
            stmt2.close();
        }
        if(conn != null){
            conn.close();
        }
    }


    public static int sel_student_by_sno() throws SQLException
    {

        double isage = 18;
        int  isageind = 0;
        String issex = "男";
        int  issexind = 0;
        String isno = "95002";
        String isname = "xxxxxx";
        int  isnameind = 0;
        String isdept = "CS";
        int  isdeptind = 0;
        System.out.println("Please input sno to be selected:");
        Scanner s = new Scanner(System.in);
        isno = s.next();
        //conn = onecon.getConnection();
        rs = stmt.executeQuery("select sno ,sname ,sage ,ssex ,sdept  from student where sno='" + isno + "'");
        if(!rs.next()) System.out.println("ERROR: no student \n");
        else
        {
            rs.previous();
            System.out.printf("Success to select!\n");
            System.out.printf("sno     sname   ssex    sage    sdept \n");
        }
        while (rs.next())
        {

            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
        }
        //con.close();
        return 0;
    }

    public static int sel_student_total_grade_by_sno() throws SQLException
    {
        System.out.println("Please input sno to be selected:");
        Scanner s = new Scanner(System.in);
        String isno = s.next();
        //conn = onecon.getConnection();
        rs = stmt.executeQuery("select student.sno,sname,count(grade),sum(grade),avg(grade),MIN(grade),MAX(grade) "
                + "from student,sc where student.sno=sc.sno and student.sno='" + isno + "' group by student.sno,sname");
        System.out.printf("sno     \tsname  \tcount   \tsum     \tavg     \tmin     \tmax \n");
	    /*if(!rs.next()){
	    	System.out.println("ERROR:no tatal grade \n");
	    }
	    else
	    {
	    	rs.previous();
	        System.out.printf("Success to total grade:!\n");
	        System.out.printf("sno     sname   count   sum     avg     min     max \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getInt(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
        }
        //con.close();
        return 0;
    }


    public static void insert_rows_into_student_table()
    {
        int  isage = 18;
        String issex = "男";
        String isno = "95002";
        String isname = "xxxxxx";
        String isdept = "CS";
        int yn;
        while(true){
            System.out.println("Please input sno(eg:95001):");
            Scanner s = new Scanner(System.in);
            isno = s.next();
            System.out.println("Please input name(eg:XXXX):");
            Scanner s1 = new Scanner(System.in);
            isname = s1.next();
            System.out.println("Please input age(eg:18):");
            Scanner s2 = new Scanner(System.in);
            isage = s2.nextInt();
            System.out.println("Please input sex(eg:男):");
            Scanner s3 = new Scanner(System.in);
            issex = s3.next();
            System.out.println("Please input dept(eg:CS、IS、MA...):");
            Scanner s4 = new Scanner(System.in);
            isdept = s4.next();
            try {// 采用预编译方式定义SQL语句，使添加的数据信息以参数的形式给出
                // con = onecon.getConnection();
                String str = "insert into student(sno,sage,ssex,sname,sdept) values(?,?,?,?,?)";
                // 创建PreparedStatement对象，
                pstmt = conn.prepareStatement(str);
                pstmt.setString(1, isno); // 给第一个参数设定值
                pstmt.setInt(2, isage); // 给第二个参数设定值
                pstmt.setString(3, issex);
                pstmt.setString(4, isname);
                pstmt.setString(5, isdept);
                pstmt.executeUpdate();// 执行SQL语句
                System.out.println("execute successfully!\n\n");
                //pstmt.close();
                //con.close();

            } catch (SQLException e1) {// 执行SQL语句过程中出现的异常进行处理
                System.out.println("ERROR: execute\n" );
                e1.printStackTrace();
            }
            System.out.println("Insert again?(1--yes,0--no):");
            Scanner y = new Scanner(System.in);
            yn = y.nextInt();
            if (yn==1){
                continue;
            }
            else break;
        }
        return ;
    }

    public static int insert_rows_into_course_table()
    {

        String icname = "xxxxxxxxxx";
        int yn;
        while(true){
            System.out.println("Please input cno(eg:1,2,3,...):");
            Scanner s1 = new Scanner(System.in);
            String icno = s1.next();
            System.out.println("Please input course name(eg:XXXXXXXXXX):");
            Scanner s2 = new Scanner(System.in);
            icname = s2.next();
            System.out.println("Please input cpno(eg:1,2,3...):");
            Scanner s3 = new Scanner(System.in);
            String icpno = s3.next();
            System.out.println("Please input ccredit(eg:3):");
            Scanner s4 = new Scanner(System.in);
            int iccredit = s4.nextInt();
            try{// 采用预编译方式定义SQL语句，使添加的数据信息以参数的形式给出
                // con = onecon.getConnection();
                String str = "insert into course(cno,cname,cpno,ccredit) values(?,?,?,?)";
                // 创建PreparedStatement对象，
                pstmt = conn.prepareStatement(str);
                pstmt.setString(1, icno); // 给第一个参数设定值
                pstmt.setString(2, icname); // 给第二个参数设定值
                pstmt.setString(3, icpno);
                pstmt.setInt(4, iccredit);
                System.out.println(pstmt.toString());
                pstmt.executeUpdate();// 执行SQL语句
                System.out.println("execute successfully!\n");
                // con.close();
            } catch (SQLException e1) {// 执行SQL语句过程中出现的异常进行处理
                System.out.println("ERROR: execute \n" );
                e1.printStackTrace();
            }
            System.out.println("Insert again?(1--yes,0--no):");
            Scanner y = new Scanner(System.in);
            yn = y.nextInt();
            if (yn==1){
                continue;
            }
            else break;
        }
        return 0;
    }
    public static int insert_rows_into_sc_table()
    {
        int igrade = 80;
        String icno ;
        String isno = "95001";
        int yn;
        while(true){
            System.out.println("Please input sno(eg:95001,...):");
            Scanner s1 = new Scanner(System.in);
            isno = s1.next();
            System.out.println("Please input cno(eg:1,2,3,...):");
            Scanner s2 = new Scanner(System.in);
            icno = s2.next();
            System.out.println("Please input grade(eg:3):");
            Scanner s3 = new Scanner(System.in);
            igrade = s3.nextInt();
            try{// 采用预编译方式定义SQL语句，使添加的数据信息以参数的形式给出
                // con = onecon.getConnection();
                String str = "insert into sc(sno,cno,grade) values(?,?,?)";
                // 创建PreparedStatement对象，
                pstmt = conn.prepareStatement(str);
                pstmt.setString(1, isno); // 给第一个参数设定值
                pstmt.setString(2, icno); // 给第二个参数设定值
                pstmt.setInt(3, igrade);
                pstmt.executeUpdate();// 执行SQL语句
                System.out.println("execute successfully!\n");
                // con.close();

            } catch (SQLException e1) {// 执行SQL语句过程中出现的异常进行处理
                System.out.println("ERROR: execute \n" );
                e1.printStackTrace();
            }
            System.out.println("Insert again?(1--yes,0--no):");
            Scanner y = new Scanner(System.in);
            yn = y.nextInt();
            if (yn==1){
                continue;
            }
            else break;
        }
        return 0;
    }

    public static int current_of_update_for_student() throws SQLException
    {
        String yn;
        String deptname;
        String hsno;
        String hsname;
        String hssex;
        String hsdept;
        float hsage;
        int ihsdept=0;
        int ihsname=0;
        int ihssex=0;
        int ihsage=0;
        float isage = 38;
        String  issex = "男";
        String  isname = "xxxxxx";
        String  isdept = "CS";
        System.out.println("Please input deptname to be updated(CS、IS、MA...,**--All):\n");
        Scanner s= new Scanner(System.in);
        deptname = s.next();
        //	con = onecon.getConnection();
        if (deptname.equals("*") || deptname.equals("**"))
        {
            rs = stmt.executeQuery("SELECT sno,sname,ssex,sage,sdept FROM student ");
        }
        else
        {
            rs = stmt.executeQuery("SELECT sno,sname,ssex,sage,sdept FROM student where sdept = '" + deptname + "'");
        }
        while(rs.next())
        {
            String ss=rs.getString(1);
            System.out.println( "sno     sname   ssex    sage    sdept");
            System.out.println(ss+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            System.out.println("UPDATE ?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                System.out.println("Please input new name(eg:XXXX):");
                Scanner s1= new Scanner(System.in);
                isname = s1.next();
                System.out.println("Please input new age(eg:18):");
                Scanner s2= new Scanner(System.in);
                isage = s2.nextInt();
                System.out.println("Please input new sex(eg:男):");
                Scanner s3= new Scanner(System.in);
                issex = s3.next();
                System.out.println("Please input new dept(eg:CS、IS、MA...):");
                Scanner s4= new Scanner(System.in);
                isdept = s4.next();
                String str = "update student set sage='" + isage + "',sname='" + isname+ "',ssex='" + issex + "',sdept='" + isdept + "' where sno='" + ss + "'";
                stmt2.executeUpdate(str);// 执行SQL语句
                // stmt.close();
            };
            if (yn.equals("0")) break;
        };
        //con.close();
        return 0;
    }


    public static int current_of_delete_for_student() throws SQLException
    {
        System.out.printf("Please input deptname to be deleted(CS、IS、MA...,**--All):");
        Scanner s= new Scanner(System.in);
        String deptname = s.next();
        //con = onecon.getConnection();
        if (deptname.equals("*"))
            rs = stmt.executeQuery("SELECT sno,sname,ssex,sage,sdept FROM student ");
        else
            rs = stmt.executeQuery("SELECT sno,sname,ssex,sage,sdept FROM student  where sdept = '" + deptname + "'");
        while( rs.next())
        {
            System.out.println( "sno     sname   ssex    sage    sdept");
            String ss=rs.getString(5);
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+ rs.getString(4)+"\t"+ rs.getString(5));
            System.out.println("delete?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            String yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                pstmt = conn.prepareStatement("delete from student  where sdept='" + ss + "' ");
                pstmt.executeUpdate();// 执行删除
                //pstmt.close(); // 释放Statement所连接的数据库及JDBC资源
            };
            if (yn.equals("0")) break;
        };
        //  con.close(); // 关闭与数据库的连接
        return 0;

    }

    public static int current_of_update_for_course() throws SQLException
    {
        System.out.println("Please input cno to be updated(1、2...,**--All):\n");
        Scanner s= new Scanner(System.in);
        String ccno = s.next();
        //	con = onecon.getConnection();
        if (ccno.equals("**"))
            rs = stmt.executeQuery("SELECT cno,cname,cpno,ccredit FROM course ");
        else
            rs = stmt.executeQuery("SELECT cno,cname,cpno,ccredit FROM course where cno = '" + ccno + "'");
        while(rs.next())
        {
            System.out.println( "cno     cname    cpno  ccredit");
            String ss=rs.getString(1);
            System.out.println(ss+"\t"+rs.getString(2)+"\t"+"  "+ rs.getString(3)+"\t"+rs.getString(4));
            System.out.println("UPDATE ?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            String yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                System.out.println("Please input new cname(eg:数据库):");
                Scanner s1= new Scanner(System.in);
                String icname = s1.next();
                System.out.println("Please input new cpno(eg:1,2,...):");
                Scanner s2= new Scanner(System.in);
                String icpno = s2.next();
                System.out.println("Please input new ccredit(eg:3):");
                Scanner s3= new Scanner(System.in);
                int iccredit = s3.nextInt();
                String str = "update course set cname='" + icname + "',cpno='" + icpno+ "',ccredit='" + iccredit + "' where cno = '" + ss + "'";
                stmt2.executeUpdate(str);// 执行SQL语句
            };
            if (yn.equals("0")) break;
        };
        //	con.close();
        return 0;
    }

    static int current_of_delete_for_course() throws SQLException
    {
        System.out.printf("Please input cno to be deleted(1、2...,**--All):");
        Scanner s= new Scanner(System.in);
        String ccno = s.next();
        //	con = onecon.getConnection();
        if (ccno.equals("**"))
            rs = stmt.executeQuery("SELECT cno,cname,cpno,ccredit FROM course ");
        else
            //rs = stmt.executeQuery("SELECT cno,cname,cpno,ccredit FROM course cno = '" + ccno + "'");
            rs = stmt.executeQuery("SELECT cno,cname,cpno,ccredit FROM course cno = " + ccno  );
        while( rs.next())
        {
            System.out.println( "cno     cname    cpno  ccredit");
            String ss=rs.getString(1);
            System.out.println(ss+"\t"+rs.getString(2)+"\t"+"  "+ rs.getString(3)+"\t"+ rs.getInt(4));
            System.out.println("delete?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            String yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                pstmt = conn.prepareStatement("delete from course  where cno='" + ccno + "' ");
                pstmt.executeUpdate();// 执行删除
                //pstmt.close(); // 释放Statement所连接的数据库及JDBC资源
            };
            if (yn.equals("0")) break;
        };
        //  con.close(); // 关闭与数据库的连接
        return 0;
    }

    public static int current_of_update_for_sc() throws SQLException
    {
        System.out.println("Please input sno to be updated(95001...,*****--All):");
        Scanner s= new Scanner(System.in);
        String ssno = s.next();
        //	con = onecon.getConnection();
        if (ssno.equals("*****"))
            rs = stmt.executeQuery("SELECT sno,cno,gradet FROM sc ");
        else
            rs = stmt.executeQuery("SELECT sno,cno,grade FROM sc where sno = '" + ssno + "'");
        while(rs.next())
        {
            System.out.println( "sno     cno    grade ");
            String ss=rs.getString(1);
            String sss=rs.getString(2);
            System.out.println(ss+"\t"+rs.getString(2)+"\t"+ rs.getInt(3));
            System.out.println("UPDATE ?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            String yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                System.out.println("Please input new grade(eg:88):");
                Scanner s1= new Scanner(System.in);
                int igrade = s1.nextInt();
                String str = "update sc set grade='" + igrade + "' where sno = '" + ss + "' and cno = '" + sss + "'";
                stmt2.executeUpdate(str);// 执行SQL语句
            };
            if (yn.equals("0")) break;
        };
        //	con.close();
        return 0;
    }


    public static int current_of_delete_for_sc() throws SQLException
    {
        String yn;
        String  ssno;
        String  hsno;
        String  hcno;
        float hgrade;
        int   ihgrade=0;
        float igrade = 88;
        int   igradeind = 0;
        System.out.printf("Please input sno to be deleted(95001...,*****--All):\n");
        Scanner s= new Scanner(System.in);
        ssno = s.next();
        //	con = onecon.getConnection();
        if (ssno.equals("*****"))
            rs = stmt.executeQuery("SELECT sno,cno,grade FROM sc  ");
        else
            rs = stmt.executeQuery("SELECT sno,cno,grade FROM sc where sno = '" + ssno + "'");
        while( rs.next())
        {
            System.out.println( "sno     cno    grade ");
            String ss=rs.getString(1);
            System.out.println(ss+"\t"+rs.getString(2)+"\t"+ rs.getInt(3));
            System.out.println("delete?(y/n/0,y--yes,n--no,0--exit)");
            Scanner y= new Scanner(System.in);
            yn = y.next();
            if (yn.equals("y") || yn.equals("Y"))
            {
                pstmt = conn.prepareStatement("delete from sc  where sno='" + ss + "' ");
                pstmt.executeUpdate();// 执行删除
                //pstmt.close(); // 释放Statement所连接的数据库及JDBC资源
            };
            if (yn.equals("0")) break;
        };
        //   con.close(); // 关闭与数据库的连接
        return 0;
    }

    public static int dynamic_exec_sql_command() throws SQLException
    {

        System.out.println("Please input a sql command(delete、update、insert):");
        Scanner s1= new Scanner(System.in);
        String a1 = s1.next();
        if (a1.length()<6)
        {
            System.out.printf("Please input correct command.\n");
            return -1;
        }
        if (a1.equals("select")) {
            System.out.printf("Please input only DELETE、UPDATE、INSERT command.\n");
            return -1;
        }
        if (a1.equals("insert"))
        {
            System.out.printf("which table do you want to insert?---sc or student or course\n");
            Scanner s2= new Scanner(System.in);
            String a2 = s2.next();
            if(a2.equals("sc"))
            {
                insert_rows_into_sc_table();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("student"))
            {
                insert_rows_into_student_table();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("course"))
            {
                insert_rows_into_course_table();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
        }
        if (a1.equals("delete"))
        {
            System.out.printf("which table do you want to delete?---sc or student or course\n");
            Scanner s2= new Scanner(System.in);
            String a2 = s2.next();
            if(a2.equals("sc"))
            {
                current_of_delete_for_sc();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("student"))
            {
                current_of_delete_for_student();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("course"))
            {
                current_of_delete_for_course();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
        }
        if (a1.equals("update"))
        {
            System.out.printf("which table do you want to update?---sc or student or course\n");
            Scanner s2= new Scanner(System.in);
            String a2 = s2.next();
            if(a2.equals("sc"))
            {
                current_of_update_for_sc();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("student"))
            {
                current_of_update_for_student();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
            if(a2.equals("course"))
            {
                current_of_update_for_course();
                System.out.printf("The sql command is executed successfully!\n");
                return 0;
            }
        }
        else
        {
            System.out.printf("ERROR: execute the sql command.\n");
        }
        return 0;
    }

    public static int using_cursor_to_list_student() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select * from student order by sno");
        System.out.printf("sno     \tsname   \tssex    \tsage    \tsdept \n");
	    /*if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("sno     sname   ssex    sage    sdept \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
        }
        //stmt.close();
        //   con.close();
        return 0;
    }

    public static int using_cursor_to_list_course() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select * from course order by cno");
        System.out.printf("cno     \tcname    \tcpno  \tccredit  \n");
		/*
	    if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("cno     \tcname    \tcpno  \tccredit  \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ "  "+rs.getString(3)+"\t"+rs.getString(4));
        }
        // stmt.close();
        // con.close();
        return 0;

    }

    public static int using_cursor_to_list_sc() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select * from sc order by sno,cno");
        System.out.printf("sno     \tcno    \tgrade  \n");
		/*if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("sno     \tcno    \tgrade  \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3));
        }
        //stmt.close();
        //con.close();
        return 0;
    }

    public static int using_cursor_to_list_s_sc_c() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select student.sno,sname,ssex,sage,sdept,sc.cno,cname,grade "
                + "from student,sc,course where student.sno=sc.sno and sc.cno=course.cno order by student.sno");
        System.out.printf("sno     \tsname   \tssex    \tsage    \tsdept   \tcno     \tcname     \tgrade \n");
		/*
		if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("sno     \tsname   \tssex    \tsage    \tsdept   \tcno     \tcname     \tgrade \n");
	    }
	    */
        while (rs.next())
        {

            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+"  "+rs.getString(8));
        }
        //stmt.close();
        // con.close();
        return 0;
    }

    public static int using_cursor_to_total_s_sc() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select student.sno,sname,count(grade),sum(grade), "
                + "avg(grade),MIN(grade),MAX(grade) from student,sc "
                + "where student.sno=sc.sno group by student.sno,sname");
        System.out.printf("sno     \tsname   \tcount   \tsum     \tavg     \tmin     \tmax    \n");
		/*
		if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("sno     \tsname   \tcount   \tsum     \tavg     \tmin     \tmax    \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
        }
        // stmt.close();
        // con.close();
        return 0;
    }

    public static int using_cursor_to_total_c_sc() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("select course.cno,cname,count(grade),sum(grade),avg(grade),MIN(grade),MAX(grade) from course,sc where course.cno=sc.cno group by course.cno,cname");
        System.out.printf("cno     \tcname   \tcount   \tsum     \tavg     \tmin     \tmax \n");
		/*if(!rs.next())
	    	System.out.println("ERROR: open");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully!\n");
	        System.out.printf("cno     \tcname   \tcount   \tsum     \tavg     \tmin     \tmax \n");
	    }*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+ rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
        }
        // stmt.close();
        //con.close();
        return 0;
    }



    public static int using_cursor_to_total_ty() throws SQLException
    {
        //con = onecon.getConnection();
        System.out.printf("Example:   select sc.sno,avg(grade)\n");
        System.out.printf("           from student,sc \n");
        System.out.printf("           group by sc.sno \n");
        System.out.printf("Please input total sql statement according to the example.\n");
        Scanner s = new Scanner(System.in);
        String cmd = s.nextLine();
        int i=0;
        if(cmd.indexOf("select") != -1){
            i++;
        }
        if(cmd.indexOf("avg") != -1){
            i++;
        }
        if(cmd.indexOf("count") != -1){
            i++;
        }
        if(cmd.indexOf("sum") != -1){
            i++;
        }
        if(cmd.indexOf("min") != -1){
            i++;
        }
        if(cmd.indexOf("max") != -1){
            i++;
        }
        if(cmd.indexOf(",") != -1){
            i++;
        }
        System.out.println(i);
        if (!(i==2 || i==3)) {
            System.out.printf("Please input correct sql statement.\n");
            return -1;
        }
        rs = stmt.executeQuery(cmd);
        System.out.printf("   分组字段名            统计值 \n");
	/*
	    if(!rs.next()) System.out.println("ERROR: open \n");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully! \n");
	    }
	    System.out.printf("Success to total grade:!\n\n");
	    System.out.printf("   分组字段名            统计值 \n");*/
        while (rs.next())
        {
            System.out.println(rs.getString(1)+"\t"+rs.getString(2));
        }
        // stmt.close();
        //  con.close();
        return 0;
    }

    public static int using_cursor_to_list_table_names() throws SQLException
    {
        //con = onecon.getConnection();
        rs = stmt.executeQuery("SELECT name FROM sysobjects WHERE (xtype = 'U')");
        System.out.printf("There are below table names.\n");
		/*if(!rs.next()) System.out.println("ERROR: open \n");
	    else
	    {
	    	rs.previous();
	    	System.out.printf("Open successfully! \n");
	    	System.out.printf("There are below table names.\n");
	    }*/
        while (rs.next())
        {

            System.out.println(rs.getString(1));
        }
        // stmt.close();
        //  con.close();
        return 0;
    }

    public static int create_student_table() throws SQLException
    {
        String yn;
        rs = stmt.executeQuery("SELECT name FROM sysobjects WHERE xtype = 'U' and name='student'");
        if(rs.next())
        {
            System.out.println("ERROR:  table student already exists");
            return 0;
        }
        else
        {
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                // 定义插入记录的SQL语句
                String r1 = "CREATE TABLE student (sno char(5) NOT null primary key,"
                        + "sname char(6) null ,ssex char(2) null ,sage int null ,sdept char(2) null)";
                stmt.executeUpdate(r1); // 执行SQL命令
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源
                System.out.println("Success to create table student!");
            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                e1.printStackTrace();
                System.out.println("ERROR: create table student ");
            }
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                stmt.executeUpdate("insert into student(sno,sname,ssex,sage,sdept) values('95001', '李斌', '男',16,'CS')");
                stmt.executeUpdate("insert into student(sno,sname,ssex,sage,sdept) values('95002', '赵霞', '女',18, 'IS')");
                stmt.executeUpdate("insert into student(sno,sname,ssex,sage,sdept) values('95003', '周淘', '男',17, 'CS')");
                stmt.executeUpdate("insert into student(sno,sname,ssex,sage,sdept) values('95004', '钱乐', '女',18, 'IS')");
                stmt.executeUpdate("insert into student(sno,sname,ssex,sage,sdept) values('95005', '孙力', '男',16, 'MA')");
                System.out.printf("Success to insert rows to student table!\n");
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源

            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                e1.printStackTrace();
                System.out.println("ERROR: insert rows");
            }
            //   con.close(); // 关闭与数据库的连接
            return 0;
        }
    }

    public static int create_sc_table() throws SQLException
    {
        String yn;
        rs = stmt.executeQuery("SELECT name FROM sysobjects WHERE xtype = 'U' and name='sc'");
        if(rs.next())
        {
            System.out.println("ERROR:  table sc already exists");
            return 0;
        }
        else
        {
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                // 定义插入记录的SQL语句
                String r1 = "CREATE TABLE sc(sno char(5) NOT null ,cno char(1) NOT null ,"
                        + "grade int null ,primary key(sno,cno),foreign key(sno) references student(sno),"
                        + "foreign key(cno) references course(cno))";
                stmt.executeUpdate(r1); // 执行SQL命令
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源
                System.out.println("Success to create table sc!");
            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                System.out.println("ERROR: create table sc ");
            }
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                stmt.executeUpdate("insert into sc values('95001', '1', 66)");
                stmt.executeUpdate("insert into sc values('95001', '2', 66)");
                stmt.executeUpdate("insert into sc values('95001', '3', 66)");
                stmt.executeUpdate("insert into sc values('95002', '2', 0)");
                stmt.executeUpdate("insert into sc values('95002', '3', 97)");
                stmt.executeUpdate("insert into sc values('95002', '4', 0)");
                stmt.executeUpdate("insert into sc values('95003', '1', 88)");
                stmt.executeUpdate("insert into sc values('95003', '2', 68)");
                stmt.executeUpdate("insert into sc values('95003', '3', 88)");
                stmt.executeUpdate("insert into sc values('95004', '2', 76)");
                stmt.executeUpdate("insert into sc values('95004', '3', 76)");
                stmt.executeUpdate("insert into sc values('95005', '1', 87)");
                stmt.executeUpdate("insert into sc(sno,cno) values('95005', '2')");
                System.out.printf("Success to insert rows to sc table!\n");
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源

            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                System.out.println("ERROR: insert rows ");
            }
            //   con.close(); // 关闭与数据库的连接
            return 0;
        }
    }

    public static int create_course_table() throws SQLException
    {
        String yn;
        rs = stmt.executeQuery("SELECT name FROM sysobjects WHERE xtype = 'U' and name='course'");
        if(rs.next())
        {
            System.out.println("ERROR:  table course already exists");
            return 0;
        }
        else
        {
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                // 定义插入记录的SQL语句
                String r1 = "CREATE TABLE course(cno char(1) NOT null primary key,cname char(10) null ,"
                        + "cpno char(1) null ,ccredit int null)";
                stmt.executeUpdate(r1); // 执行SQL命令
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源
                System.out.println("Success to create table course!");
            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                System.out.println("ERROR: Failed to create table course!");
                e1.printStackTrace();
                return 0;
            }
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                stmt.executeUpdate("insert into course values('1', 'C语言', '',3)");
                stmt.executeUpdate("insert into course values('2', '数据库', '1',4)");
                stmt.executeUpdate("insert into course values('3', '编译原理', '2',5)");
                stmt.executeUpdate("insert into course values('4', '数据结构', '3',2)");
                stmt.executeUpdate("insert into course values('5', '操作系统', '4',3)");
                System.out.printf("Success to insert rows to course table!\n");
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源

            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                System.out.println("ERROR: insert rows ");
                e1.printStackTrace();
            }
            // con.close(); // 关闭与数据库的连接
            return 0;
        }
    }

    public static int create_teacher_table() throws SQLException
    {
        String yn;
        rs = stmt.executeQuery("SELECT name FROM sysobjects WHERE xtype = 'U' and name='teacher'");
        if(rs.next())
        {
            System.out.println("ERROR:  table teacher already exists");
            return 0;
        }
        else
        {
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                // 定义插入记录的SQL语句
                String r1 = "CREATE TABLE teacher (tno char(5) NOT null primary key,"
                        + "tname char(6) null ,tsex char(2) null ,tage int null ,tdept char(2) null)";
                stmt.executeUpdate(r1); // 执行SQL命令
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源
                System.out.println("Success to create table teacher!");
            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                e1.printStackTrace();
                System.out.println("ERROR: create table teacher ");
            }
            try {
                stmt = conn.createStatement(); // 建立Statement类对象
                stmt.executeUpdate("insert into teacher(tno,tname,tsex,tage,tdept) values('10001', '李斌2', '男',16,'CS')");
                stmt.executeUpdate("insert into teacher(tno,tname,tsex,tage,tdept) values('10002', '赵霞2', '女',18, 'IS')");
                stmt.executeUpdate("insert into teacher(tno,tname,tsex,tage,tdept) values('10003', '周淘2', '男',17, 'CS')");
                stmt.executeUpdate("insert into teacher(tno,tname,tsex,tage,tdept) values('10004', '钱乐2', '女',18, 'IS')");
                stmt.executeUpdate("insert into teacher(tno,tname,tsex,tage,tdept) values('10005', '孙力2', '男',16, 'MA')");
                System.out.printf("Success to insert rows to teacher table!\n");
                //stmt.close(); // 释放Statement所连接的数据库及JDBC资源

            } catch (SQLException e1) { // 数据库读取时产生的异常进行处理
                e1.printStackTrace();
                System.out.println("ERROR: insert rows");
            }
            //   con.close(); // 关闭与数据库的连接
            return 0;
        }
    }







    private static int check_username_password() {
        int num = 0;
        int insertResult;
        //String delTabsql = "drop table users";
        String delTabsql = "IF (EXISTS(SELECT *FROM student.dbo.sysdatabases WHERE dbid = db_ID('users')))"+"drop table users";
        String createTabsql = "create table users(uno varchar(6) not null, uname varchar(10) not null, upassword varchar(10) default null, uclass varchar(1) default 'a');";
        String insertSql = "insert into users values('000001','admin','admin', 'Z'),('999999','guest','guest','A');";
        try {
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            stmt.executeUpdate(delTabsql);
            stmt.executeUpdate(createTabsql);
            System.out.println("Success to create table users!");
            insertResult = stmt.executeUpdate(insertSql);
            if(insertResult == 2){
                System.out.println("Success to insert datas!");
            }else {
                System.out.println("Failed to insert datas!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to create table users!");
            e.printStackTrace();
        }
        while(num < 3){
            System.out.println("Please input user name(eg:guest):");
            Scanner name_scanner = new Scanner(System.in);
            String name = name_scanner.next();
            System.out.println("Please input user password(eg:guest):");
            Scanner pwd_scanner = new Scanner(System.in);
            String pwd = pwd_scanner.next();

            String checkSql = "select count(*) from users where uname = '" + name + "' and upassword = '" + pwd + "' ";
            ResultSet rs;
            try {
                rs = stmt.executeQuery(checkSql);
                if(rs.next()){
                    if(rs.getInt(1) == 1){
                        return 0;
                    }
                }
                num++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    void ErrorHandler ()
    {
        // display error information from SQLCA
        System.out.printf("\nError Handler called:\n");
    }
    static void pause()
    {
        System.out.printf("Press any key & enter key to continue!");
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
    }

}





