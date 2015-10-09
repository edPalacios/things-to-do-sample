package com.ep.thingstodo.activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ep.thingstodo.R;
import com.ep.thingstodo.model.ToDoPOJO;
import com.ep.thingstodo.model.ToDoPOJO.Priority;
import com.ep.thingstodo.model.ToDoPOJO.Status;
import com.ep.thingstodo.utils.Utils;
import com.ep.thingstodo.dialogfragments.DatePickerFragment;
import com.ep.thingstodo.dialogfragments.TimePickerFragment;
import com.ep.thingstodo.listeners.OnUpdateDateViewListener;
import com.ep.thingstodo.listeners.OnUpdateTimeViewListener;

public class AddToDoActivity extends Activity implements OnUpdateDateViewListener, OnUpdateTimeViewListener {

	// 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
	private static final int SEVEN_DAYS = 604800000;

	private String timeString;
	private String dateString;
	public TextView dateView;
	private TextView timeView;

	private Date date;
	private RadioGroup radioGroupPriotity;
	private RadioGroup radioGroupStatus;
	private EditText titleInput;
	private RadioButton radioBtnStatus;
	private RadioButton radioBtnPriority;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_todo);
		initViews();
		setDefaultDateTime();
		initListeners();
	}

	private void initListeners() {
		final Button datePickerButton = (Button) findViewById(R.id.date_picker_button);
		datePickerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
		});

		final Button timePickerButton = (Button) findViewById(R.id.time_picker_button);
		timePickerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePickerDialog();
			}
		});

		final Button cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		final Button resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				titleInput.setText("");
				radioBtnStatus.setChecked(true);
				radioBtnPriority.setChecked(true);
				setDefaultDateTime();
			}
		});

		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Priority priority = getPriority();
				Status status = getStatus();
				String titleString = titleInput.getText().toString();
				String fullDate = dateString + " " + timeString;
				Intent data = new Intent();
				ToDoPOJO.packageIntent(data, titleString, priority, status, fullDate);
				setResult(RESULT_OK, data);
				finish ();
			}
		});
	}

	private void initViews() {
		titleInput = (EditText) findViewById(R.id.title);
		radioBtnStatus = (RadioButton) findViewById(R.id.statusPending);
		radioBtnPriority = (RadioButton) findViewById(R.id.medPriority);
		radioGroupPriotity = (RadioGroup) findViewById(R.id.priorityGroup);
		radioGroupStatus = (RadioGroup) findViewById(R.id.statusGroup);
		dateView = (TextView) findViewById(R.id.date);
		timeView = (TextView) findViewById(R.id.time);
	}

	private void setDefaultDateTime() {
		date = new Date();
		date = new Date(date.getTime() + SEVEN_DAYS);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Utils.setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
		dateView.setText(dateString);
		Utils.setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.MILLISECOND));
		timeView.setText(timeString);
	}


	private Priority getPriority() {
		switch (radioGroupPriotity.getCheckedRadioButtonId()) {
		case R.id.lowPriority: {
			return Priority.LOW;
		}
		case R.id.highPriority: {
			return Priority.HIGH;
		}
		default: {
			return Priority.MEDIUM;
		}
		}
	}

	private Status getStatus() {
		switch (radioGroupStatus.getCheckedRadioButtonId()) {
		case R.id.statusDone: {
			return Status.DONE;
		}
		default: {
			return Status.PENDING;
		}
		}
	}

	private String getToDoTitle() {
		return titleInput.getText().toString();
	}

	@Override
	public void updateTime(String time) {
		timeView.setText(time);
	}

	@Override
	public void updateDate(String date) {
		dateView.setText(date);
	}

	private void showDatePickerDialog() {
		DialogFragment dialogFragment = new DatePickerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("date", dateString);
		dialogFragment.setArguments(bundle);
		dialogFragment.show(getFragmentManager(), "datePicker");
	}

	private void showTimePickerDialog() {
		DialogFragment timePickerFragment = new TimePickerFragment();
		timePickerFragment.show(getFragmentManager(), "timePicker");
	}
}
