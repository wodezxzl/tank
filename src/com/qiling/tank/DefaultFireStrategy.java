package com.qiling.tank;

public class DefaultFireStrategy implements FireStrategy {
    private DefaultFireStrategy() {};

    private static class Single{
        private static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }

    @Override
    public void fire(Tank t) {
        int bx = t.getX() + (Tank.getWIDTH() >> 1) - (Bullet.getWIDTH() >> 1);
        int by = t.getY() + (Tank.getHEIGHT() >> 1) - (Bullet.getHEIGHT() >> 1);
        new Bullet(bx, by, t.getDir(), t.tankFrame, t.getGroup());

        // 坦克开火音乐
        if (t.getGroup() == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }

    public static DefaultFireStrategy getInstance() {return Single.INSTANCE;}
}
