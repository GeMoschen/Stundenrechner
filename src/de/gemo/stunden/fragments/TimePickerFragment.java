package de.gemo.stunden.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;
import de.gemo.stunden.utils.StringUtils;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	private TextView textView;
	private int hour, minute;

	public TimePickerFragment(TextView textView) {
		this.textView = textView;
		this.hour = StringUtils.getHourFromString(textView.getText().toString());
		this.minute = StringUtils.getMinutesFromString(textView.getText().toString());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), this, this.hour, this.minute, true);
	}

	public void onTimeSet(TimePicker view, int hour, int minute) {
		textView.setText(StringUtils.toTime(hour, minute));
	}
}
