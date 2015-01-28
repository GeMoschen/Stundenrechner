package de.gemo.stunden.units.undostates;

import de.gemo.stunden.units.Day;
import de.gemo.stunden.utils.GUIUtils;

public class DayEditState implements UndoState {

    private Day day;

    public DayEditState(Day day) {
        this.day = day;
    }

    @Override
    public void show() {
        GUIUtils.showDayView(this.day);
    }
}
