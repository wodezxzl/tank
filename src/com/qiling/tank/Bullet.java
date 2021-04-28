package com.qiling.tank;

import java.awt.*;
import java.util.Objects;

public class Bullet {
    private static final int SPEED = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("bulletSpeed")));
    private static final int WIDTH = ResourceMgr.bulletD.getWidth();
    private static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    private final Dir dir;
    private boolean living = true;
    private final GameModel gm;
    // 用于碰撞检测
    Rectangle rect = new Rectangle();

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group = Group.BAD;

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Bullet(int x, int y, Dir dir, GameModel gm, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.gm = gm;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        // new出子弹后自己加入bullets集合
        gm.bullets.add(this);
    }

    public void paint(Graphics g) {
        if (!living) {
            gm.bullets.remove(this);
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

        // 更新rect
        rect.x = this.x;
        rect.y = this.y;

        // 判断是否清除子弹
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    // 碰撞检测
    public void collide(Tank tank) {
        // 来自坦克自己打出的子弹与自己相撞不死亡
        if (this.group == tank.getGroup()) return;

        if (rect.intersects(tank.rect)) {
            tank.die();
            this.die();

            // 添加爆炸对象
            int ex = tank.getX() + (Tank.getWIDTH() >> 1) - (Explode.getWIDTH() >> 1);
            int ey = tank.getY() + (Tank.getHEIGHT() >> 1) - (Explode.getHEIGHT() >> 1);
            gm.explodes.add(new Explode(ex, ey, gm));
        }
    }

    private void die() {
        this.living = false;
    }
}
