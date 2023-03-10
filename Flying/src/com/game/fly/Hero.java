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
            bullets[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
            bullets[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
            doubleFire -= 2;
            return bullets;

        } else {     //如果时单倍火力
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(this.x + 2 * xStep, this.y - yStep);
            return bullets;
        }
    }

    //英雄机的碰撞判断
    public boolean hit(FlyingObject enemy) {      //注意有参的方法
        int x1 = enemy.x - this.width / 2;
        int x2 = enemy.x + enemy.width + this.width / 2;
        int y1 = enemy.y - this.height / 2;
        int y2 = enemy.y + enemy.height + this.height / 2;
        int heroX = this.x + this.width / 2;
        int heroY = this.y + this.height / 2;

        return heroX > x1 && heroX < x2 && heroY > y1 && heroY < y2;
    }

    //为英雄机添加移动方法
    public void moveTo(int x, int y) {
        //注意位置都偏差，宽和高差1/2
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    //双倍火力
    public void addDoubleFire() {
        doubleFire += 40;
    }

    //增加生命
    public void addLife() {
        life++;
    }

    public int getLife() {
        return life;
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

    //对生命值做减法
    public void subStractLife() {
        life--;
    }

    //将双倍火力变单倍火力（通过参数传递的方法实现）
    public void setDoubleFire(int doubleFire) {
        this.doubleFire = doubleFire;   //之后要传递参数0过来 将doubleFire设置为0
    }

}
