package com.organiser.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.organiser.model.CalendarItems;

import java.util.Calendar;
import java.util.List;

public class StartReceiversAfterBoot extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        // set alarms
        long currentTimeInMillis = Calendar.getInstance().get(Calendar.MILLISECOND);
        List<CalendarItems> calendarItemsList = new CalendarItems().getAllItems();
        for (int i = 0; i < calendarItemsList.size(); i++){
            CalendarItems calendarItems = calendarItemsList.get(i);
            if (currentTimeInMillis < calendarItems.getTimeInMillis()){
                new AlarmReceiver().setReminderAlarm(context, calendarItems.getTimeInMillis());
            }
        }

    }
}
