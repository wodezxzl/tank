package com.qiling.tank;

public class Main {
    public static void main(String[] args) {
        TankFrame frame = new TankFrame();

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
