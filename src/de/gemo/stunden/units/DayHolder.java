package de.gemo.stunden.units;

import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;
import de.gemo.stunden.utils.DateUtils;
import de.gemo.stunden.utils.GUICreator;

public class DayHolder {

    private static int ID_COUNTER = 0;
    public static int START_DAY = 16;
    private static String FILENAME = "workData.json";

    private Activity activity;
    private List<Day> data;
    private Month activeMonth;

    public DayHolder(Activity activity) {
        this.activity = activity;
        this.data = new ArrayList<Day>();
        this.activeMonth = this.getMonth(new Date());
        this.load();
    }

    private boolean load() {
        try {
            // clear data and open stream
            this.data.clear();
            JsonReader reader = new JsonReader(new InputStreamReader(this.activity.openFileInput(FILENAME)));

            // begin array
            reader.beginArray();

            // iterate...
            while (reader.hasNext()) {
                // read data from JSON
                reader.beginObject();

                String nextName = reader.nextName();

                // handle designs
                if (nextName.equalsIgnoreCase("nextID")) {
                    // handle ID
                    ID_COUNTER = reader.nextInt();
                } else if (nextName.equalsIgnoreCase("Design")) {
                    // handle design
                    String designName = reader.nextString();
                    GUICreator.setDesign(designName);
                } else {
                    int ID = reader.nextInt();

                    reader.nextName();
                    long date = reader.nextLong();

                    reader.nextName();
                    int startTime = reader.nextInt();

                    reader.nextName();
                    int endTime = reader.nextInt();

                    // create Day
                    Day day = new Day(ID, date);
                    day.setStartTime(startTime);
                    day.setEndTime(endTime);
                    day.update();
                    this.data.add(day);
                }
                reader.endObject();
            }

            // close stream
            reader.endArray();
            reader.close();

            // refresh values
            this.activeMonth = this.getMonth(new Date());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save() {
        try {
            // DELETE OLD FILE
            File file = new File(FILENAME);
            if (file.exists()) {
                file.delete();
            }

            // open stream
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(this.activity.openFileOutput(FILENAME, Context.MODE_PRIVATE)));
            writer.setIndent("  ");

            // begin array
            writer.beginArray();

            // write id
            writer.beginObject();
            writer.name("nextID").value(ID_COUNTER);
            writer.endObject();

            // // write design
            writer.beginObject();
            writer.name("Design").value(GUICreator.DESIGN.getName());
            writer.endObject();

            // iterate...
            for (Day day : this.data) {
                writer.beginObject();
                writer.name("ID").value(day.getID());
                writer.name("Date").value(day.getDate());
                writer.name("StartTime").value(day.getStartTime());
                writer.name("EndTime").value(day.getEndTime());
                writer.endObject();
            }
            writer.endArray();

            // close
            writer.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectMonth(String date) {
        this.activeMonth = this.getMonth(DateUtils.getAdvancedDate(date));
    }

    private Month getMonth(Date date) {
        int month = DateUtils.getMonth(date);
        int year = DateUtils.getYear(date);
        if (DateUtils.getDay(date) < START_DAY) {
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
        }
        return this.getMonth(month, year);
    }

    public void refreshMonth() {
        this.activeMonth = this.activeMonth.refreshMonth(this);
    }

    public void nextMonth() {
        this.activeMonth = this.activeMonth.nextMonth(this);
    }

    public void previousMonth() {
        this.activeMonth = this.activeMonth.previousMonth(this);
    }

    private Month getMonth(int month, int year) {
        return new Month(this, START_DAY, month, year);
    }

    public Month getActiveMonth() {
        return activeMonth;
    }

    public void addDayToList(Day day) {
        this.data.add(day);
        this.refreshMonth();
    }

    public void removeDay(Day day) {
        for (int index = 0; index < this.data.size(); index++) {
            if (day.getID() == this.data.get(index).getID()) {
                this.data.remove(index);
                this.refreshMonth();
                return;
            }
        }
    }

    public int getWorkedMinutes() {
        int minutes = 0;
        for (Day day : this.data) {
            minutes += day.getWorkedMinutes();
        }
        return minutes;
    }

    public int getPauseMinutes() {
        int pause = 0;
        for (Day day : this.data) {
            pause += day.getPause();
        }
        return pause;
    }

    public List<Day> getDaysBetween(long startTime, long endTime) {
        List<Day> list = new ArrayList<Day>();
        for (Day day : this.data) {
            if (day.getDate() >= startTime && day.getDate() <= endTime) {
                list.add(day);
            }
        }
        Collections.sort(list);
        return list;
    }

    public static int getIDAndIncrement() {
        ID_COUNTER++;
        return ID_COUNTER - 1;
    }
}
