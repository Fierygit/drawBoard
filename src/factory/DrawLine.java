package factory;

import java.awt.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/28 10:01
 */

public class DrawLine {

    public static void paint(Graphics g,Color color,Float stroke,int x1,int y1,int x2,int y2){

        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawLine(x1,y1,x2,y2);

    }
}
