package lesson04.bullet;

import lesson04.Bullet;
import lesson04.ShootGame;

/**
 * Created by he on 2017/3/14.
 */
public class RightXX extends Bullet{
    public double speed = 4;
    
    public RightXX(int x, int y) {
        super(x, y);
        image=ShootGame.bullet1;
		width=image.getWidth();
		height=image.getHeight();
    }

    @Override
    public void step() {
        this.x += speed;
        this.y -= speed*speed;
    }
}
