package com.game.fly;

import java.awt.image.BufferedImage;


/**
 * 一般把属性设置成私有的
 * 方法设置为公有的
 */
public abstract class FlyingObject {
    protected int width;                  //宽度
    protected int height;                 //高度
    protected int x;                      //x坐标
    protected int y;                      //y坐标
    protected BufferedImage image;        //图片

    //飞行物移动
    public abstract void step();

    //判断子弹是否击中飞行物
    public boolean shootBy(Bullet bullet) {
        int x = bullet.x;
        int y = bullet.y;
        return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
    }

    //判断是否越界
    public abstract boolean outOfBounds();
}
