package com.organiser.activity;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.organiser.R;
import com.organiser.model.CalendarItem;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        CalendarItem todayItem = getTodayCalendarItem();
        fillViews(todayItem);
      }

    private CalendarItem getTodayCalendarItem() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new CalendarItem().getItemFromDate(year, month, day);
    }

    private void fillViews(CalendarItem todayItem){
        findViewById(R.id.buttonClose).setOnClickListener(closeAlarmClick);
        findViewById(R.id.stopAlarm).setOnClickListener(stopAlarmClick);
        ((TextView) findViewById(R.id.idMessage)).setText(todayItem.getMessage());
        ((TextView) findViewById(R.id.reminderTypeView)).setText(todayItem.getReminderType());

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.el_bimbo);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    View.OnClickListener stopAlarmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMediaPlayer.stop();
        }
    };

    View.OnClickListener closeAlarmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mMediaPlayer.stop();
            finish();
        }
    };

    @Override
    public void onAttachedToWindow() {
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

}
