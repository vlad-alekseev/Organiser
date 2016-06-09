package com.organiser.fragment;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.organiser.Constants;
import com.organiser.TimeConverter;
import com.organiser.adapter.CalendarDayAdapter;
import com.organiser.model.CalendarItems;
import com.organiser.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CalendarFragment extends Fragment {

    private View mView;
    private TextView mCurrentMonthAndYear;
    private ImageButton mPrevMonth, mNextMonth;
    private RecyclerView mRecyclerViewCalendarDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        setRetainInstance(true);

        initViews();
        setDefaultValue();
        setAdapterFromDate(getCalendarItems());

        return mView;
    }

    private void initViews() {
        mPrevMonth = (ImageButton) mView.findViewById(R.id.prevMonthButton);
        mNextMonth = (ImageButton) mView.findViewById(R.id.nextMonthButton);
        mCurrentMonthAndYear = (TextView) mView.findViewById(R.id.currentDate);
        mRecyclerViewCalendarDay = (RecyclerView) mView.findViewById(R.id.recyclerViewCalendar);
    }

    private void setDefaultValue() {
        String defaultYear = new SimpleDateFormat(Constants.FORMAT_LLLL_yyyy)
                .format(Calendar.getInstance().getTime());

        mCurrentMonthAndYear.setText(defaultYear);
        mNextMonth.setOnClickListener(onButtonNextMonthClick);
        mPrevMonth.setOnClickListener(onButtonPrevMonthClick);
    }

    private List<CalendarItems> getCalendarItems() {
        int month = Integer.valueOf(TimeConverter.convertDate(
                mCurrentMonthAndYear.getText().toString(), Constants.FORMAT_LLLL_yyyy, "MM"));
        int year = Integer.valueOf(TimeConverter.convertDate(
                mCurrentMonthAndYear.getText().toString(), Constants.FORMAT_LLLL_yyyy, "yyyy"));

        Calendar calendar = TimeConverter.getCalendarFromMonthAndYear(mCurrentMonthAndYear.getText().toString());

        List<CalendarItems> listCalendarDay = new ArrayList<>();
        CalendarItems oneDay = null;

        // Counts the number of days last month in the first week.
        // In order to the first day of the month corresponds to the correct day of the week
        int minusDays = (calendar.get(Calendar.DAY_OF_WEEK) - 2) * (-1);

        for (int i = minusDays; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            oneDay = new CalendarItems().getItemFromDate(year, month, i);
            if (oneDay == null) {
                oneDay = new CalendarItems();
                oneDay.setNumOfDay(i);
            }
            listCalendarDay.add(oneDay);
        }
        return listCalendarDay;
    }

    private void setAdapterFromDate(List<CalendarItems> listCalendarDay) {
        CalendarDayAdapter mDayAdapter = new CalendarDayAdapter(
                listCalendarDay, mCurrentMonthAndYear.getText().toString(),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        GridLayoutManager lLayout = new GridLayoutManager(getActivity(), 7);

        mRecyclerViewCalendarDay.setHasFixedSize(true);
        mRecyclerViewCalendarDay.setLayoutManager(lLayout);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerViewCalendarDay.setItemAnimator(itemAnimator);
        mRecyclerViewCalendarDay.setAdapter(mDayAdapter);
    }

    View.OnClickListener onButtonPrevMonthClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currDate = mCurrentMonthAndYear.getText().toString();
            mCurrentMonthAndYear.setText(TimeConverter.changeMonth(currDate, Constants.MONTH_MINUS));
            List<CalendarItems> listCalendarDay = getCalendarItems();
            setAdapterFromDate(listCalendarDay);
        }
    };

    View.OnClickListener onButtonNextMonthClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currDate = mCurrentMonthAndYear.getText().toString();
            mCurrentMonthAndYear.setText(TimeConverter.changeMonth(currDate, Constants.MONTH_PLUS));
            List<CalendarItems> listCalendarDay = getCalendarItems();
            setAdapterFromDate(listCalendarDay);
        }
    };
}
