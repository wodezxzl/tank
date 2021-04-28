package com.qiling.tank.collider;

import com.qiling.tank.GameObject;
import com.qiling.tank.PropertyMgr;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider{
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        String colliderStr = PropertyMgr.get("colliders");
        assert colliderStr != null;
        String[] strings = colliderStr.split(",");
        for (int i = 0; i < strings.length; i++) {
            try {
                Collider instance = (Collider) Class.forName(strings[i]).getDeclaredConstructor().newInstance();
                add(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    public boolean collider(GameObject o1, GameObject o2) {
        // 根据返回值决定是否还沿着检测链向下检测
        for (int i = 0; i < colliders.size(); i++) {
            if (!(colliders.get(i).collider(o1, o2))) return false;
        }

        return true;
    }
}
