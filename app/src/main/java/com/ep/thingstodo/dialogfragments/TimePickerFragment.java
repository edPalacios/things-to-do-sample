package com.ep.thingstodo.dialogfragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.ep.thingstodo.utils.Utils;
import com.ep.thingstodo.listeners.OnUpdateTimeViewListener;

import java.util.Calendar;

/**
 * Created by Eduardo on 09/10/2015.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnUpdateTimeViewListener timeViewListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        timeViewListener = (OnUpdateTimeViewListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        timeViewListener = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = Utils.setTimeString(hourOfDay, minute, 0);
        timeViewListener.updateTime(time);
    }
}
