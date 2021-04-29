package com.qiling.tank.collider;

import com.qiling.tank.*;

public class TankWallCollider implements Collider{
    @Override
    public boolean collider(GameObject o1, GameObject o2) {
        if (o1 instanceof Wall && o2 instanceof Tank) {
            Wall b = (Wall) o1;
            Tank t = (Tank) o2;

            if (b.getRect().intersects(t.getRect())) {
                t.back();
            }
        } else if (o1 instanceof Tank && o2 instanceof Wall) {
            collider(o2, o1);
        }

        return true;
    }
}
