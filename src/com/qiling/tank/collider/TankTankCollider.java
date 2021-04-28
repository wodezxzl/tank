package com.qiling.tank.collider;

import com.qiling.tank.GameObject;
import com.qiling.tank.Tank;

public class TankTankCollider implements Collider{

    @Override
    public boolean collider(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;

            if (t1.getRect().intersects(t2.getRect())) {
                t1.reverse();
                t2.reverse();
            }
        }

        return true;
    }
}
