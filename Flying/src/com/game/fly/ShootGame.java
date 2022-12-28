package com.game.fly;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这是一个测试类
 */
public class ShootGame extends JPanel {
    public static final int WIDTH = 400;        //窗体的宽
    public static final int HEIGHT = 654;       //窗体的高
    public static BufferedImage background;     //背景
    public static BufferedImage start;          //开始
    public static BufferedImage gameover;       //结束
    public static BufferedImage pause;          //暂停
    public static BufferedImage airplane;       //敌机
    public static BufferedImage bee;            //蜜蜂
    public static BufferedImage bullet;         //子弹
    public static BufferedImage hero0;          //英雄机1
    public static BufferedImage hero1;          //英雄机2

    public FlyingObject[] flyings = {};     //存放飞行物敌机和蜜蜂的数组

    public Hero hero = new Hero();         //英雄飞机对象

    public Bullet[] bullets = {};           //子弹对象

    public ShootGame() {
        flyings = new FlyingObject[2];
        flyings[0] = new Airplane();
        flyings[1] = new Bee();
    }

    //加载图片资源
    static {
        try {
            background = ImageIO.read(ShootGame.class.getResource("images/background.png"));
            start = ImageIO.read(ShootGame.class.getResource("images/start.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("images/gameover.png"));
            pause = ImageIO.read(ShootGame.class.getResource("images/pause.png"));
            airplane = ImageIO.read(ShootGame.class.getResource("images/airplane.png"));
            bee = ImageIO.read(ShootGame.class.getResource("images/bee.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("images/bullet.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("images/hero0.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("images/hero1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //重写绘画方法
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintFlyingObject(g);
        paintHero(g);
        paintBullets(g);
        paintScore(g);
    }

    //画敌机和蜜蜂
    public void paintFlyingObject(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
        }

    }

    //画英雄机
    public void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, null);
    }

    //画子弹
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
        }
    }

    //画得分
    public void paintScore(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.setColor(new Color(0xFF0000));
//        g.drawString("SCORE:" + score,20,25);
//        g.drawString("LIFE:" + hero.getLife(),20,45);
//        g.drawString("TIME:" + time1,20,65);
    }

    //设置总的执行方法
    public void action() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (true) {  //state == RUNNING
                    enterAction();
                    stepAction();
                }
                repaint();  //重画
            }
        }, 10, 10); //计时器启动之后10毫秒第一次运行、之后每个10毫秒调用一次
    }

    //生成飞行物的比例
    public static FlyingObject nextOne() {
        Random rad = new Random();
        int n = rad.nextInt(10);
        if (n == 0) {
            return new Bee();
        } else {
            return new Airplane();
        }
    }

    //设置飞行物进入的方法
    private int findex = 0; //设置计数器 每次调用时自增
    public void enterAction() {
        findex++;
        if (findex%60 == 0){    //设置飞行物进入都频率（大概400毫秒出现一家敌机）
            FlyingObject obj = nextOne();   //随机生成一个对象
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    //飞行物的移动
    public void stepAction() {
        hero.step();
        //让每一架敌机和蜜蜂都往前走一步
        for (int i = 0; i < flyings.length; i++) {
            flyings[i].step();
        }
        //让每个子弹都往前走一步
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
    }


    public static void main(String[] args) {
        //创建窗体
        JFrame frame = new JFrame("飞机大战");

        //创建面板
        ShootGame game = new ShootGame();

        //将面板加到窗体里
        frame.add(game);

        //设置窗体大小
        frame.setSize(WIDTH, HEIGHT);

        //设置可见
        frame.setVisible(true);

        //设置可关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //初始位置再屏幕正中间
        frame.setLocationRelativeTo(null);

        //总在最上层
        frame.setAlwaysOnTop(true);

        game.action();
    }
}
