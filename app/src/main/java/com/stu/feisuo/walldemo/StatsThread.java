package com.stu.feisuo.walldemo;

public class StatsThread extends AbstractThread {

	private CPULoad cpu = new CPULoad();

	private String usage = "";
	private String upDays = "";
	private String upHours = "";
	private String upMins = "";
	private String upSecs = "";
	private String tHours = "";
	private String tDate = "";
	private String sTTC = "";
	private String sSpeed = "";


	public StatsThread(int poll) {
		super(poll);
		cpu = new CPULoad();

	}

	@Override
	protected void doStuff() {
		int iUsage = (Math.round(cpu.getUsage()));
		long uptime = (Math.round(cpu.getUptime()));
		int speed = (Math.round(cpu.getSpeed()));
		int days = (int) (uptime / 86400);
		int hours = (int) ((uptime % 86400) / 3600);
		int minutes = (int) (((uptime % 86400) % 3600) / 60);
		int seconds = (int) (((uptime % 86400) % 3600) % 60);
		usage = "000";
		if (iUsage < 10) {
			usage = "00" + String.valueOf(iUsage);
		} else if (iUsage < 100) {
			usage = "0" + String.valueOf(iUsage);
		} else {
			usage = "100";
		}
		upDays = (days < 10) ? "0" + String.valueOf(days) : String
				.valueOf(days);
		upHours = (hours < 10) ? "0" + String.valueOf(hours) : String
				.valueOf(hours);
		upMins = (minutes < 10) ? "0" + String.valueOf(minutes) : String
				.valueOf(minutes);
		upSecs = (seconds < 10) ? "0" + String.valueOf(seconds) : String
				.valueOf(seconds);
		String tMonth = (DateCalc.getMonth()) < 10 ? "0"
				+ String.valueOf(DateCalc.getMonth()) : String.valueOf(DateCalc
				.getMonth());
		tHours = (DateCalc.getHours() < 10) ? "0"
				+ String.valueOf(DateCalc.getHours()) : String.valueOf(DateCalc
				.getHours());
		tDate = String.valueOf(DateCalc.getYear()) + tMonth + "."
				+ String.valueOf(DateCalc.getDecDay());
		sTTC = String.valueOf(DateCalc.getDaysToFirstContact());

		sSpeed = "0000";
		if (speed < 10) {
			sSpeed = "000" + String.valueOf(speed);
		} else if (speed < 100) {
			sSpeed = "00" + String.valueOf(speed);
		} else if (speed < 1000) {
			sSpeed = "0" + String.valueOf(speed);
		} else {
			sSpeed = String.valueOf(Math.round(speed));
		}

	}

	public CPULoad getCpu() {
		return cpu;
	}

	public String getUsage() {
		return usage;
	}

	public String getUpDays() {
		return upDays;
	}

	public String getUpHours() {
		return upHours;
	}

	public String getUpMins() {
		return upMins;
	}

	public String getUpSecs() {
		return upSecs;
	}

	public String gettHours() {
		return tHours;
	}

	public String gettDate() {
		return tDate;
	}

	public String getsTTC() {
		return sTTC;
	}

	public String getsSpeed() {
		return sSpeed;
	}

}
