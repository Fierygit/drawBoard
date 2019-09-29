package factory;

import java.awt.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/28 10:01
 */

public class DrawRectangle {

    public static void paint(Graphics g,Color color,float stroke,int x1,int y1,int x2,int y2){

        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(stroke));

        int x11 ,y11,x22,y22;
        if(x1 < x2){
            x11 = x1;
            x22 = x2;
        }else {
            x11 = x2;
            x22 = x1;
        }
        y11 = y1 > y2 ? y2 : y1;
        y22 = y1 > y2 ? y1 : y2;

        g2d.drawRect(x11,y11,x22-x11,y22-y11);

    }
}
