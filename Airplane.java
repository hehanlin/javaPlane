package lesson04;

import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Created by he on 2017/3/11.
 */
public class Airplane extends FlyingObject implements Enemy{
    public int speed = 3; //移动速度
    public BufferedImage down[] = {
            ShootGame.airplaneDown1,
            ShootGame.airplaneDown2,
            ShootGame.airplaneDown3,
            ShootGame.airplaneDown4
    };
    public int die = 0;
    public int dieIndex = 0;

	public Airplane() {
	    this.image = ShootGame.airplane; //初始化敌机图片
        this.width = image.getWidth(); // 初始化敌机高度
        this.height = image.getHeight(); // 初始化敌机高度
        this.y-=height;
        Random rand = new Random();
        this.x = rand.nextInt(ShootGame.WIDTH-width);
    }

    //出界
    @Override
    public boolean outOfbounds() {
	    return y>ShootGame.HIGHT;
    }

    //下一步
    @Override
    public void step() {
        y+=this.speed;
    }

    public void showDie() {
	    image = down[dieIndex++%4];
	    if(dieIndex>=5) {
	        die=2;
        }
    }

    //获取分数
    @Override
    public int getScore() {
        return 10;
    }

    public void setDie() {
	    die = 1;
    }

}
