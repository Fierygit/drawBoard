package factory;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/9/29 12:44
 * 实现画笔的获取
 */

public class Factory {

    public  static  DrawImg getPaint(int state){
        if(state == 0){
            return new DrawLine();
        }else if (state == 1){
            return new DrawRectangle();
        }else{
            return new DrawCircle();
        }
    }

}
