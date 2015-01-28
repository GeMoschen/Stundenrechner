package de.gemo.stunden.units.undostates;

import de.gemo.stunden.utils.GUIUtils;

public class DayCreateState implements UndoState {

	private String dateString, startTime, endTime;

	public DayCreateState(String dateString, String startTime, String endTime) {
		this.dateString = dateString;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public void show() {
		GUIUtils.showDayView(this.dateString, this.startTime, this.endTime);
	}
}
