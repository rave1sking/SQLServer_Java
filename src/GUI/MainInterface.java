package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


// 主界面
public class MainInterface  extends JFrame{
      private static final long serialVersionUID = 1L;
      public MainInterface()
      {
          setTitle("后勤管理系统");
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setSize(600,470);
          this.setLocation(300,120);

          JPanel A=new JPanel();
          A.setBackground(Color.PINK);
          ImageIcon im =new ImageIcon("jnumain.jpg");
          //设置图片大小
          // im.setImage(im.getImage().getScaledInstance(400,470, Image.SCALE_DEFAULT));
          JLabel a=new JLabel(im);
          A.add(a);
          JLabel Welcome = new JLabel("欢迎使用");
          Welcome.setBounds(0,300,400,100);
          Welcome.setFont(new Font("楷体",Font.BOLD,50));
          A.add(Welcome);

          JPanel B=new JPanel(new BorderLayout());
          B.setBackground(Color.PINK);
          JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,A,B);//分屏的方式：左右HORIZONTAL_SPLIT，上下VERTICAL_SPLIT
          jSplitPane.setDividerLocation(400);//左边占的长度
          this.add(jSplitPane);
          jSplitPane.setDividerSize(1);//分界线的宽度 设置为0 即不显示出分界线
          A.setBorder(BorderFactory.createLineBorder(Color.PINK));
          B.setBorder(BorderFactory.createLineBorder(Color.PINK));

          JLabel w=new JLabel(" 我  是 ");
          JButton g=new JButton("管理员");

          JPanel G=new JPanel();
          G.setBackground(Color.PINK);
          G.add(g);
          g.setFont(new Font("楷体",Font.BOLD,18));
          g.setContentAreaFilled(false);

          JButton x=new JButton("用  户");
          JPanel X=new JPanel();
          X.add(x);
          X.setBackground(Color.PINK);
          x.setFont(new Font("楷体",Font.BOLD,18));
          x.setContentAreaFilled(false);

          B.add(w,BorderLayout.NORTH);
          w.setHorizontalAlignment(SwingConstants.CENTER);//居中
          w.setPreferredSize(new Dimension(0,150));//宽度150
          w.setFont(new Font("楷体",Font.PLAIN,30));//设置字体的字体，样子，大小
          B.add(X,BorderLayout.CENTER);
          B.add(G,BorderLayout.SOUTH);
          G.setPreferredSize(new Dimension(0,200));//宽度200

          x.addActionListener(new ActionListener()    //监听用户按钮
          {

              public void actionPerformed(ActionEvent e)
              {
                  new Users();
              }
          });

          g.addActionListener(new ActionListener()   //监听管理员按钮
          {
              public void actionPerformed(ActionEvent e)
              {
                  new Admin();
              }
          });

      }

    public static void main(String[] args)
    {
        new MainInterface().setVisible(true);

    }
}














