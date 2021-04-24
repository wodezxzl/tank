package com.qiling.tank;

import java.awt.*;

public class Explode {
    private static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    private static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private int x, y;
    TankFrame tankFrame;
    // 画到第几步了
    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public void paint(Graphics g) {
        // 注意这里之前使用的是for循环, 逻辑错误, 每次进来都从0开始循环了
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length - 1) {
            tankFrame.explodes.remove(this);
        }
    }
}
