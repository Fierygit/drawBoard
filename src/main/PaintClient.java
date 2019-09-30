package main;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/27 23:43
 */


import factory.DrawCircle;
import factory.DrawLine;
import factory.DrawRectangle;
import entity.PaintHistory;
import factory.Factory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 监测数据显示类
 */
public class PaintClient extends JFrame {

    //欢迎界面 为 默认的界面
    Client client = null;
    Image offScreen = null;	//用于双缓冲
    private JButton circle = new JButton("圆圈");
    private JButton rectangle = new JButton("矩形");
    private JButton line = new JButton("直线");

    private JPanel modelPanel = new JPanel();
    private JPanel statePanel = new JPanel();
    private JPanel setPanel = new JPanel();

    private JLabel modelStateLabel = new JLabel("当前状态:");
    private JLabel colorStateLabel = new JLabel("当前颜色:");

    //画板
    private JPanel drawBoard = new JPanel();

    private JPanel selectColorJPanel = new JPanel();

    private JLabel colorLabel = new JLabel("颜色选择:");
    private JButton colorJButton0 = new JButton();
    private JButton colorJButton1 = new JButton();
    private JButton colorJButton2 = new JButton();
    private JButton colorJButton3 = new JButton();
    private JButton colorJButton4 = new JButton();
    private JButton colorJButton5 = new JButton();


    private JLabel strokeLabel = new JLabel("线条粗细:");
    private Choice strokeChoice = new Choice();

    // 设置window的icon

    private Toolkit toolKit = getToolkit();
    private Image icon = toolKit.getImage(PaintClient.class.getResource("computer.png"));

    private Image iBuffer = null;    //用于双缓冲

    private int state = 0;
    private Color color = Color.black;
    private float stroke = 1;
    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;

    private List<PaintHistory> paintHistories = new ArrayList<>();

    private JButton backJButton0 = new JButton("back");
    private JButton backJButton1 = new JButton("clean");

    /**
     * 类的构造方法
     *
     * @param client
     */
    public PaintClient(Client client) {
        this.client = client;
    }

