package com.game.fly;


import java.util.Random;

public class Airplane extends FlyingObject implements Enemy {
    //特有属性
    private int speed = 2;               //敌机移动的步数

    /**
     * 构造方法
     */
    public Airplane() {
        image = ShootGame.airplane;      //加载图片
        width = image.getWidth();        //获取宽度
        height = image.getHeight();      //获取高度
        y = 0;//初始位置
        Random rad = new Random();
        x = rad.nextInt(0,ShootGame.WIDTH - width);//确定生成位置
    }

    @Override
    public int getScore() {

        return 0;
    }

    @Override
    public void step() {

    }
}
