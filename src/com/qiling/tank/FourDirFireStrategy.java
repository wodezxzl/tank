package com.qiling.tank;

// 向四个方向开火的策略
public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank t) {
        int bx = t.getX() + (Tank.getWIDTH() >> 1) - (Bullet.getWIDTH() >> 1);
        int by = t.getY() + (Tank.getHEIGHT() >> 1) - (Bullet.getHEIGHT() >> 1);

        Dir[] dirs = Dir.values();

        for (Dir dir : dirs) {
            new Bullet(bx, by, dir, t.gm, t.getGroup());
        }

        // 坦克开火音乐
        if (t.getGroup() == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
