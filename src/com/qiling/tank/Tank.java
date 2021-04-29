package com.qiling.tank;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Tank extends GameObject{
    private int x, y;
    private Dir dir;
    private static final int WIDTH = ResourceMgr.goodTankL.getWidth();
    private static final int HEIGHT = ResourceMgr.goodTankL.getHeight();
    private static final int SPEED = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("tankSpeed")));
    // 坦克是否在移动
    private boolean moving = false;
    GameModel gm = null;
    private boolean living = true;
    // 坦克的阵营
    private Group group = Group.BAD;
    private Random random = new Random();
    // 用于碰撞检测
    Rectangle rect = new Rectangle();
    // 用于记录上一个位置
    private int oldX, oldY;

    public Rectangle getRect() {
        return rect;
    }

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

    public Tank(int x, int y, Dir dir, GameModel gm, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        // 为了在Tank中能够访问到TankFrame, 需要初始化时传进来
        this.gm = gm;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
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

    @Override
    public void paint(Graphics g) {
        if (!living) gm.remove(this);
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (!moving) return;
        oldX = x;
        oldY = y;
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

        // 更新rect
        rect.x = this.x;
        rect.y = this.y;

        // 敌方坦克5%概率自动开火和随机方向
        if (this.group == Group.BAD) {
            if (random.nextInt(100) > 95) {
                this.fire(DefaultFireStrategy.getInstance());
                // 敌方坦克随机方向
                randomDir();
            }

        }

        // 越界检测
        checkBeyondBorder();
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    private void checkBeyondBorder() {
        if (this.x < 30 ) x = 30;
        if (this.y < 30) y = 30;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    public void fire(FireStrategy fireStrategy) {
        fireStrategy.fire(this);
    }

    public void die() {
        this.living = false;
    }

    public void back() {
        x = oldX;
        y = oldY;
    }
}
