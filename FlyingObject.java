package lesson04;

import java.awt.image.BufferedImage;

//飞行物类
public abstract class FlyingObject {
	protected int x; // 横坐标
	protected int y; // 纵坐标
	protected int width;// 宽
	protected int height; // 高
	protected BufferedImage image; // 飞行物图片

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHight() {
		return height;
	}
	public void setHight(int hight) {
		this.height = hight;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public abstract boolean outOfbounds(); // 出界处理
	public abstract void step(); // 下一步
	public boolean shootBy(Bullet bullet) { //子弹击中目标
		int x = bullet.getX();
		int y = bullet.getY();
		return x>=this.x-bullet.getWidth() && x <= this.x+this.getWidth()
				&& y>=this.y-bullet.getHight() && y<=this.y+this.getHight();
	}

}

