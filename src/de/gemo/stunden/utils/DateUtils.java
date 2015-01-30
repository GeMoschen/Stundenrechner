package de.gemo.stunden.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.GERMANY);
    private static SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", Locale.GERMANY);
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.GERMANY);
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.GERMANY);
    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.GERMANY);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
    private static SimpleDateFormat advancedDateFormat = new SimpleDateFormat("EE, dd.MM.yyyy", Locale.GERMANY);
    private static SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.", Locale.GERMANY);

    public static String getCurrentTimeString() {
        return StringUtils.twoDigit(getCurrentHour()) + ":" + StringUtils.twoDigit(getCurrentMinute());
    }

    public static int getCurrentHour() {
        return Integer.valueOf(hourFormat.format(new Date()).toString());
    }

    public static int getCurrentMinute() {
        return Integer.valueOf(minuteFormat.format(new Date()).toString());
    }

    public static int getCurrentDay() {
        return Integer.valueOf(dayFormat.format(new Date()).toString());
    }

    public static int getCurrentMonth() {
        return Integer.valueOf(monthFormat.format(new Date()).toString());
    }

    public static int getCurrentYear() {
        return Integer.valueOf(yearFormat.format(new Date()).toString());
    }

    public static int getDay(Date date) {
        return Integer.valueOf(dayFormat.format(date).toString());
    }

    public static int getMonth(Date date) {
        return Integer.valueOf(monthFormat.format(date).toString());
    }

    public static int getYear(Date date) {
        return Integer.valueOf(yearFormat.format(date).toString());
    }

    public static int getHour(Date date) {
        return Integer.valueOf(hourFormat.format(date).toString());
    }

    public static int getMinute(Date date) {
        return Integer.valueOf(minuteFormat.format(date).toString());
    }

    public static String getCurrentDateString() {
        return advancedDateFormat.format(new Date()).toString();
    }

    public static long dayBeginning(int day, int month, int year) {
        try {
            return dateFormat.parse(day + "." + month + "." + year).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Date getDate(int day, int month, int year) {
        try {
            return dateFormat.parse(day + "." + month + "." + year);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date getDate(String text) {
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date getAdvancedDate(String text) {
        try {
            return advancedDateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String getDate(long timestamp) {
        return dateFormat.format(new Date(timestamp)).toString();
    }

    public static String getAdvancedDate(long timestamp) {
        return advancedDateFormat.format(new Date(timestamp)).toString();
    }

    public static String getDateString(int day, int month, int year) {
        return dateFormat.format(getDate(day, month, year)).toString();
    }

    public static String getAdvancedDateString(int day, int month, int year) {
        return advancedDateFormat.format(getDate(day, month, year)).toString();
    }

    public static String getShortDate(long timestamp) {
        return shortDateFormat.format(new Date(timestamp)).toString();
    }

}
