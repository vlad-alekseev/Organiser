package com.organiser.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.organiser.fragment.WeatherFragment;
import com.organiser.R;
import com.organiser.fragment.CalendarFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListenersOnButton();
        // set default fragment in container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.custom_fragment, new CalendarFragment()).commit();
    }


    private void setListenersOnButton() {
        Button calendarButton = (Button) findViewById(R.id.buttonCalendar);
        Button forecastButton = (Button) findViewById(R.id.buttonForecast);
        if (calendarButton != null) {
            calendarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.custom_fragment, new CalendarFragment()).commit();
                }
            });
        }

        if (forecastButton != null) {
            forecastButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.custom_fragment, new WeatherFragment()).commit();
                }
            });
        }

    }

}