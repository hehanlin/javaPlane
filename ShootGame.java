package lesson04;

import lesson04.award.DoubleFire;
import lesson04.award.FiveFire;
import lesson04.award.Life;
import lesson04.award.ThreeFire;
import lesson04.boss.Boss;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShootGame extends JPanel {
	public static final int WIDTH = 400; // 窗口宽
	public static final int HIGHT = 654; // 窗口高
	public static BufferedImage background; // 背景图片
	public static BufferedImage airplane; // 敌机图片
	public static BufferedImage airplane1; // 敌机图片
    public static BufferedImage airplaneDown1; //敌机坠机1
    public static BufferedImage airplaneDown2; //敌机坠机2
    public static BufferedImage airplaneDown3; //敌机坠机3
    public static BufferedImage airplaneDown4; //敌机坠机4
    public static BufferedImage fastAirplane; //快速敌机
    public static BufferedImage fastAirplaneDown1;
    public static BufferedImage fastAirplaneDown2;
    public static BufferedImage fastAirplaneDown3;
    public static BufferedImage fastAirplaneDown4;
	public static BufferedImage bee; // 补给图
	public static BufferedImage bullet; // 子弹图
	public static BufferedImage bullet1; // 子弹图
	public static BufferedImage gameover; // gameover状态图
	public static BufferedImage hero0; // 英雄飞机1图
	public static BufferedImage hero1; // 英雄飞机2图
	public static BufferedImage hero2; // 透明英雄
	public static BufferedImage pause; // 暂停图
	public static BufferedImage start; // 开始图
	public static BufferedImage boss; // boss 图
	public Boss myBoss = null;
	private Hero hero = new Hero(); // 英雄飞机对象
	private int state = START; // 游戏状态
	private static final int START = 0; // 准备开始
	private static final int RUNNING = 1; // 正在游戏中
	private static final int PAUSE = 2; // 暂停
	private static final int GAME_OVER = 3; // 游戏结束
	private Bullet[] bullets = {}; // 子弹数组
	private Timer timer; // 定时器
	public static int interval = 10; //定时器间隔毫秒
	private int shootIndex = 0; // 控制子弹发射频率
	private int airPlaneIndex = 0; // 控制敌机出现频率
	private FlyingObject[] flyings = {}; // 飞行物数组
	private int score = 0; // 得分数
	private static AudioClip ac;
	private static AudioClip ac_bullet;
	private static AudioClip ac_bang;
	private static AudioClip ac_award;

	static {
		try {//载入图片
			background = ImageIO.read(ShootGame.class.getResource("statics/background.jpg"));
			airplane = ImageIO.read(ShootGame.class.getResource("statics/airplane.png"));
			airplane1 = ImageIO.read(ShootGame.class.getResource("statics/fastairplane.png"));
            airplaneDown1 = ImageIO.read(ShootGame.class.getResource("statics/airplane_down1.png"));
            airplaneDown2 = ImageIO.read(ShootGame.class.getResource("statics/airplane_down2.png"));
            airplaneDown3 = ImageIO.read(ShootGame.class.getResource("statics/airplane_down3.png"));
            airplaneDown4 = ImageIO.read(ShootGame.class.getResource("statics/airplane_down4.png"));
			bee = ImageIO.read(ShootGame.class.getResource("statics/bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("statics/b.png"));
			bullet1 = ImageIO.read(ShootGame.class.getResource("statics/bullet.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("statics/gameover.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("statics/hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("statics/hero1.png"));
			hero2 = ImageIO.read(ShootGame.class.getResource("statics/touming.png"));
			pause = ImageIO.read(ShootGame.class.getResource("statics/pause.png"));
			start = ImageIO.read(ShootGame.class.getResource("statics/start.png"));
			boss = ImageIO.read(ShootGame.class.getResource("statics/boss.png"));
			ac = Applet.newAudioClip(ShootGame.class.getResource("statics/1.wav"));
			ac_bullet = Applet.newAudioClip(ShootGame.class.getResource("statics/bullet.wav"));
			ac_bang = Applet.newAudioClip(ShootGame.class.getResource("statics/bang.wav"));
			ac_award = Applet.newAudioClip(ShootGame.class.getResource("statics/award.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, null); // 绘制背景
		paintHero(g); // 绘制英雄
		paintBullet(g); // 绘制子弹
		paintAirplane(g); // 绘制敌机
		paintScoreAndLife(g); // 绘制分数和命数
		paintState(g); // 绘制游戏状态

	}

	//绘制游戏状态
    public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}

    //绘制子弹
	public void paintBullet(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(), null);
        }
	}

	//绘制敌机,boss,奖品
	public void paintAirplane(Graphics g) {
		for(int i = 0; i< flyings.length; i++) {
			FlyingObject air = flyings[i];
			g.drawImage(air.getImage(), air.getX(), air.getY(), null);
		}
	}

	//绘制分数及命数
	public void paintScoreAndLife(Graphics g) {
		int x = 10;
		int y = 25;
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 17);
		g.setFont(font);
		g.setColor(Color.green);
		g.drawString("得分:"+score, x, y);
		y+=20;
		g.drawString("生命:"+hero.getLife(), x, y);
	}

	//绘制游戏状态
	public void paintState(Graphics g) {
		switch (state) {
			case START:
				g.drawImage(start, 0, 0, null);
				break;
			case PAUSE:
				g.drawImage(pause, 0, 0, null);
				break;
			case GAME_OVER:
				g.drawImage(gameover, 0, 0, null);
				break;
		}
	}

	//判断该子弹是否击中飞行物
	public boolean bang(Bullet bullet) {
		int index = -1;
		for (int i = 0; i < this.flyings.length; i++) {
			if (flyings[i].shootBy(bullet)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			FlyingObject temp = flyings[index];
			if(temp.getY()<0) {
				return false;
			}
			if(temp instanceof Boss) {
				Boss tmp = (Boss)temp;
				pkBoss(tmp, index);
			}else {
			    if(temp instanceof Award) {
			    	Award tmp = (Award)temp;
			    	addAward(tmp);
			    	ac_award.play();
			    	flyings[index] = flyings[flyings.length - 1];
					flyings[flyings.length - 1] = temp;
					flyings = Arrays.copyOf(flyings, flyings.length - 1);
                }else {
			        Airplane a = (Airplane)temp;
			        ac_bang.play();
			        a.setDie();
                }
			}
			return true;
		}
		return false;
	}

	//射击boss
	public void pkBoss(Boss b, int bIndex) {
		if(b.hp>0) {
            b.hp--;
        }else {
            flyings[bIndex] = flyings[flyings.length-1];
            flyings = Arrays.copyOf(flyings, flyings.length - 1); //飞行物消失
            score += myBoss.getScore();
            myBoss = null;
        }
	}
	//增加奖励
	public void addAward(Award award) {
		int type = award.getType();
	    if(type==Award.LIFE) {
	    	hero.addLife();
		}else {
	    	hero.setFire(type);
		}
	}


	//判断所有子弹是否击中飞行物
	public void bangAction() {
		for (int i = 0; i< bullets.length; i++) {
			if(bang(bullets[i])) {
				Bullet b = bullets[i];
				bullets[i] = bullets[bullets.length - 1];
				bullets = Arrays.copyOf(bullets, bullets.length - 1); // 子弹消失
			}
		}
	}

	//射击子弹
	public void shootAction() {
		if(shootIndex++%20==0) {
			Bullet[] bs = hero.shoot();
			ac_bullet.play();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}

	//所有对象下一步
	public void stepAction() {
		for (int i = 0; i < bullets.length; i++) { //所有子弹下一步
			bullets[i].step();
		}
		hero.step(); //英雄下一步
		for (int i = 0; i< flyings.length; i++) { // 飞行物下一步
			flyings[i].step();
			if(flyings[i] instanceof Boss) {
				Boss tmp = (Boss)flyings[i];
				tmp.step(hero.getX(),hero.getY());
			}
		}

	}

	//生成飞行物
	public FlyingObject nextOne() {
		if(myBoss == null) {
			if((score>=400 && score<=450) || score == 4000000) {
				myBoss = new Boss();
				return myBoss;
			}
		}

		Random rand = new Random();
		int  type = rand.nextInt(20);
	    if(type%6 == 0) {
	    	if(type == 0) {
	    		return new Life();
			}else if(type == 6) {
	    		return new DoubleFire();
			}else if(type == 12) {
	    		return new ThreeFire();
			}else {
	    		return new FiveFire();
			}
		}else {
	    	if(type%4==0) {
				return new FastAirplane();
			}else {
	    		return new Airplane();
			}
		}
	}

	//飞行物入场
	public void enterAction() {
		if(airPlaneIndex++%20 == 0) {
			FlyingObject airplane = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = airplane;
		}
	}

	//飞行物及子弹出界处理
	public void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (int i=0; i< flyings.length; i++) {
			if(!flyings[i].outOfbounds()) {
				flyingLives[index] = flyings[i];
				index++;
			}
		}
		flyings=Arrays.copyOf(flyingLives,index);

		index = 0;
		Bullet[] bs = new Bullet[bullets.length];
		for (int i=0; i<bullets.length; i++) {
			if(!bullets[i].outOfbounds()) {
				bs[index] = bullets[i];
				index++;
			}
		}

		bullets=Arrays.copyOf(bs,index);
	}

	//坠机动画
    public void dieAction() {
	    int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (int i=0; i< flyings.length; i++) {
		    if(flyings[i] instanceof Airplane) {
		        Airplane tmp = (Airplane)flyings[i];
		        if(tmp.die == 0) {
		        	flyingLives[index] = flyings[i];
                    index++;
				}else if(tmp.die == 1) {
		        	tmp.showDie();
		        	flyingLives[index] = flyings[i];
                    index++;
				}else {
					score+=tmp.getScore();
		        	continue;
				}
            }else {
		        flyingLives[index] = flyings[i];
                index++;
            }
		}
		flyings=Arrays.copyOf(flyingLives,index);
    }

	//撞击敌机减命
	public void isZhuang() {
		if(hero.invincible>0){
			hero.images[1] = hero2;
			return;
		}else {
			hero.images[1] = hero1;
		};
		int index = -1;
		for (int i = 0; i < this.flyings.length; i++) {
			if (hero.hit(flyings[i])) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			FlyingObject temp = flyings[index];
			if (temp instanceof Boss) {
				Boss tmp = (Boss) temp;
				pkBoss(tmp, index);
			} else {
				ac_bang.play();
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = temp;
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
			if (temp instanceof Enemy) {
				hero.subLife();
				hero.resetFire();
			} else if (temp instanceof Award) {
				Award tmp = (Award) temp;
				addAward(tmp);
				ac_award.play();
			}
		}
	}

	public void isGameOver() {
		if(hero.getLife()<=0) {
			state = GAME_OVER;
		}
	}
	

	//执行动作
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			//鼠标移动
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {//移动英雄
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			//单击鼠标
			public void mouseClicked(MouseEvent e) {
				if(state==START) {//开始
					state = RUNNING;
				}else if(state == GAME_OVER) {
					hero = new Hero();
					bullets = new Bullet[0];
					score = 0;
					flyings = new FlyingObject[0];
					state = START;
					myBoss = null;
				}
			}
			//鼠标划出窗口
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) {//暂停
					state = PAUSE;
				}
			}
			//鼠标划入窗口
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {//继续运行
					state = RUNNING;
				}
			}
		};
		this.addMouseMotionListener(l);
		this.addMouseListener(l);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(state==RUNNING) {
					shootAction();//射出子弹
					enterAction();//飞行物入场
					stepAction();//下一步
					bangAction();//击中
                    dieAction();
					isZhuang();
					isGameOver();
					outOfBoundsAction();
				}
				repaint();//重绘
			}
		}, interval, interval);
	}


	public static void main(String[] args) throws MalformedURLException {
		JFrame frame = new JFrame("飞机大战v1.0");
		frame.setSize(WIDTH, HIGHT);

		ShootGame game = new ShootGame();
		frame.add(game);
		game.action();
		
		ac.play();
		ac.loop();
		
		
		frame.setAlwaysOnTop(true); // 窗口总在最前
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // 窗口居中
		frame.setVisible(true); // 窗口可视
	}
}
