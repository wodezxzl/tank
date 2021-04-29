package com.qiling.tank.collider;

import com.qiling.tank.*;

public class BulletWallCollider implements Collider{
    @Override
    public boolean collider(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Wall) {
            Bullet b = (Bullet) o1;
            Wall t = (Wall) o2;

            if (b.getRect().intersects(t.getRect())) {
                b.die();
            }
        } else if (o1 instanceof Wall && o2 instanceof Bullet) {
            collider(o2, o1);
        }
        return true;
    }
}