    /**
     * 主菜单窗口显示； 添加Label、按钮、下拉条及相关事件监听；
     */
    public void paintJFrame() {
        this.setBounds(client.LOC_X, client.LOC_Y, client.WIDTH, client.HEIGHT);
        this.setTitle("简易画图程序");
        this.setIconImage(icon);
        this.setBackground(Color.white);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.out.println("bye bye !!");
                System.exit(0);
            }
        });

        drawBoard.setBackground(Color.white);
        drawBoard.setFocusable(false);
        drawBoard.setBounds(170, 20, 610, 530);
        this.getContentPane().add(drawBoard);
        drawBoard.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX() + 170;
                y1 = e.getY() + 40;
                x2 = e.getX() + 170;
                y2 = e.getY() + 40;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (x1 != x2) //不是点击加入
                    paintHistories.add(new PaintHistory(state, color, stroke, x1, y1, x2, y2));

                x2 = x1;
                y2 = y1;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        drawBoard.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                x2 = e.getX() + 170;
                y2 = e.getY() + 40;
            }
        });

        /**
         * 第一部分************************************************************************************************************************************
         */
        modelPanel.setBorder(BorderFactory.createTitledBorder("形状选择"));
        modelPanel.setBounds(10, 20, 140, 160);
        modelPanel.setLayout(null);
        // jframe 不能直接加 组件 ，先获取内容后加入
        this.getContentPane().add(modelPanel);

        line.setBounds(20, 25, 90, 25);
        line.setBackground(Color.white);
        line.setFont(new Font("宋体", Font.BOLD, 15));
        line.setForeground(Color.gray);
        line.setFocusPainted(false);
        modelPanel.add(line);
        line.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelStateLabel.setText("当前状态：  直线");
                state = 0;
                x2 = x1;
                y2 = y1;
            }
        });

        rectangle.setBounds(20, 70, 90, 25);
        rectangle.setBackground(Color.white);
        rectangle.setFont(new Font("宋体", Font.BOLD, 15));
        rectangle.setForeground(Color.gray);
        rectangle.setFocusPainted(false);
        modelPanel.add(rectangle);
        rectangle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelStateLabel.setText("当前状态：  矩形");
                state = 1;
                x2 = x1;
                y2 = y1;
            }
        });

        circle.setBounds(20, 115, 90, 25);
        circle.setBackground(Color.white);
        circle.setFont(new Font("宋体", Font.BOLD, 15));
        circle.setForeground(Color.gray);
        circle.setFocusPainted(false);
        modelPanel.add(circle);
        circle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelStateLabel.setText("当前状态：  圆形");
                state = 2;
                x2 = x1;
                y2 = y1;
            }
        });


        /**
         * 第二部分**********************************************************************************************************************************
         */
        statePanel.setBorder(BorderFactory.createTitledBorder("状态查看"));
        statePanel.setBounds(10, 200, 140, 95);
        statePanel.setLayout(null);
        this.getContentPane().add(statePanel);

        modelStateLabel.setForeground(Color.gray);
        modelStateLabel.setBounds(10, 20, 100, 40);
        modelStateLabel.setText("当前状态：  直线");
        statePanel.add(modelStateLabel);

        colorStateLabel.setForeground(Color.gray);
        colorStateLabel.setBounds(10, 50, 60, 40);
        statePanel.add(colorStateLabel);

        selectColorJPanel.setBorder(new BasicBorders.MarginBorder());
        selectColorJPanel.setBounds(85, 60, 20, 20);
        selectColorJPanel.setBackground(color);
        statePanel.add(selectColorJPanel);


        /**
         *    第三部分**********************************************************************************************************************************
         */
        setPanel.setBorder(BorderFactory.createTitledBorder("设置选项"));
        setPanel.setBounds(10, 320, 140, 160);
        setPanel.setLayout(null);
        this.getContentPane().add(setPanel);

        colorLabel.setForeground(Color.gray);
        colorLabel.setBounds(10, 20, 60, 40);
        setPanel.add(colorLabel);

        colorJButton0.setBounds(80, 30, 20, 20);
        colorJButton0.setBackground(Color.ORANGE);
        colorJButton0.setFocusPainted(false);
        setPanel.add(colorJButton0);
        colorJButton0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.ORANGE;
                selectColorJPanel.setBackground(color);
            }
        });


        colorJButton1.setBounds(110, 30, 20, 20);
        colorJButton1.setBackground(Color.black);
        colorJButton1.setFocusPainted(false);
        setPanel.add(colorJButton1);
        colorJButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.black;
                selectColorJPanel.setBackground(color);
            }
        });


        colorJButton2.setBounds(10, 60, 20, 20);
        colorJButton2.setBackground(Color.CYAN);
        colorJButton2.setFocusPainted(false);
        setPanel.add(colorJButton2);
        colorJButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.CYAN;
                selectColorJPanel.setBackground(color);
            }
        });

        colorJButton3.setBounds(45, 60, 20, 20);
        colorJButton3.setBackground(Color.green);
        colorJButton3.setFocusPainted(false);
        setPanel.add(colorJButton3);
        colorJButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.green;
                selectColorJPanel.setBackground(color);
            }
        });


        colorJButton4.setBounds(80, 60, 20, 20);
        colorJButton4.setBackground(Color.red);
        colorJButton4.setFocusPainted(false);
        setPanel.add(colorJButton4);
        colorJButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.red;
                selectColorJPanel.setBackground(color);
            }
        });

        colorJButton5.setBounds(110, 60, 20, 20);
        colorJButton5.setBackground(Color.MAGENTA);
        colorJButton5.setFocusPainted(false);
        setPanel.add(colorJButton5);
        colorJButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.MAGENTA;
                selectColorJPanel.setBackground(color);
            }
        });


        strokeLabel.setForeground(Color.gray);
        strokeLabel.setBounds(10, 85, 60, 40);
        setPanel.add(strokeLabel);

        strokeChoice.add("1");
        strokeChoice.add("2");
        strokeChoice.add("3");
        strokeChoice.add("4");
        strokeChoice.add("5");
        strokeChoice.setBounds(80, 95, 50, 20);
        setPanel.add(strokeChoice);
        strokeChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stroke = (float) Integer.parseInt((String) e.getItem());
            }
        });

        backJButton0.setBounds(10, 130, 60, 20);
        backJButton1.setBounds(80, 130, 50, 20);
        backJButton0.setFont(new Font("宋体", Font.BOLD, 10));
        backJButton0.setForeground(Color.gray);
        backJButton1.setFont(new Font("宋体", Font.BOLD, 5));
        backJButton1.setForeground(Color.gray);
        backJButton1.setFocusPainted(false);

        backJButton0.setFocusPainted(false);

        backJButton0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<PaintHistory> temp = new ArrayList<>();
                for(int i = 0 ; i < paintHistories.size() - 1; i++){
                    temp.add(paintHistories.get(i));
                }
                paintHistories = temp;
            }
        });

        backJButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintHistories.clear();
            }
        });

        setPanel.add(backJButton0);
        setPanel.add(backJButton1);



        /** *******************************************************************************  */

        this.setResizable(false);
        new Thread(new RepaintThread()).start(); // 启动重画线程
    }

    /**
     * 画出主界面组件元素
     */
    public void paint(Graphics g) {
        // super.paint(g);
        g.setColor(Color.black);
        //  g.setFont(new Font("微软雅黑", Font.ITALIC, 40));
        if (iBuffer == null) {
            iBuffer = createImage(WIDTH, HEIGHT);
            g = iBuffer.getGraphics();
        }
        g.setColor(getBackground());
        g.fillRect(0, 0, WIDTH, HEIGHT);
        super.paint(g);

        g.drawImage(iBuffer, 0, 0, null);

        for(PaintHistory p : paintHistories){
            if (p.getState() == 0) {
                Factory.getPaint(0).paint(g, p.getColor(), p.getStroke(), p.getX1(), p.getY1(), p.getX2(), p.getY2());
            } else if (p.getState() == 1) {
                Factory.getPaint(1).paint(g, p.getColor(), p.getStroke(), p.getX1(), p.getY1(), p.getX2(), p.getY2());
            } else if (p.getState() == 2) {
                Factory.getPaint(2).paint(g, p.getColor(), p.getStroke(), p.getX1(), p.getY1(), p.getX2(), p.getY2());
            }
        }

        /**
         * 这里最后用工厂模式获取
         */
        if (state == 0) {
            Factory.getPaint(0).paint(g, color, stroke, x1, y1, x2, y2);
        } else if (state == 1) {
            Factory.getPaint(1).paint(g, color, stroke, x1, y1, x2, y2);
        } else if (state == 2) {
            Factory.getPaint(2).paint(g, color, stroke, x1, y1, x2, y2);
        }




    }

    /**
     *   jframe 不会自动调用   update
     * @param g
     */
    public void update(Graphics g) {
        if (offScreen == null)	offScreen = this.createImage(WIDTH, HEIGHT);
        Graphics gOffScreen = offScreen.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);	//重画背景画布
        this.paint(gOffScreen);	//重画界面元素
        gOffScreen.setColor(c);
        g.drawImage(offScreen, 0, 0, null);	//将新画好的画布“贴”在原画布上
    }

    /**
     * 重画线程（每隔300毫秒重画一次）
     */
    private class RepaintThread implements Runnable {
        public void run() {
            while (true) {
                // 调用重画方法
                repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    System.exit(0);
                }
            }
        }
    }


}
