package lesson04;

/**
 * Created by he on 2017/3/12.
 */

//奖励接口
public interface Award {
    int LIFE = 0; //加命
    int DOUBLE_FIRE = 1; //双发子弹
    int THREE_FIRE = 2; //三发子弹
    int FIVE_FIRE = 3;  //5发子弹

    int getType(); // 获取奖励类型
}
