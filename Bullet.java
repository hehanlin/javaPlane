package lesson04;


//子弹类
public class Bullet extends FlyingObject {
	protected int speed=3; // 飞行速度

	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
		image=ShootGame.bullet;
		width=image.getWidth();
		height=image.getHeight();
	}

	@Override
	public boolean outOfbounds() { // 出界
		return y<-height;
	}

	@Override
	public void step() { // 下一步
		y-=speed;
	}

}
