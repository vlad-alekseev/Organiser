package com.organiser.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.organiser.Constants;
import com.organiser.activity.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.getBoolean(Constants.ONCE_ALARM, Boolean.TRUE)) {
            Intent intentAlarm = new Intent(context, AlarmActivity.class);
            intentAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intentAlarm);
        }
    }


}
