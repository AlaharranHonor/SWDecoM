package com.alaharranhonor.swdm.old.util;

public class TimeUtil {

	public static int getRealLifeMinutes(int daytimeInTicks) {
		return (int) Math.floor(((daytimeInTicks % 1000) / 1000f) * 60);
	}

	public static int getRealLifeHours(int daytimeInTicks) {
		return (int) Math.floor(((daytimeInTicks + 6000) / 12000f) * 12);
	}

	public static float getMinuteHandTurnDegrees(int daytimeInTicks) {
		return (daytimeInTicks % 1000) / 1000f * 360;
	}

	public static float getHourHandTurnDegrees(int daytimeInTicks) {
		return ((daytimeInTicks + 6000) / 12000f) * 360;
	}

	public static String getRealLifeMessage(int daytimeInTicks) {
		int hours = TimeUtil.getRealLifeHours(daytimeInTicks);
		String time = hours > 11 && hours < 24 ? "PM" : "AM";
		if (hours == 24) {
			hours = 12;
		} else if (hours > 13) {
			hours -= 12;
		}
		return "The time is: " + hours + ":" + TimeUtil.getRealLifeMinutes(daytimeInTicks) + " " + time;
	}
}
