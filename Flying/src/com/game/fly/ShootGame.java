package com.game.fly;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private int score = 0;
    private int state;  //状态
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

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
        paintState(g);
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
        g.drawString("SCORE:" + score, 20, 25);
        g.drawString("LIFE:" + hero.getLife(), 20, 45);
//        g.drawString("TIME:" + time1,20,65);
    }

    //画状态
    public void paintState(Graphics g) {
        switch (state) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameover, 0, 0, null);
                break;
        }
    }

    //设置总的执行方法
    public void action() {
        MouseAdapter l = new MouseAdapter() {   //设置监听
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    //获取鼠标坐标
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveTo(x, y);
                }
            }

            //鼠标点击监听
            public void mouseClicked(MouseEvent e) {
                switch (state) {
                    case START:
                        state = RUNNING;//状态变成运行状态
                        break;
                    case GAME_OVER:
                        hero = new Hero();  //重新加载一个英雄机对象
                        flyings = new FlyingObject[0];  //飞行物数组清零
                        bullets = new Bullet[0];    //子弹数组清零
                        score = 0;  //得分清零
                        state = START;  //状态要设置
                        break;
                }
            }

            //暂停操作第一步 变成暂停状态
            public void mouseExited(MouseEvent e) {
                if (state != GAME_OVER) {    //游戏没有结束的情况下 才能暂停
                    state = PAUSE;
                }
            }

            //暂停操作第二部 变成运行状态
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {    //暂停状态下 把鼠标移回窗体 变为运行状态
                    state = RUNNING;
                }
            }

        };

        //将监听器添加到面板上
        this.addMouseListener(l);
        this.addMouseMotionListener(l);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (state == RUNNING) {  //state == RUNNING
                    enterAction();
                    stepAction();
                    shootAction();
                    bangAction();
                    outOfBounds();
                    checkGameOverAction();
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
        if (findex % 60 == 0) {    //设置飞行物进入都频率（大概400毫秒出现一家敌机）
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

    //发射子弹
    int shootIndex = 0;

    public void shootAction() {
        shootIndex++;   //设置子弹发射都频率
        if (shootIndex % 30 == 0) {
            Bullet[] bs = hero.shoot();//调用发射子弹都方法
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);//数组扩容
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }


    //遍历每一颗子弹
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            bang(b);
        }
    }

    public void bang(Bullet b) {
        int index = -1; //击中飞行物的索引（判断击中的是哪一个飞行物）
        for (int i = 0; i < flyings.length; i++) {  //遍历每一个飞行物
            if (flyings[i].shootBy(b)) { //调用shootBy方法 判断子弹和敌人是否发生碰撞
                index = i;  //记录被击中的飞行物的下标
                break;
            }
        }
        //击中
        if (index != -1) {
            FlyingObject one = flyings[index];  //把击中都飞行物赋值给one
            flyings[index] = flyings[flyings.length - 1];//把击中的飞行物和数组中最后一个
            flyings = Arrays.copyOf(flyings, flyings.length - 1);//删除掉数组中最后一个元素

            if (one instanceof Enemy) {  //击中敌机
                score += ((Enemy) one).getScore();
            } else if (one instanceof Award) { //击中蜜蜂
                int type = ((Award) one).getType();
                switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                }
            }
        }
    }


    //删除越界飞行物
    public void outOfBounds() {
        int index = 0;
        FlyingObject[] flyingLives = {};
        for (int i = 0; i < flyings.length; i++) {
            if (!flyings[i].outOfBounds()) {
                flyingLives = Arrays.copyOf(flyingLives, flyingLives.length + 1);
                flyingLives[index++] = flyings[i];
            }
        }
        flyings = Arrays.copyOf(flyingLives, flyingLives.length);

        index = 0;
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].outOfBounds()) {
                bulletLives[index++] = bullets[i];
            }
        }
        bullets = Arrays.copyOf(bulletLives, index);
    }

    //判断是否发生碰撞并进行删除 和 对应的惩罚措施
    public boolean isGameOver() {
        //遍历所有飞行物
        for (int i = 0; i < flyings.length; i++) {
            int index = -1; //标记被碰撞的飞行物
            FlyingObject obj = flyings[i];  //每一个飞行物
            if (hero.hit(obj)) {
                hero.subStractLife();   //减少一个命
                hero.setDoubleFire(0);  //设置为单倍火力
                index = i;
            }
            //删除掉被碰撞的飞行物
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t;
                flyings = Arrays.copyOf(flyings, flyings.length - 1); //数值缩容
            }
        }
        return hero.getLife() <= 0;
    }

    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAME_OVER;
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
