package com.stu.feisuo.walldemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * @author cedarrapidsboy
 * Provider for battery information.
 *
 */
public class BatteryReceiver extends BroadcastReceiver {
	
	private static final String DEUTERIUM_STOPPED = "Deuterium refill stopped";
	private static final String DEUTERIUM_FULL = "Deuterium tanks full";
	private static final String DEUTERIUM_FLOW_NORMAL = "Deuterium flow normal";
	private static final String DEUTERIUM_REFILLING = "Refilling deuterium";
	private static final String DEUTERIUM_STATUS_UNKNOWN = "Deuterium status unknown";
	private int level = 0;
	private String status = DEUTERIUM_STATUS_UNKNOWN;
	private double eV = 0.0d;

	@Override
	public void onReceive(Context context, Intent intent) {
		level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		status = DEUTERIUM_STATUS_UNKNOWN;
		eV = 0.0d;
		int s = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		switch (s) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			status = DEUTERIUM_REFILLING;
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			status = DEUTERIUM_FLOW_NORMAL;
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			status = DEUTERIUM_FULL;
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			status = DEUTERIUM_STOPPED;
			break;
		case BatteryManager.BATTERY_STATUS_UNKNOWN:
			status = DEUTERIUM_STATUS_UNKNOWN;
			break;

		}
		int i = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
		eV = DateCalc.roundToDecimals(i / 1000d, 2);
	}
	
	/**
	 * @return battery percentage (0-100)
	 */
	public int getBatteryLevel(){
		return level;
	}
	
	/**
	 * @return battery voltage
	 */
	public double geteV(){
		return eV;
	}
	
	/**
	 * @return lcars-themed battery status message
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * @param threshold percentage (<=) that will trigger a true
	 * @return true if battery level is below or equal to threshold
	 */
	public boolean isBatteryLow(int threshold){
		if (level <= threshold){
			return true;
		}
		return false;
	}
}
