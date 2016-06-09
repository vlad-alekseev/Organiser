package com.organiser.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.organiser.activity.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private String ONCE_ALARM = "onceAlarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.getBoolean(ONCE_ALARM, Boolean.TRUE)) {
            Intent intentAlarm = new Intent(context, AlarmActivity.class);
            intentAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intentAlarm);
        }
    }

    public void setReminderAlarm(Context context, Long time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        intent.putExtra(ONCE_ALARM, Boolean.TRUE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

}
