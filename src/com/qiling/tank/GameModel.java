package com.qiling.tank;

import com.qiling.tank.collider.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameModel {
    Tank MyTank = new Tank(200, 400, Dir.UP, this, Group.GOOD);
    List<GameObject> objects = new ArrayList<>();
    ColliderChain chain = new ColliderChain();

    public GameModel(){
        // 初始化我方坦克
        objects.add(MyTank);

        // 初始化敌方坦克
        int initBadTanksCount = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("initBadTanksCount")));
        for (int i = 0; i <= initBadTanksCount; i++) {
            Tank tank = new Tank(50 + 80 * i, 200, Dir.DOWN, this, Group.BAD);
            // 地方坦克自动移动
            tank.setMoving(true);
            add(tank);
        }

        // 初始化墙
        add(new Wall(150, 150, 200, 50));
        add(new Wall(550, 150, 200, 50));
        add(new Wall(300, 300, 50, 200));
        add(new Wall(550, 300, 50, 200));
    };

    public void add(GameObject obj) {
        this.objects.add(obj);
    }

    public void remove(GameObject obj) {
        this.objects.remove(obj);
    }

    public Tank getMyTank() {
        return MyTank;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        // 碰撞检测
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                chain.collider(o1, o2);
            }
        }
    }
}
