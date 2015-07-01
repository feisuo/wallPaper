package com.stu.feisuo.walldemo;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by feisuo on 2015/5/22.
 */
public class IAMPaint {
    private Typeface font;
    private float scale;
    public static final float TEXT_XLANGE=32f;
    public static final float TEXT_LANGE=24f;
    public static final float TEXT_MEDIUM =16f;
    public static final float TEXT_SMALL=12f;
    private Paint  bitmapPaint= new Paint();
    private Paint  usagePaint= new Paint();
    private Paint  uptimePaint= new Paint();
    private Paint  processPaint= new Paint();
    private Paint  hotspotPaint= new Paint();
    private Paint secPaint = new Paint();
    private Paint minPaint = new Paint();
    private Paint hourPaint = new Paint();
    private Paint dayPaint = new Paint();
    private Paint monthPaint = new Paint();
    private Paint powerPaint= new Paint();
    private Paint powerTextPaint = new Paint();
    private Paint themePaint = new Paint();

    public IAMPaint(Typeface font, float scale) {

        this.font = font;
        this.scale = scale;
        initialize();
    }


    private void initialize(){

        bitmapPaint.setARGB(0xff,0xff,0xff,0xff);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setStrokeWidth(0);
        bitmapPaint.setStrokeCap(Paint.Cap.ROUND);
        bitmapPaint.setStyle(Paint.Style.STROKE);


        usagePaint.setTypeface(font);
        usagePaint.setTextSize(scale*TEXT_LANGE);
        usagePaint.setColor(0xff861cb8);
        usagePaint.setAntiAlias(true);
        usagePaint.setStrokeCap(Paint.Cap.ROUND);
        usagePaint.setStyle(Paint.Style.STROKE);

        hotspotPaint.setTypeface(font);
        hotspotPaint.setTextSize(scale*TEXT_SMALL);
        hotspotPaint.setColor(0xffe0ed10);
        hotspotPaint.setAntiAlias(true);
        hotspotPaint.setStrokeCap(Paint.Cap.ROUND);
        hotspotPaint.setStyle(Paint.Style.STROKE);

        powerTextPaint.setTypeface(font);
        powerTextPaint.setTextSize(scale*TEXT_MEDIUM);
        powerTextPaint.setColor(0xff861cb8);
        powerTextPaint.setAntiAlias(true);
        powerTextPaint.setStrokeCap(Paint.Cap.ROUND);
        powerTextPaint.setStyle(Paint.Style.STROKE);


        processPaint.setTypeface(font);
        processPaint.setTextSize(scale*TEXT_SMALL);
        processPaint.setColor(0xff9f9fff);
        processPaint.setAntiAlias(true);
        processPaint.setTextAlign(Paint.Align.RIGHT);


        secPaint.setColor(Color.GREEN);
        secPaint.setStrokeWidth(4);
        secPaint.setAntiAlias(true);
        secPaint.setStrokeCap(Paint.Cap.ROUND);
        secPaint.setStyle(Paint.Style.STROKE);

        minPaint.setColor(Color.RED);
        minPaint.setStrokeWidth(10);
        minPaint.setAntiAlias(true);
        minPaint.setStrokeCap(Paint.Cap.ROUND);
        minPaint.setStyle(Paint.Style.STROKE);

        hourPaint.setColor( Color.YELLOW);
        hourPaint.setStrokeWidth(14);
        hourPaint.setAntiAlias(true);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStyle(Paint.Style.STROKE);

        dayPaint.setColor(Color.MAGENTA);
        dayPaint.setStrokeWidth(12);
        dayPaint.setAntiAlias(true);
        dayPaint.setStrokeCap(Paint.Cap.ROUND);
        dayPaint.setStyle(Paint.Style.STROKE);


        monthPaint.setColor(0xff4d0b97);
        monthPaint.setStrokeWidth(18);
        monthPaint.setAntiAlias(true);
        monthPaint.setStrokeCap(Paint.Cap.ROUND);
        monthPaint.setStyle(Paint.Style.STROKE);


        powerPaint.setColor(0xffb4c7e7);
        powerPaint.setStrokeWidth(12);
        powerPaint.setAntiAlias(true);
        powerPaint.setStrokeCap(Paint.Cap.ROUND);
        powerPaint.setStyle(Paint.Style.STROKE);

        themePaint.setColor(0xfffef48b);
        themePaint.setAntiAlias(true);
//        themePaint.setStrokeWidth(18);
//        themePaint.setStrokeCap(Paint.Cap.ROUND);
        themePaint.setStyle(Paint.Style.FILL);

    }

    public Paint getBitmapPaint() {
        return bitmapPaint;
    }

    public Paint getUsagePaint() {
        return usagePaint;
    }

    public Paint getUptimePaint() {
        return uptimePaint;
    }



    public Paint getProcessPaint() {
        return processPaint;
    }

    public Paint getPowerPaint() {
        return powerPaint;
    }

    public Paint getHotspotPaint() {
        return hotspotPaint;
    }

    public Paint getSecPaint() {
        return secPaint;
    }

    public Paint getMinPaint() {
        return minPaint;
    }

    public Paint getHourPaint() {
        return hourPaint;
    }

    public Paint getDayPaint() {
        return dayPaint;
    }

    public Paint getMonthPaint() {
        return monthPaint;
    }

    public Paint getPowerTextPaint() {
        return powerTextPaint;
    }

    public Paint getThemePaint() {
        return themePaint;
    }
}
