package com.organiser.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.organiser.data.AlarmScheduleManager;
import com.organiser.model.CalendarItem;

import java.util.Calendar;
import java.util.List;

public class StartReceiversAfterBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // set alarms
        long currentTimeInMillis = Calendar.getInstance().get(Calendar.MILLISECOND);
        List<CalendarItem> calendarItemList = new CalendarItem().getAllItems();
        for (int i = 0; i < calendarItemList.size(); i++){
            CalendarItem calendarItem = calendarItemList.get(i);
            if (currentTimeInMillis < calendarItem.getTimeInMillis()){
                AlarmScheduleManager.setReminderAlarm(context, calendarItem.getTimeInMillis());
            }
        }

    }
}
