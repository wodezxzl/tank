package com.qiling.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameModel {
    Tank MyTank = new Tank(200, 400, Dir.UP, this, Group.GOOD);
    java.util.List<Bullet> bullets = new ArrayList<>();
    java.util.List<Tank> enemyTanks = new ArrayList<>();
    List<Explode> explodes = new ArrayList<>();

    public GameModel(){
        // 初始化敌方坦克
        int initBadTanksCount = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("initBadTanksCount")));
        for (int i = 0; i <= initBadTanksCount; i++) {
            enemyTanks.add(new Tank(50 + 80 * i, 200, Dir.DOWN, this, Group.BAD));
        }
    };

    public Tank getMyTank() {
        return MyTank;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量" + bullets.size(), 10, 60);
        g.drawString("敌人的数量" + enemyTanks.size(), 10, 80);
        MyTank.paint(g);
        // 增强for循环不能删除元素
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTanks.get(i).setMoving(true);
            enemyTanks.get(i).paint(g);
        }

        // 画出所有爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        // 碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            // 所有子弹也要和主坦克进行碰撞检测
            bullets.get(i).collide(MyTank);
            for (int j = 0; j < enemyTanks.size(); j++) {
                bullets.get(i).collide(enemyTanks.get(j));
            }
        }
    }
}
