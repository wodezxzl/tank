package com.qiling.tank;

import java.awt.*;

public class Bullet {
    private static final int SPEED = 10;
    private static final int WIDTH = ResourceMgr.bulletD.getWidth();
    private static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    private final Dir dir;
    private boolean living = true;
    private final TankFrame tankFrame;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x + (Tank.getWIDTH() >> 1) - (WIDTH >> 1);
        this.y = y + (Tank.getHEIGHT() >> 1) - (HEIGHT >> 1);
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!living) {
            tankFrame.bullets.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
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

        // 判断是否清除子弹
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    // 碰撞检测
    public void collide(Tank tank) {
        // 来自坦克自己打出的子弹与自己相撞不死亡
        if (this.group == tank.getGroup()) return;

        Rectangle rectBullet = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(), Tank.getWIDTH(), Tank.getHEIGHT());
        if (rectBullet.intersects(rectTank)) {
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.living = false;
    }
}
