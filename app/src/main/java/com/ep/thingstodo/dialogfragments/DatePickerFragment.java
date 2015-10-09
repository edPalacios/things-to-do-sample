package com.ep.thingstodo.dialogfragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.ep.thingstodo.utils.Utils;
import com.ep.thingstodo.listeners.OnUpdateDateViewListener;

import java.util.Calendar;

/**
 * Created by Eduardo on 09/10/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    private String date;
    private OnUpdateDateViewListener viewListener;
    public DatePickerFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        viewListener = (OnUpdateDateViewListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        date = getArguments().getString("date");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        date = Utils.setDateString(year, monthOfYear, dayOfMonth);
        viewListener.updateDate(date);
    }
}
