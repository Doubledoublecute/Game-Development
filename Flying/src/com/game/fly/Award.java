package com.game.fly;

/**
 * 这是一个奖励接口
 */
public interface Award {
    int DOUBLE_FIRE = 0;    //双倍火力
    int LIFE = 1;           //生命值

    int getType();          //获取奖励类型
}
