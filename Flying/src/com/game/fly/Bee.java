package com.game.fly;

import java.util.Random;

public class Bee extends FlyingObject implements Award {
    //蜜蜂的走位 是斜着走的
    private int xSpeed = 1;      //x轴的速度
    private int ySpeed = 2;      //y轴的速度
    //奖励类型
    private int awardType;       //奖励类型

    public Bee() {
        image = ShootGame.bee;
        width = image.getWidth();
        height = image.getHeight();
        y = 0;
        Random rad = new Random();
        x = rad.nextInt(ShootGame.WIDTH - width);
        awardType = rad.nextInt(2);
    }

    @Override
    public int getType() {

        return awardType;
    }

    @Override
    public void step() {
        y += ySpeed;
        x += xSpeed;
        if (x < 0) {
            xSpeed = 1;
        } else if (x > ShootGame.WIDTH - width) {
            xSpeed = -1;
        }
    }

    @Override
    public boolean outOfBounds() {
        return y > ShootGame.HEIGHT;
    }
}
