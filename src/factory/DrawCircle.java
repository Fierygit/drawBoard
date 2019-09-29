package factory;

import java.awt.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/28 10:01
 */

public class DrawCircle {

    public static void paint(Graphics g,Color color,float stroke,int x1,int y1,int x2,int y2){

        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(stroke));


        double xx = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
        double xxx = Math.sqrt(xx);
        int radious = (int)xxx;
             g2d.drawOval(x1 - radious,y1 - radious,radious*2,radious*2);

    }
}
