package com.organiser.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.organiser.Constants;
import com.organiser.receiver.AlarmReceiver;

public class AlarmScheduleManager {

    public static void setReminderAlarm(Context context, Long time) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        intent.putExtra(Constants.ONCE_ALARM, Boolean.TRUE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}