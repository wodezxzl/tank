package com.qiling.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class TankFrame extends Frame {
    static final int
            GAME_WIDTH = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("gameWidth"))),
            GAME_HEIGHT = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("gameHeight")));
    GameModel gm = new GameModel();

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

    // 将游戏画在屏幕上
    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    // 消除闪烁现象
    Image offscreenImage = null;
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

    // 通过按键来控制主站坦克
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
                    gm.getMyTank().fire(instance);
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            // 通过GameModel获取主战坦克
            Tank MyTank = gm.getMyTank();

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
