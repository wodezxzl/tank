package com.qiling.tank.collider;

import com.qiling.tank.Bullet;
import com.qiling.tank.Explode;
import com.qiling.tank.GameObject;
import com.qiling.tank.Tank;

public class BulletTankCollider implements Collider{
    @Override
    public boolean collider(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;

            if (b.getRect().intersects(t.getRect())) {
                // 来自同一方打出的子弹与自己相撞不死亡
                if (t.getGroup() == b.getGroup()) return false;

                t.die();
                b.die();

                // 添加爆炸对象
                int ex = t.getX() + (Tank.getWIDTH() >> 1) - (Explode.getWIDTH() >> 1);
                int ey = t.getY() + (Tank.getHEIGHT() >> 1) - (Explode.getHEIGHT() >> 1);
                b.getGM().add(new Explode(ex, ey, b.getGM()));

                return false;
            }
        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            collider(o2, o1);
        }

        return true;
    }
}

