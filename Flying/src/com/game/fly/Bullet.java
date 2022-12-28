package com.game.fly;

public class Bullet extends FlyingObject {

    private int speed = 4;      //子弹的移动速度

    /**
     * 有参构造方法
     * @param x 英雄机的x
     * @param y 英雄机的y
     */
    public Bullet(int x, int y) {
        image = ShootGame.bullet;
        width = image.getWidth();
        height = image.getHeight(); 
        this.x = x;
        this.y = y;
    }

    @Override
    public void step() {

    }
}
