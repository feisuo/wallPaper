package com.stu.feisuo.walldemo;

import java.util.Random;

/**
 * Created by feisuo on 2015/5/26.
 */
public class BackGroundColorThread extends AbstractThread {

    private static final Random RANDOM = new Random();
    private static final int[] COLORS ={
            0xff59c0ce,
            0xffe684a9,
            0xfffef48b,
            0xff9dcb7a,
            0xffd65143,
    };

    private int backgroundRed=0x59;
    private int backgroundGreen=0xc0;
    private int backgroundBlue=0xce;
    private int lastColorIndex = 0;

    public BackGroundColorThread(int poll) {
        super(poll);
    }

    @Override
    protected void doStuff() {
        int rgb=getNextColor();
        backgroundRed = (0xFF0000 & rgb) >> 16;
        backgroundGreen = (0xFF00 & rgb) >> 8;
        backgroundBlue = (0xFF & rgb);
        pauseThread();

    }

    private int getNextColor() {
        int index;

        do {
            index = RANDOM.nextInt(COLORS.length);
        } while (index == lastColorIndex);
        lastColorIndex = index;
        return COLORS[index];
    }

    public int getBackgroundRed() {
        return backgroundRed;
    }

    public int getBackgroundGreen() {
        return backgroundGreen;
    }

    public int getBackgroundBlue() {
        return backgroundBlue;
    }

    public int getNextBackground(){
        if(lastColorIndex==COLORS.length-1||lastColorIndex==COLORS.length) {
            lastColorIndex = 0;
        }
        return COLORS[lastColorIndex+1];
    }
}
