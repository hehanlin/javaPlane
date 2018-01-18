package lesson04;

import java.util.Random;


/**
 * Created by he on 2017/3/12.
 */

//蜜蜂奖励
public class Bee extends FlyingObject implements Award{

    public int xSpeed = 1; //x移动速度
    public int ySpeed = 2; //y移动速度
    public int awardType; //奖励类型

    public Bee() {
        image = ShootGame.bee;
        width = image.getWidth();
        height = image.getHeight();
        y=-height; // 在窗口上方
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH-width);
    }

    @Override
    public int getType() {
        return awardType;
    }

    @Override
    public boolean outOfbounds() {
        return y>ShootGame.HIGHT;
    }



    @Override
    public void step() {
        x+=xSpeed; // 横坐标改变
        y+=ySpeed; // 纵坐标改变
        if(x>ShootGame.WIDTH-width) { //右出界
            xSpeed = -xSpeed;
        }
        if(x<0) { //左出界
            xSpeed = -xSpeed;
        }
    }
}
