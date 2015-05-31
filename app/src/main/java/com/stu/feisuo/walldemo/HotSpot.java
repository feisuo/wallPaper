package com.stu.feisuo.walldemo;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by feisuo on 2015/5/27.
 */
public class HotSpot {
    public RectF rect;
    public String name;

    public HotSpot(RectF rect, String name) {
        this.rect = rect;
        this.name = name;
    }
}
