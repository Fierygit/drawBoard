package main;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/27 23:40
 */

import factory.PaintWelcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{


    /**
     * 程序界面宽度
     */
    public static final int WIDTH = 800;
    /**
     * 程序界面高度
     */
    public static final int HEIGHT = 620;
    /**
     * 程序界面出现位置（横坐标）
     */
    public static final int LOC_X = 200;
    /**
     * 程序界面出现位置（纵坐标）
     */
    public static final int LOC_Y = 70;

    Color color = Color.WHITE;
    //设置window的icon（这里我自定义了一下Windows窗口的icon图标，因为实在觉得哪个小咖啡图标不好看 = =）
    Toolkit toolKit = getToolkit();
    Image icon = toolKit.getImage(Client.class.getResource("computer.png"));

    //持有其他类
   PaintClient paintClient = new PaintClient(this);


    /**
     * 主方法
     * @param args	//
     */
    public static void main(String[] args) {
        new Client().launchJFrame();
    }

    /**
     * 显示主界面
     */
    public void launchJFrame() {
        this.setBounds(LOC_X, LOC_Y, WIDTH, HEIGHT);	//设定程序在桌面出现的位置
        this.setTitle("简易画图程序");	        //设置程序标题
        this.setIconImage(icon);
        this.setBackground(Color.white);	//设置背景色
        this.addWindowListener(new WindowAdapter() {
            //添加对窗口状态的监听
            public void windowClosing(WindowEvent arg0) {
                //当窗口关闭时
                System.out.println("程序已经退出！！！");
                System.exit(0);	//退出程序
            }

        });
        this.addMouseListener(new MouseMonitor());
        this.addKeyListener(new KeyMonitor());	//添加键盘监听器
        this.setResizable(false);	//窗口大小不可更改
        this.setVisible(true);	//显示窗口
        new Thread(new RepaintThread()).start();	//开启重画线程
    }

    /**
     * 画出程序界面各组件元素
     */
    public void paint(Graphics g) {
        super.paint(g);
        // java 传参不是 传的不是 指针， 而是一份拷贝，这份拷贝能生效， 但是不会改变原来的 那份值
        PaintWelcome.paint(g,color);
        //使文字 "————点击进入主界面————" 黑白闪烁
        if (color == Color.WHITE)	color = Color.black;
        else if (color == color.BLACK)	color = Color.white;
    }

    /*
     * 内部类形式实现对键盘事件的监听
     *
     * 内部类 可以看到 外类的 东西， 所以 不用 传参？？？？？？？？？
     */
    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.MOUSE_MOTION_EVENT_MASK) {	//当监听到用户敲击键盘enter键后执行下面的操作
                setVisible(false);	         //隐去欢迎界面
                paintClient.setVisible(true);	//显示监测界面
                paintClient.paintJFrame();	  //初始化监测界面
            }
        }

    }
    private class MouseMonitor extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            int mouseCode = e.getButton();
            if (mouseCode == MouseEvent.BUTTON1) {	//鼠标第一个键
                setVisible(false);	//隐去欢迎界面
                paintClient.setVisible(true);	//显示监测界面
                paintClient.paintJFrame();	//初始化监测界面
            }
        }

    }

    /*
     * 重画线程（每隔250毫秒重画一次）
     */
    private class RepaintThread implements Runnable {
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    //重画线程出错抛出异常时创建一个Dialog并显示异常详细信息
                    System.out.println(e);
                }
            }
        }

    }

}
