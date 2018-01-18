package lesson04;

import java.util.Random;


/**
 * Created by he on 2017/3/16.
 */
public class FastAirplane extends Airplane{
    public int speed = 10;

	public FastAirplane() {
	    this.image = ShootGame.airplane1; //初始化敌机图片
        this.width = image.getWidth(); // 初始化敌机高度
        this.height = image.getHeight(); // 初始化敌机高度
        this.y-=height;
        Random rand = new Random();
        this.x = rand.nextInt(ShootGame.WIDTH-width);
    }
	
    @Override
    public void step() {
        y+=speed;
    }
}
