package lesson04;

import lesson04.bullet.*;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private int life; // 命数
	private int fireType; // 子弹类型
	public BufferedImage[] images={}; // 英雄图片
	private int index=0;//交换图片用
	public double invincible = 0; //无敌状态时间
	
	Hero(){
		life=3;
		fireType=0;
		images=new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		image=ShootGame.hero0;
		image=ShootGame.hero1;
		width=image.getWidth();
		height=image.getHeight();
		x=150;
		y=400;
	}


	public	Bullet[]shoot(){
		int xStep=width/4;
		int yStep=20;
		Bullet[] bullets;
		switch (fireType) {
			case Award.DOUBLE_FIRE:
                bullets=new Bullet[2];
                bullets[0]=new Bullet(x+xStep, y-yStep);
                bullets[1]=new Bullet(x+3*xStep, y-yStep);
                break;
			case Award.THREE_FIRE:
				bullets=new Bullet[3];
				bullets[0]=new Bullet(x+xStep, y-yStep);
				bullets[1]=new Bullet(x+2*xStep, y-yStep);
				bullets[2]=new Bullet(x+3*xStep, y-yStep);
				break;
			case Award.FIVE_FIRE:
				bullets = new Bullet[7];
				bullets[0] = new Left45X(x+2*xStep, y-yStep);
				bullets[1] = new LeftXX(x+2*xStep, y-yStep);
				bullets[2] = new Left60X(x+2*xStep, y-yStep);
				bullets[3] = new Center90X(x+2*xStep, y-yStep);
				bullets[4] = new Right60X(x+2*xStep, y-yStep);
				bullets[5] = new RightXX(x+2*xStep, y-yStep);
				bullets[6] = new Right45X(x+2*xStep, y-yStep);
				break;
			default:
				bullets = new Bullet[1];
				bullets[0] = new Bullet(x+2*xStep, y-yStep);
				break;
		}
		return bullets;
	}

	public void moveTo(int x, int y) {//移动英雄
		this.x = x-this.width/2;
		this.y = y-this.height/2;
	}
	public boolean outOfbounds() {
		return false;
	}//出界

	public void setInvincible(double time) {
	    invincible = time;
	}

	@Override
	public void step() { // 下一步
		image = images[index++%2];
		invincible-=ShootGame.interval;
	}

	public int getLife() {//获取命数
		return life;
	}

	public boolean hit(FlyingObject air) {
		int x = air.getX();
		int y = air.getY();
		return x>this.getX()-air.getWidth() && x<this.getX()+this.getWidth()&& y>this.getY()-air.getHight() && y<this.getY()+this.getHight();
	}

	public void subLife() {
		life--;
		setInvincible(3000);
	}

	public void addLife() {
		life++;
	}

	public void setFire(int fireType) {
		if(fireType>this.fireType)this.fireType = fireType;
	}
	
	public void resetFire() {
		fireType = 0;
	}
}
