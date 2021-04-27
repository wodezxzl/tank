package com.qiling.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TankFrame extends Frame {
    static final int
            GAME_WIDTH = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("gameWidth"))),
            GAME_HEIGHT = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("gameHeight")));
    Tank MyTank = new Tank(200, 400, Dir.UP, this, Group.GOOD);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> enemyTanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();
    // 消除闪烁现象
    Image offscreenImage = null;

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank War");
        setVisible(true);

        this.addKeyListener(new MyKeyListener());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量" + bullets.size(), 10, 60);
        g.drawString("敌人的数量" + enemyTanks.size(), 10, 80);
        MyTank.paint(g);
        // 增强for循环不能删除元素
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).setMoving(true);
            enemyTanks.get(i).paint(g);
        }

        // 画出所有爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        // 碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            // 所有子弹也要和主坦克进行碰撞检测
            bullets.get(i).collide(MyTank);
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collide(enemyTanks.get(j));
            }
        }
    }

    @Override
    public void update(Graphics g) {
        if (offscreenImage == null) {
            offscreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offscreenImage.getGraphics();
        Color c = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(c);
        paint(goffScreen);
        g.drawImage(offscreenImage, 0, 0, null);
    }

    class MyKeyListener extends KeyAdapter {
        boolean bL, bU, bR, bD;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    // 按下ctrl键发射出一颗子弹
                    // 此处使用策略模式
                    String goodFSName = PropertyMgr.get("goodFS");
                    FireStrategy instance;
                    try {
                        instance = (FireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
                    } catch (Exception e2) {
                        instance = DefaultFireStrategy.getInstance();
                        e2.printStackTrace();
                    }
                    MyTank.fire(instance);
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                // 按键松开时进入这个判断
                // 没有键被按下时坦克不动
                MyTank.setMoving(false);
            } else {
                MyTank.setMoving(true);
                if (bL) MyTank.setDir(Dir.LEFT);
                if (bU) MyTank.setDir(Dir.UP);
                if (bR) MyTank.setDir(Dir.RIGHT);
                if (bD) MyTank.setDir(Dir.DOWN);
            }
        }
    }
}
