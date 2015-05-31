package com.stu.feisuo.walldemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class CPULoad {
	long boot;

	long total = 0;
	long idle = 0;

	float usage = 0;

	public CPULoad() {
		readUsage();
		boot = 0;
	}

	public float getUsage() {
		readUsage();
		return usage;
	}

	public float getSpeed() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/cpuinfo")), 1000);
			String load = reader.readLine();
			boolean found = false;
			while (!found) {
				if (load.contains("BogoMIPS")){
					found = true;
				} else {
					load = reader.readLine();
				}
			}
			reader.close();
			if (load ==null){
				load = "BogoMIPS\t: 0000";
			}
			String[] toks = load.split(":");

			return Float.parseFloat(toks[1].trim());

		} catch (Exception ex) {
			return 0.00f;
		} 
	}

	private void readUsage() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/stat")), 1000);
			String load = reader.readLine();
			reader.close();

			String[] toks = load.split(" ");

			long currTotal = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]);
			long currIdle = Long.parseLong(toks[5]);

			this.usage = (currTotal - total) * 100.0f
					/ (currTotal - total + currIdle - idle);
			this.total = currTotal;
			this.idle = currIdle;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public long getUptime() {
		try {
			if (boot == 0) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(
								"/proc/uptime")), 1000);
				String load = reader.readLine();
				reader.close();

				String[] toks = load.split(" ");
				boot = (new Date().getTime() / 1000)
						- Math.round(Float.parseFloat(toks[0]));
			}
			return (new Date().getTime() / 1000) - boot;
		} catch (IOException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}