package factory;

import java.awt.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/28 10:01
 */

public class PaintWelcome{

    public static void paint(Graphics g,Color color){


        g.setFont(new Font("宋体", Font.BOLD, 40));
        g.setColor(Color.black);
        g.drawString("欢迎使用简易画图程序", 120, 190);
        g.setFont(new Font("微软雅黑", Font.ITALIC, 40));
        g.setColor(Color.BLACK);
        g.drawString("Firelfy", 330, 360);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.setColor(color);
        g.drawString("————点击键进入————", 145, 480);

    }
}
