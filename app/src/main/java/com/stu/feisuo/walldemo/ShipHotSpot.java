package com.stu.feisuo.walldemo;

import android.graphics.Rect;

/**
 * Created by feisuo on 2015/5/23.
 */
public class ShipHotSpot {
    Rect rect = new Rect();
    String name="";

    public ShipHotSpot(Rect rect, String name) {
        this.rect = rect;
        this.name = name;
    }
}
