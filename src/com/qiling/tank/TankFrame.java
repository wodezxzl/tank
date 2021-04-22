package com.qiling.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    Tank MyTank = new Tank(200, 200, Dir.UP);
    Bullet b = new Bullet(300 , 300, Dir.UP);

    public TankFrame() {
        setSize(700, 400);
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
        MyTank.paint(g);
        b.paint(g);
    }

    class MyKeyListener extends KeyAdapter{
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
