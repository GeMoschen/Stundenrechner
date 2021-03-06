package de.gemo.stunden.units.undostates;

import de.gemo.stunden.units.Month;
import de.gemo.stunden.utils.GUICreator;

public class MonthState implements UndoState {

    private Month month;

    public MonthState(Month month) {
        this.month = month;
    }

    @Override
    public void show() {
        GUICreator.showMonthView(this.month);
    }
}
