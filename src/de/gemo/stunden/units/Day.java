package de.gemo.stunden.units;

import de.gemo.stunden.utils.StringUtils;

public class Day implements Comparable<Day> {

    private final int ID;
    private long date;
    private int startTime, endTime;
    private int pause;
    private int workedMinutes;

    private static int PAUSE_1 = 30;
    private static int PAUSE_1_AFTER = 6 * 60;
    private static int PAUSE_2 = 30;
    private static int PAUSE_2_AFTER = 10 * 60;

    public Day(int ID, long date) {
        this.ID = ID;
        this.date = date;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public boolean setStartTime(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return false;
        }
        this.startTime = (hour * 60) + minute;
        return true;
    }

    public boolean setEndTime(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return false;
        }
        this.endTime = (hour * 60) + minute;
        return true;
    }

    public void update() {
        int sTime = this.startTime;
        int eTime = this.endTime;

        if (eTime <= sTime) {
            eTime += (24 * 60);
        }

        this.workedMinutes = eTime - sTime;
        this.workedMinutes -= this.calculatePause();
    }

    public String getDateString() {
        return de.gemo.stunden.utils.DateUtils.getDate(this.date);
    }

    public String getPauseString() {
        return StringUtils.toTime(this.pause);
    }

    public String getWorkString() {
        return StringUtils.toTime(this.getWorkedHours(), this.getWorkMinutes());
    }

    public String getStartString() {
        return StringUtils.toTime((int) (this.startTime / 60), (this.startTime % 60));
    }

    public String getEndString() {
        return StringUtils.toTime((int) (this.endTime / 60), (this.endTime % 60));
    }

    private int getWorkedHours() {
        return (int) (this.workedMinutes / 60);
    }

    private int getWorkMinutes() {
        return (this.workedMinutes % 60);
    }

    public int getWorkedMinutes() {
        return workedMinutes;
    }

    private int calculatePause() {
        this.pause = 0;

        if (this.workedMinutes > PAUSE_1_AFTER) {
            this.pause += PAUSE_1;
        }

        if (this.workedMinutes > PAUSE_2_AFTER) {
            this.pause += PAUSE_2;
        }

        return this.pause;
    }

    public int getID() {
        return ID;
    }

    public long getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getPause() {
        return pause;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(Day other) {
        final int BEFORE = -1;
        final int AFTER = 1;

        if (this.date == other.date) {
            if (this.ID < other.ID) {
                return BEFORE;
            } else {
                return AFTER;
            }
        } else {
            if (this.date < other.date) {
                return BEFORE;
            } else {
                return AFTER;
            }
        }
    }
}
