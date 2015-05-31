package com.stu.feisuo.walldemo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateCalc {
	private static long CONTACT = 2942996400l;
    private static Calendar calendar = Calendar.getInstance();
	public static double stardate(){
		
		Date StardateOriginToday = new Date("July 15, 1987 00:00:00");
		Date StardateInputToday = new Date();
		
		long stardateToday = StardateInputToday.getTime() - StardateOriginToday.getTime();
		double dStardateToday = stardateToday / (1000 * 60 * 60 * 24 * 0.036525);
		dStardateToday = Math.floor(dStardateToday + 410000);
		dStardateToday = dStardateToday / 10;
		
		return dStardateToday;

	}
	public static String toScientific(double num,int places) { 
		   double power=(int)(Math.log(num)/Math.log(10));
		   if(power < 0) power--;
		   double fraction=num/Math.pow(10,power);
		   String result="";     
		   String sign="";
		   fraction=roundToDecimals(fraction,places); 
		   if(power > 0) sign="+";
		   result+=fraction+"e"+sign+power;
		   return result;
		   }

	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return ((temp)/Math.pow(10,c));
		}
	
	public static int getDay(){
		return new Date().getDate() + 1;
//        return calendar.get(Calendar)
	}
	public static int getDecDay(){
		GregorianCalendar cal = new GregorianCalendar(getYear(), getMonth(), getDay());
		return Math.round(((float)(getDay() - 1) / (float)cal.getActualMaximum(Calendar.DAY_OF_MONTH)) * 10f);
	}
	
	public static int getYear(){
		return new Date().getYear() + 1900;
	}
	public static int getMonth(){
		return new Date().getMonth() + 1;
	}
	public static int getHours(){
		return new Date().getHours();
	}
    public static int getSecond(){
        return calendar.get(Calendar.SECOND);
    }
    public static int getMinute(){
        return calendar.get(Calendar.MINUTE);
    }
    public static int getHour(){
        return calendar.get(Calendar.HOUR);
    }
	public static double getDaysToFirstContact(){
		long seconds = CONTACT - ((new Date().getTime()) / 1000);
		return roundToDecimals(((double)seconds * 100 / 86400d) / 100, 1);
	}
}
