package com.qiling.tank;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Dir dir;
    private static final int WIDTH = ResourceMgr.tankL.getWidth();
    private static final int HEIGHT = ResourceMgr.tankL.getHeight();
    private static final int SPEED = 5;
    // 坦克是否在移动
    private boolean moving = false;
    // 获取tankFrame的引用, 方便给它传递子弹
    private final TankFrame tankFrame;
    private boolean living = true;
    // 坦克的阵营
    private Group group = Group.BAD;
    private Random random = new Random();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        // 为了在Tank中能够访问到TankFrame, 需要初始化时传进来
        this.tankFrame = tankFrame;
        this.group = group;
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
        if (!living) tankFrame.enemyTanks.remove(this);
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD, x, y, null);
                break;
        }
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

        if (random.nextInt(10) > 8) this.fire();
    }

    public void fire() {
        tankFrame.bullets.add(new Bullet(this.x, this.y, this.dir, this.tankFrame, this.group));
    }

    public void die() {
        this.living = false;
    }
}
