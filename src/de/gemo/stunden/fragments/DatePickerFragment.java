package de.gemo.stunden.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import de.gemo.stunden.utils.DateUtils;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView textView;
    private int day, month, year;

    public DatePickerFragment(TextView textView) {
        this.textView = textView;
        this.day = DateUtils.getDay(DateUtils.getAdvancedDate(textView.getText().toString()));
        this.month = DateUtils.getMonth(DateUtils.getAdvancedDate(textView.getText().toString()));
        this.year = DateUtils.getYear(DateUtils.getAdvancedDate(textView.getText().toString()));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, this.year, this.month - 1, this.day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        textView.setText(DateUtils.getAdvancedDateString(day, month + 1, year));
    }
}
