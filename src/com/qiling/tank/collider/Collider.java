package com.qiling.tank.collider;

import com.qiling.tank.GameObject;

public interface Collider {
    /**
     * @return false 表示不再继续沿着链条向下判断, ture表示继续判断
     */
    boolean collider(GameObject o1, GameObject o2);
}
