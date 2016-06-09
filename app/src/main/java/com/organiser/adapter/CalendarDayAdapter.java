package com.organiser.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organiser.Constants;
import com.organiser.R;
import com.organiser.data.TimeConverter;
import com.organiser.model.CalendarItem;
import com.organiser.adapter.viewHolder.CalendarDayViewHolder;

import java.util.Calendar;
import java.util.List;


public class CalendarDayAdapter extends RecyclerView.Adapter<CalendarDayViewHolder> {

    private View mView;
    private String mMonthAndYear;
    private int mTodayNumber, mCurrentMonth, mSelectedMonth;
    private List<CalendarItem> mListCalendarDay;
    private IItemEventListener mEventListener;

    public interface IItemEventListener {
        void onDayItemClick(String date);
    }

    public void setEventClickListener(IItemEventListener listener) {
        mEventListener = listener;
    }

    public CalendarDayAdapter(List<CalendarItem> listCalendarDay, String monthAndYear, int todayNumber) {
        mMonthAndYear = monthAndYear;
        mListCalendarDay = listCalendarDay;
        mTodayNumber = todayNumber;
        mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mSelectedMonth = Integer.valueOf(TimeConverter.convertDate(monthAndYear, Constants.FORMAT_LLLL_yyyy, "MM"));
    }

    @Override
    public CalendarDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_calendar_day, parent, false);
        return new CalendarDayViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final CalendarDayViewHolder holder, final int position) {

        CalendarItem calendarItem = mListCalendarDay.get(position);
        // set value only current month
        if (calendarItem.getNumOfDay() > 0) {

            if (calendarItem.getMessage() != null)
                holder.messageButton.setImageResource(R.mipmap.ic_message);

            holder.calendarDay.setText(String.valueOf(calendarItem.getNumOfDay()));

            // set color background for current day
            if (calendarItem.getNumOfDay() == mTodayNumber) {
                if (mSelectedMonth == mCurrentMonth) {
                    holder.calendarDay.setBackgroundResource(R.drawable.rectangle_rounded_all_orange);
                    holder.calendarDay.setTextColor(mView.getResources().getColor(R.color.colorWhite));
                } else {
                    holder.calendarDay.setBackgroundResource(R.drawable.shape_border_orange);
                }
            }

            holder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedDate = String.valueOf(holder.calendarDay.getText()) + " " + mMonthAndYear;
                    mEventListener.onDayItemClick(selectedDate);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListCalendarDay.size();
    }
}
