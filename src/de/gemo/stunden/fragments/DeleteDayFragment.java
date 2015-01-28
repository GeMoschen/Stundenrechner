package de.gemo.stunden.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import de.gemo.stunden.MainActivity;
import de.gemo.stunden.units.Day;
import de.gemo.stunden.utils.GUIUtils;

public class DeleteDayFragment extends DialogFragment {

	private final MainActivity activity;
	private final Day day;

	public DeleteDayFragment(MainActivity activity, Day day) {
		this.activity = activity;
		this.day = day;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity()).setTitle(day.getDateString() + " löschen?").setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// delete day
				activity.getDayHolder().removeDay(day);

				// save changes
				activity.getDayHolder().save();

				// update view
				activity.getDayHolder().selectMonth(day.getDateString());
				GUIUtils.showMonthView();
			}
		}).setNegativeButton("Nein", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		}).create();
	}
}