package com.qiling.tank;

public class Main {
    public static void main(String[] args) {
        // 初始化游戏
        TankFrame frame = new TankFrame();

        // 加入背景音乐
        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

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
