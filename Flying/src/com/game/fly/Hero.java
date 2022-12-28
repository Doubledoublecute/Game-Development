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
    public void step() {
        int num = index++ / 2 % images.length;
        image = images[num];
    }

    //英雄机发射自当都方法
    public Bullet[] shoot() {
        int xStep = this.width / 4;
        int yStep = 20;
        if (doubleFire > 0) {  //判断是否为单倍火力
            Bullet[] bullets = new Bullet[2];
            bullets[0] = new Bullet(this.x + 1 * xStep,this.y - yStep);
            bullets[1] = new Bullet(this.x + 3 * xStep,this.y - yStep);
            doubleFire -= 2;
            return bullets;

        } else {     //如果时单倍火力
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(this.x + 2 * xStep,this.y - yStep);
            return bullets;
        }
    }
}
