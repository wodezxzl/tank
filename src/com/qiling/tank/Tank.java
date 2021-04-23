package com.qiling.tank;

import java.awt.*;

public class Tank {
    private int x, y;
    private Dir dir;
    private static final int SPEED = 5;
    // 坦克是否在移动
    private boolean moving = false;
    // 获取tankFrame的引用, 方便给它传递子弹
    private final TankFrame tankFrame;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        // 为了在Tank中能够访问到TankFrame, 需要初始化时传进来
        this.tankFrame = tankFrame;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);
        move();
    }

    private void move() {
        if (!moving) return;
        // 判断方向移动
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }
    }

    public void fire() {
        tankFrame.bullets.add(new Bullet(this.x, this.y, this.dir));
    }
}
