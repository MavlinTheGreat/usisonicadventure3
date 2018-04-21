package usi;

import java.util.Random;
public class pineapple {
    int startx;
    long startTime;
    final static int pinespeed = 50;
    final static int pinewidth = 100;
    final static int gapsize = 600;
    int yGC;
    final static int gapH = 150;
    final static Random gapRand = new Random();
    boolean isCOUNTED = false;
    pineapple(int startx, int height) {
        startTime = System.currentTimeMillis();
        yGC = gapRand.nextInt(height - 500) + 250;
        System.out.println(this.startTime);
        this.startx = startx;
    }

    int calX() {
        return (int) (startx - pinespeed * (System.currentTimeMillis() - startTime) / 60);
    }

}
