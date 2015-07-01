package com.stu.feisuo.walldemo;

import java.util.Calendar;

/**
 * Created by feisuo on 2015/5/26.
 */
public class PoloarClockThread extends AbstractThread {

    private Calendar calendar;
    private int years;
    private int months;
    private int days;
    private int hours;
    private int minuts;
    private int seconds;
    private int miliSecond;
    private String[] weekStr={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    private String[] MonthStr={"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER","UNDECIMBER"};

    public PoloarClockThread(int poll) {
        super(poll);
    }

    @Override
    protected void doStuff() {
        calendar= Calendar.getInstance();
        months=calendar.get(Calendar.MONTH);
        days=calendar.get(Calendar.DAY_OF_WEEK);
        hours=calendar.get(Calendar.HOUR_OF_DAY);
        minuts=calendar.get(Calendar.MINUTE);
        seconds=calendar.get(Calendar.SECOND);
        miliSecond=calendar.get(Calendar.MILLISECOND);
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months+1;
    }
    public String getFormatMonths() {
        return MonthStr[months];
    }

    public int getDays() {
        return days;
    }
    public String getFormatDays() {
        days-=1;
        return weekStr[days];
    }

    public int getHours() {
        return hours;
    }

    public String getFormatHours() {
        return hours<10?"0"+hours:String.valueOf(hours);
    }

    public int getMinuts() {
        return minuts;
    }

    public String getFormatMinuts() {
        return minuts<10?"0"+minuts:String.valueOf(minuts);
    }
    public int getSeconds() {
        return seconds;
    }

    public String getFormatSeconds() {
        return seconds<10?"0"+seconds:String.valueOf(seconds);
    }

    public int getMiliSecond() {
        return miliSecond;
    }

}
