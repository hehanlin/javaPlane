package lesson04.boss;

import lesson04.Airplane;
import lesson04.Enemy;
import lesson04.FlyingObject;
import lesson04.ShootGame;

/**
 * Created by he on 2017/3/14.
 */
public class Boss extends FlyingObject implements Enemy {
    public int speed = 3;
    private int speedX = 30;
    private int speedY = 30;
    private int speedReturnCenterX = 3;
    private int speedReturnCenterY = 3;
    private int centerX = ShootGame.WIDTH/3-this.width;
    private int centerY = ShootGame.HIGHT/10-this.height;
    public int MAX_HP = 300; //最高血量
    public int hp;
    public int flag = 0; // 0 入场， 1 撞击 2 返回中心位置
    public int zhuangTime = 3000; // 撞击倒计时

    public Boss() {
        this.image = ShootGame.boss;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.y -= height;
        this.x = 50;
        this.hp = MAX_HP;
    }

    @Override
    public int getScore() {
        return 50;
    }

    @Override
    public boolean outOfbounds() {
        return false;
    }

    @Override
    public void step() {

    }

    public void step(int heroX, int heroY) {
        if (this.flag == 0) {
            enter();
        } else if(this.flag == 1){
            zhuangTime-=ShootGame.interval;
            if(zhuangTime<=0) {
                zhuangHero(heroX,heroY);
            }
        }else if(this.flag == 2) {
            zhuangTime-=ShootGame.interval;
            if(zhuangTime<=0) {
                returnCenter();
            }
        }
    }


    //boss进入初始位置
    public void enter() {
        if (this.y <= ShootGame.HIGHT/4-this.height) {
            y += speed;
        } else {
            this.flag = 1;
        }
    }

    public void zhuangHero(int heroX, int heroY) {
        if(heroX<x)speedX = -speedX;
        if(heroY<y)speedY = -speedY;
        x += speedX;
        y += speedY;
        speedX = 30;
        speedY = 30;
        if(Math.abs((double)(heroX-x))<70 && Math.abs((double)(heroY-y))<70){
            zhuangTime = 3000;
            this.flag = 2;
        }
    }

    public void returnCenter() {
        if(x>centerX)speedReturnCenterX = -speedReturnCenterX;
        if(y>centerY)speedReturnCenterY = -speedReturnCenterY;
        x+=speedReturnCenterX;
        y+=speedReturnCenterY;
        speedReturnCenterX = 3;
        speedReturnCenterY = 3;
        if(Math.abs((double)(x-centerX))<10 && Math.abs((double)(y-centerY))<10) {
            zhuangTime = 3000;
            this.flag = 1;
        }
    }
}
