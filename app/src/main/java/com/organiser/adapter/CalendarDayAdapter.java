package com.organiser.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organiser.Constants;
import com.organiser.R;
import com.organiser.fragment.RemainderFragment;
import com.organiser.model.CalendarItems;
import com.organiser.adapter.viewHolder.CalendarDayViewHolder;

import java.util.List;


public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayViewHolder> {

    private View mView;
    private String mMonthAndYear;
    private int mTodayNumber;
    private List<CalendarItems> mListCalendarDay;


    public CalendarDayAdapter(List<CalendarItems> listCalendarDay, String monthAndYear, int todayNumber) {
        mMonthAndYear = monthAndYear;
        mListCalendarDay = listCalendarDay;
        mTodayNumber = todayNumber;
    }

    @Override
    public CalendarDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_calendar_day, parent, false);
        return new CalendarDayViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final CalendarDayViewHolder holder, final int position) {

        CalendarItems calendarItems = mListCalendarDay.get(position);
        // set value only current month
        if (calendarItems.getNumOfDay() > 0) {

            if (calendarItems.getMessage() != null)
                holder.messageButton.setImageResource(R.mipmap.ic_message);

            holder.calendarDay.setText(String.valueOf(calendarItems.getNumOfDay()));

            // set color background for current day
            if (calendarItems.getNumOfDay() == mTodayNumber) {
                holder.calendarDay.setBackgroundColor(mView.getResources().getColor(R.color.materialDesignOrange));
                holder.calendarDay.setTextColor(mView.getResources().getColor(R.color.colorWhite));
            }

            holder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String changeDate = String.valueOf(holder.calendarDay.getText()) + " " + mMonthAndYear;
                    Bundle bundles = new Bundle();
                    bundles.putString(Constants.KEY_CURRENT_DATE, changeDate);

                    RemainderFragment fragment = new RemainderFragment();
                    fragment.setArguments(bundles);

                    ((FragmentActivity) mView.getContext())
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.custom_fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListCalendarDay.size();
    }
}
