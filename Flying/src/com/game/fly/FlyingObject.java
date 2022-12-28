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
}
