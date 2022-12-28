package com.game.fly;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
    private BufferedImage[] images = {};
    private int index;          //计数器
    private int doubleFire;     //双倍火力
    private int life;           //英雄机生命值

    public Hero() {
        image = ShootGame.hero0;
        width = image.getWidth();
        height = image.getHeight();
        x = 150;
        y = 400;
        doubleFire = 0;     //如果双倍火力等于0 说明是单被火力 如果大于0 说明是双倍火力
        life = 3;
        images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1};
    }

    @Override
    public void step(){
        int num = index++ / 2 % images.length;
        image = images[num];
    }
}
