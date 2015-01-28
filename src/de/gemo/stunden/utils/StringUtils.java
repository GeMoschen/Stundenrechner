package de.gemo.stunden.utils;

public class StringUtils {

	public static String twoDigit(int value) {
		if (value < 10) {
			return "0" + value;
		}
		return String.valueOf(value);
	}

	public static String toTime(int hour, int minute) {
		return twoDigit(hour) + ":" + twoDigit(minute);
	}

	public static String toTime(int minutes) {
		return StringUtils.toTime((int) (minutes / 60), (minutes % 60));
	}

	public static int getHourFromString(String text) {
		return getIntFromString(text, 0, ":");
	}

	public static int getMinutesFromString(String text) {
		return getIntFromString(text, 1, ":");
	}

	public static int getIntFromString(String text, int index, String delimiter) {
		try {
			String[] split = text.split(delimiter);
			return Integer.valueOf(split[index]);
		} catch (Exception e) {
			return 0;
		}
	}
}
