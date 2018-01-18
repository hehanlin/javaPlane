package lesson04.bullet;

import lesson04.Bullet;
import lesson04.ShootGame;

/**
 * Created by he on 2017/3/14.
 */
public class Right60X extends Bullet{
    public int speed = 10;

    public Right60X(int x, int y) {
        super(x, y);
        image=ShootGame.bullet1;
		width=image.getWidth();
		height=image.getHeight();
    }
    @Override
    public void step() {
        this.x += speed/2;
        this.y -= speed;
    }
}
