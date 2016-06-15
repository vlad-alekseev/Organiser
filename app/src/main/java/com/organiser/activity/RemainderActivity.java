package com.organiser.activity;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.organiser.Constants;
import com.organiser.R;
import com.organiser.data.AlarmScheduleManager;
import com.organiser.data.TimeConverter;
import com.organiser.model.CalendarItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class RemainderActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private TextView mViewDate;
    private CalendarItem mOneDay;
    private long mTimeInMillis;
    private EditText mTextMessage;
    private TextView mTimeReminder;
    private int mYear, mMonth, mDay;
    private SwitchCompat mSwitchAlarm;
    private Button mSaveButton, mDeleteButton;
    private Calendar mCalendar = Calendar.getInstance();
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat(Constants.TIME_FORMAT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        String currentDate = getIntent().getExtras().getString(Constants.KEY_CURRENT_DATE);
        initViews();
        setDefault(currentDate);
    }


    private void initViews() {
        mViewDate = (TextView) findViewById(R.id.reminderDate);
        mSpinner = (Spinner) findViewById(R.id.spinnerCurrency);
        mSaveButton = (Button) findViewById(R.id.saveReminderButton);
        mDeleteButton = (Button) findViewById(R.id.deleteCalendarItemButton);
        mTextMessage = (EditText) findViewById(R.id.textMessage);
        mTimeReminder = (TextView) findViewById(R.id.timeReminder);
        mSwitchAlarm = (SwitchCompat) findViewById(R.id.switch_compat);
    }

    private void setDefault(String currentDate) {
        mViewDate.setText(currentDate);
        mOneDay = getCalendarItem(currentDate);
        mTimeReminder.setText(mTimeFormat.format(mCalendar.getTime()));
        mTimeReminder.setOnClickListener(changeTime);
        mSaveButton.setOnClickListener(onButtonSaveReminderClick);

        // set default value, if entry exists
        if (mOneDay != null) {
            setDefaultValue(mOneDay);
            mDeleteButton.setVisibility(View.VISIBLE);
            mDeleteButton.setOnClickListener(onButtonDeleteClick);
            if (mOneDay.getIsSetAlarm()) {
                mSwitchAlarm.setChecked(true);
            }
        }

    }

    private void setDefaultValue(CalendarItem oneDay) {
        mTextMessage.setText(oneDay.getMessage());
        String[] array = getResources().getStringArray(R.array.reminderType);
        mSpinner.setSelection(Arrays.asList(array).indexOf(oneDay.getReminderType()));
        mTimeReminder.setText(oneDay.getTime());
    }

    private CalendarItem getCalendarItem(String currentDate) {
        mMonth = Integer.valueOf(
                TimeConverter.convertDate(currentDate, Constants.FORMAT_dd_LLLL_yyyy, "MM"));
        mYear = Integer.valueOf(
                TimeConverter.convertDate(currentDate, Constants.FORMAT_dd_LLLL_yyyy, "yyyy"));
        mDay = Integer.valueOf(
                TimeConverter.convertDate(currentDate, Constants.FORMAT_dd_LLLL_yyyy, "dd"));

        return new CalendarItem().getItemFromDate(mYear, mMonth, mDay);
    }

    View.OnClickListener onButtonDeleteClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mOneDay.delete();
            finishActivity();
        }
    };

    View.OnClickListener onButtonSaveReminderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mTextMessage.getText().toString().equals("")) {
                // removes old record for the creation of a new
                if (mOneDay != null) {
                    mOneDay.delete();
                }

                // Does not give the opportunity to set reminder in past tense
                mTimeInMillis = getTimeInMillis();
                if (mTimeInMillis > System.currentTimeMillis()) {
                    AlarmScheduleManager.setReminderAlarm(getApplicationContext(), mTimeInMillis);
                } else {
                    if (mSwitchAlarm.isChecked()) {
                        Toast.makeText(getApplicationContext(), R.string.alarm_clock_not_included,
                                Toast.LENGTH_LONG).show();

                        mSwitchAlarm.setChecked(false);
                        mSwitchAlarm.setClickable(false);
                    }
                }

                createNewCalendarItems();

                Toast.makeText(getApplicationContext(), R.string.reminder_saved, Toast.LENGTH_SHORT).show();
                finishActivity();
            } else {
                Toast.makeText(getApplicationContext(), R.string.message_empty, Toast.LENGTH_SHORT).show();
            }

        }
    };

    private long getTimeInMillis() {
        String date = mViewDate.getText().toString();

        SimpleDateFormat dtFormat = new SimpleDateFormat(Constants.FORMAT_dd_LLLL_yyyy + " " + Constants.TIME_FORMAT);
        long timeInMilliseconds = 0;
        try {
            Date checkedDate = dtFormat.parse(date + " " + mTimeReminder.getText().toString());
            timeInMilliseconds = checkedDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    View.OnClickListener changeTime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = mCalendar.get(Calendar.MINUTE);
            TimePickerDialog tpd = new TimePickerDialog(RemainderActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            String hourOfDayTxt = Integer.toString(hourOfDay);
                            String minuteTxt = Integer.toString(minute);

                            // if the number is less than 10, 0 is added to correct the date
                            if (hourOfDay < 10) hourOfDayTxt = "0" + hourOfDay;
                            if (minute < 10) minuteTxt = "0" + minute;

                            mTimeReminder.setText(hourOfDayTxt + ":" + minuteTxt);
                        }
                    }, hour, minute, true);
            tpd.show();
        }
    };

    private void createNewCalendarItems() {
        CalendarItem newCalendarItem = new CalendarItem();
        newCalendarItem.setMessage(mTextMessage.getText().toString());
        newCalendarItem.setNumOfYear(mYear);
        newCalendarItem.setNumOfMonth(mMonth);
        newCalendarItem.setNumOfDay(mDay);
        newCalendarItem.setReminderType(mSpinner.getSelectedItem().toString());
        newCalendarItem.setTime(mTimeReminder.getText().toString());
        newCalendarItem.setTimeInMillis(mTimeInMillis);
        if (mSwitchAlarm.isChecked()) {
            newCalendarItem.setIsSetAlarm(true);
        }
        newCalendarItem.save();
    }

    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra(Constants.FOR_RESULT_CHANGES, getString(R.string.update));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
