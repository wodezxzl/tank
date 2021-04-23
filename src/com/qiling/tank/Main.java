package com.qiling.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame frame = new TankFrame();

        // 初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            frame.enemyTanks.add(new Tank(50 + 80 * i, 200, Dir.DOWN, frame));
        }

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
        }
    }
}
