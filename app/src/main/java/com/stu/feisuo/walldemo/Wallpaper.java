package com.stu.feisuo.walldemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.Calendar;


/**
 * Created by feisuo on 2015/5/22.
 */
public class Wallpaper extends WallpaperService {


    private final Handler mHandler = new Handler();
    private final int ELECTRON_FR=50;
    private final int CAUTION_FR=100;
    private final int NORMAL_FR=1000;
    private final int MEMORY_FR=15000;
    private final int DEFAULT_BACKGROUND=0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new CubeEngine();
    }

    class CubeEngine extends Engine{


        private final RectF secCircle = new RectF();
        private final RectF minCircle = new RectF();
        private final RectF hourCircle = new RectF();
        private final RectF dayCircle = new RectF();
        private final RectF monthCircle = new RectF();
        private final RectF powerCircle = new RectF();
        private final RectF polarRect = new RectF();
        private IAMPaint IAMPaint;
        private float scale=getResources().getDisplayMetrics().density;
        float xScale;
        float yScale;
        private int wallWidth=0;
        private int wallHeight=0;
        private int mPixels;
        private float mTouchX;
        private float mTouchY;
        private Bitmap bitmapFramePortrait;
        private Bitmap bitmapMutablePortrait;
        private Bitmap bitmapFrameLandscape;
        private Bitmap bitmapPolar;
        private Bitmap bitmapShowInfo;

        private boolean isPortrait;
        private int framerate = NORMAL_FR;

        private Rect framePortraitRect;
        private Rect frameLandscapeRect;
        private Rect wallpaperRect = new Rect();
        private Resources res =getResources();


        private StatsThread statsThread;
        private PoloarClockThread poloarClockThread;



        double secEndAngle;
        double minEndAngle;
        double hourEndAngle;
        double dayEndAngle;
        double monthEndAngle;
        //polar clock show area
        int displayWidth = 308;
        int displayHeight = 440;
        int displayWidthMD = 232;
        int displayHeightMD = 300;
        // These values determine the sizes of the circles
        int secRadius = 88;
        int minRadius = 68;
        int hourRadius = 48;
        int dayRadius = 48;
        int monthRadius = 20;

        private final Runnable drawingThread = new Runnable() {
            public void run() {
                drawFrame();
            }
        };

        private HotSpot hotspot;
        private BatteryReceiver batteryReceiver =  new BatteryReceiver();
        private boolean mVisible;

        CubeEngine() {

            registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            bitmapFramePortrait=BitmapFactory.decodeResource(res,R.drawable.frame);
            bitmapMutablePortrait=Bitmap.createBitmap(bitmapFramePortrait.getWidth(),bitmapFramePortrait.getHeight(),bitmapFramePortrait.getConfig());
            bitmapPolar=BitmapFactory.decodeResource(res,R.drawable.polar);
            bitmapShowInfo=BitmapFactory.decodeResource(res,R.drawable.show_info);
            bitmapFrameLandscape=BitmapFactory.decodeResource(res,R.drawable.frame);

            frameLandscapeRect= new Rect(204,0,bitmapFrameLandscape.getWidth(),bitmapFrameLandscape.getHeight());
            framePortraitRect = new Rect(0,0,bitmapFramePortrait.getWidth(),bitmapFramePortrait.getHeight());
            IAMPaint = new IAMPaint(Typeface.createFromAsset(getAssets(),"swiss_ec.ttf"),scale);


            secCircle.set(displayWidth - secRadius, displayHeight - secRadius, displayWidth + secRadius, displayHeight + secRadius);
            minCircle.set(displayWidth - minRadius, displayHeight - minRadius, displayWidth + minRadius, displayHeight + minRadius);
            hourCircle.set(displayWidth - hourRadius, displayHeight - hourRadius, displayWidth + hourRadius, displayHeight + hourRadius);
            dayCircle.set(displayWidthMD- dayRadius, displayHeightMD - dayRadius, displayWidthMD + dayRadius, displayHeightMD + dayRadius);
            monthCircle.set(displayWidthMD- monthRadius, displayHeightMD - monthRadius, displayWidthMD + monthRadius, displayHeightMD + monthRadius);
            powerCircle.set(520,146,604,230);
            polarRect.set(224,309,410,662);
            statsThread = new StatsThread(NORMAL_FR);
            poloarClockThread= new PoloarClockThread(ELECTRON_FR);


        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
//            setTouchEventsEnabled(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(drawingThread);
            statsThread.stopThread();
            poloarClockThread.stopThread();
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            if(width>height){
                isPortrait=false;
            }else{
                isPortrait=true;
            }
            wallWidth =width*2;
            wallHeight=height;
            setDimensions();
            drawFrame();

        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            setDimensions();
            statsThread.start();
            poloarClockThread.start();

        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible=false;
            statsThread.stopThread();
            poloarClockThread.stopThread();
            mHandler.removeCallbacks(drawingThread);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible=visible;
            if(visible){
                statsThread.resumeThread();
                poloarClockThread.resumeThread();
                drawFrame();
            }else{
                mHandler.removeCallbacks(drawingThread);
                statsThread.pauseThread();
                poloarClockThread.pauseThread();
            }
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixels, int yPixels) {
            mPixels =(int)-((wallWidth/2)*xOffset);
            setDimensions();
            drawFrame();
        }


        @Override
        public void onTouchEvent(MotionEvent event) {

            if(event.getAction()==MotionEvent.ACTION_DOWN){
                mTouchX=event.getX();
                mTouchY=event.getY();
//                Log.i("outer ","mTouchX :"+ mTouchX +"mTouchY : "+mTouchY);
                hotspot=null;
                if(dayCircle.contains(mTouchX/xScale,mTouchY/yScale)){
                    hotspot = new HotSpot(monthCircle,"mon");
                }else if(secCircle.contains(mTouchX/xScale,mTouchY/yScale)){
                    hotspot = new HotSpot(secCircle,"sec");
                }
            }else{
                mTouchX=-1;
                mTouchY=-1;
            }
            super.onTouchEvent(event);
        }

        private void setDimensions(){
            wallpaperRect.set(mPixels,0,mPixels+wallWidth,wallHeight);
            xScale =(float)wallWidth/(isPortrait?bitmapFramePortrait.getWidth():bitmapFrameLandscape.getWidth());
            yScale=(float)wallHeight/(isPortrait?bitmapFramePortrait.getHeight():bitmapFrameLandscape.getHeight());

        }

        void drawFrame(){
            Canvas c;
            if(isPortrait){
                c = new Canvas(bitmapMutablePortrait);
                c.drawBitmap(bitmapFramePortrait,0,0, IAMPaint.getBitmapPaint());
                drawPolarClock(c);
                drawSystemInfo(c);
                drawText(c);
                drawPower(c);
            }
            SurfaceHolder holder = getSurfaceHolder();
            c=holder.lockCanvas();
            if(c!=null) {
                drawBitmap(c);
            }
            holder.unlockCanvasAndPost(c);
            mHandler.removeCallbacks(drawingThread);
            if (mVisible) {
//                if (batteryReceiver.isBatteryLow(20)) {
//                    framerate = CAUTION_FR;
//                } else if (systemPanelMode == ELECTRON_MODE) {
//                    framerate = ELECTRON_FR;
//                } else {
//                    framerate = NORMAL_FR;
//                }
                mHandler.postDelayed(drawingThread, framerate);
            }
        }

        void drawPolarClock(Canvas c){
            if(isPortrait) {

                c.drawBitmap(bitmapPolar, 204, 340, IAMPaint.getBitmapPaint());
                secEndAngle = poloarClockThread.getSeconds() * 6 + poloarClockThread.getMiliSecond() * .006;
                c.drawArc(secCircle, -90, (float) secEndAngle, false, IAMPaint.getSecPaint());
                // builds minute circle
                minEndAngle = poloarClockThread.getMinuts() * 6;
                if (minEndAngle == 0) {
                    minEndAngle = 3;
                }
                c.drawArc(minCircle, -90, (float) minEndAngle, false, IAMPaint.getMinPaint());

                // builds hour circle
                hourEndAngle = poloarClockThread.getHours() * 30;
                if (hourEndAngle == 0) {
                    hourEndAngle = 3;
                }
                c.drawArc(hourCircle, -90, (float) hourEndAngle, false, IAMPaint.getHourPaint());

                // builds day circle
                dayEndAngle = (poloarClockThread.getDays() - 1) * (360 / 7);
                if (dayEndAngle == 0){
                      dayEndAngle = 3;
                }
                c.drawArc(dayCircle, -90, (float) dayEndAngle, false, IAMPaint.getDayPaint());

                // builds month circle
                monthEndAngle = (poloarClockThread.getMonths() - 1) * 30;
                if (monthEndAngle == 0){
                     monthEndAngle = 3;
                }
                c.drawArc(monthCircle, -90, (float) monthEndAngle, false, IAMPaint.getMonthPaint());

                if(hotspot!=null){
                    if("sec".equals(hotspot.name)){
                        c.drawText(poloarClockThread.getFormatHours()+":"+poloarClockThread.getFormatMinuts()+":"+poloarClockThread.getFormatSeconds(),100,200 + IAMPaint.TEXT_MEDIUM,IAMPaint.getUsagePaint());
                    }else{
                        c.drawText(poloarClockThread.getFormatDays()+" "+poloarClockThread.getFormatMonths(),100,200 + IAMPaint.TEXT_MEDIUM,IAMPaint.getUsagePaint());
                    }
                    drawLabel(
                            new Point(90,(int)(200+IAMPaint.TEXT_MEDIUM)),
                            new Point((int)(hotspot.rect.centerX()), (int)(hotspot.rect
                                    .centerY())), c);
                }
            }
        }
        void drawSystemInfo(Canvas c){
            c.drawBitmap(bitmapShowInfo,354,202, IAMPaint.getBitmapPaint());
            c.drawBitmap(bitmapShowInfo,480,368, IAMPaint.getBitmapPaint());
        }

        void drawLabel(Point start, Point end, Canvas c) {
            Path linePath = new Path();
            Paint linePaint = new Paint(IAMPaint.getHotspotPaint());
            // background shadow
            linePaint.setStyle(Paint.Style.STROKE);
            linePath.moveTo(start.x, start.y);
            linePath.lineTo(start.x, end.y - 2);
            linePath.cubicTo(start.x, end.y - 1, start.x + 1, end.y,
                    start.x + 2, end.y);
            linePath.lineTo(end.x, end.y);
            linePaint.setColor(0x4B000000); // fade
            linePaint.setStrokeWidth(4);
            c.drawPath(linePath, linePaint);
            linePaint.setStrokeWidth(0);
            linePaint.setStyle(Paint.Style.FILL);
            c.drawCircle(start.x, start.y, 6, linePaint);
            c.drawCircle(end.x, end.y, 6, linePaint);

            // foreground line
            linePaint.setStyle(Paint.Style.STROKE);
            linePath.moveTo(start.x, start.y);
            linePath.lineTo(start.x, end.y - 2);
            linePath.cubicTo(start.x, end.y - 1, start.x + 1, end.y,
                    start.x + 2, end.y);
            linePath.lineTo(end.x, end.y);
            linePaint.setColor(0xff9f9fff); // blueish
            linePaint.setStrokeWidth(2);
            c.drawPath(linePath, linePaint);
            linePaint.setStrokeWidth(0);
            linePaint.setStyle(Paint.Style.FILL);
            c.drawCircle(start.x, start.y, 2, linePaint);
            c.drawCircle(end.x, end.y, 2, linePaint);
        }

        void drawText(Canvas c) {
            int[] columns = { 520, 560, 600 };
            int[] rows = { 440, 460, 480, 500, 520, 540 };
            try {
                c.drawText("CPU "+statsThread.getUsage()+"%", 400, 310,
                        IAMPaint.getUsagePaint());
                // c.drawText(dur + " " + sDays + " " + sHours + ":" + sMins +
                // ":" +
                // sSecs, mPixels + 222, 69, ulPaint);
                c.drawText("DUR ", columns[0], rows[0],
                        IAMPaint.getHotspotPaint());
                c.drawText(statsThread.getUpDays(), columns[1], rows[0],
                        IAMPaint.getHotspotPaint());
                c.drawText(
                        statsThread.getUpHours() + ":"
                                + statsThread.getUpMins() + ":"
                                + statsThread.getUpSecs(), columns[2], rows[0],
                        IAMPaint.getHotspotPaint());
                c.drawText("SD", columns[0], rows[1],
                        IAMPaint.getHotspotPaint());
                c.drawText("--", columns[1], rows[1],
                        IAMPaint.getHotspotPaint());
                c.drawText(String.valueOf(DateCalc.stardate()), columns[2],
                        rows[1], IAMPaint.getHotspotPaint());
                c.drawText("TER ", columns[0], rows[2],
                        IAMPaint.getHotspotPaint());
                c.drawText(statsThread.gettHours(), columns[1], rows[2],
                        IAMPaint.getHotspotPaint());
                c.drawText(statsThread.gettDate(), columns[2], rows[2],
                        IAMPaint.getHotspotPaint());
                c.drawText("TTC ", columns[0], rows[3],
                        IAMPaint.getHotspotPaint());
                c.drawText("--", columns[1], rows[3],
                        IAMPaint.getHotspotPaint());
                c.drawText(statsThread.getsTTC(), columns[2], rows[3],
                        IAMPaint.getHotspotPaint());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        void drawPower(Canvas c){
          double  powEndAngle = (100-batteryReceiver.getBatteryLevel())*360/100;
            if (powEndAngle == 0) {
                powEndAngle = 1;
            }
            c.drawArc(powerCircle, 360, (float) powEndAngle, false, IAMPaint.getPowerPaint());
            c.drawText("Power: "+batteryReceiver.getBatteryLevel()+"%",powerCircle.centerX()+30,powerCircle.centerY()+6,IAMPaint.getPowerPaint());
        }
        void drawBitmap(Canvas c){
            if(!isPortrait){
                c.drawBitmap(bitmapFrameLandscape,frameLandscapeRect,wallpaperRect, IAMPaint.getBitmapPaint());
            }else{
                c.drawBitmap(bitmapMutablePortrait,framePortraitRect,wallpaperRect, IAMPaint.getBitmapPaint());
                IAMPaint.getHotspotPaint().setStyle(Paint.Style.STROKE);
            }
        }


    }
}
