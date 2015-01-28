package de.gemo.stunden.units;

import java.util.Collections;
import java.util.List;

import de.gemo.stunden.utils.DateUtils;
import de.gemo.stunden.utils.StringUtils;

public class Month {
    private List<Day> days;
    private final long startTime, endTime;
    private final int month, year;

    public Month(DayHolder dayHolder, int day, int month, int year) {
        this.month = month;
        this.year = year;
        this.startTime = DateUtils.dayBeginning(day, month, year);
        int nextMonth = month + 1;
        int nextYear = year;
        if (nextMonth > 12) {
            nextMonth = 1;
            nextYear++;
        }
        this.endTime = DateUtils.dayBeginning(day - 1, nextMonth, nextYear);
        this.days = dayHolder.getDaysBetween(this.startTime, this.endTime);
    }

    public String getWorkedMinutesAsString() {
        int minutes = 0;
        for (Day day : this.days) {
            minutes += day.getWorkedMinutes();
        }
        return StringUtils.toTime(minutes);
    }

    public String getPauseMinutesAsString() {
        int pause = 0;
        for (Day day : this.days) {
            pause += day.getPause();
        }
        return StringUtils.toTime(pause);
    }

    public String getStartDate() {
        return DateUtils.getShortDate(this.startTime);
    }

    public String getEndDate() {
        return DateUtils.getShortDate(this.endTime);
    }

    public List<Day> getDays() {
        return Collections.unmodifiableList(days);
    }

    public void addDay(Day day) {
        this.days.add(day);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Month refreshMonth(DayHolder dayHolder) {
        this.days = dayHolder.getDaysBetween(this.startTime, this.endTime);
        return this;
    }

    public Month nextMonth(DayHolder dayHolder) {
        int nextMonth = this.month + 1;
        int nextYear = this.year;
        if (nextMonth > 12) {
            nextMonth = 1;
            nextYear++;
        }
        return new Month(dayHolder, DayHolder.START_DAY, nextMonth, nextYear);
    }

    public Month previousMonth(DayHolder dayHolder) {
        int prevMonth = this.month - 1;
        int prevYear = this.year;
        if (prevMonth < 1) {
            prevMonth = 12;
            prevYear--;
        }
        return new Month(dayHolder, DayHolder.START_DAY, prevMonth, prevYear);
    }
}
