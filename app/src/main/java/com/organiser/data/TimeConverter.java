package com.organiser.data;


import android.text.format.DateFormat;

import com.organiser.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConverter {

    public static String convertDate(String time, String oldFormat, String newFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
        String finalDate = timeFormat.format(myDate);
        return finalDate;
    }

    public static String changeMonth(String time, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_LLLL_yyyy);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        if (type.equals(Constants.MONTH_PLUS)) {
            cal.add(Calendar.MONTH, 1);
        } else if (type.equals(Constants.MONTH_MINUS)) {
            cal.add(Calendar.MONTH, -1);
        }

        DateFormat dateFormatter = new DateFormat();
        String res = String.valueOf(dateFormatter.format(Constants.FORMAT_LLLL_yyyy, cal.getTime()));
        return res;
    }

    public static Calendar getCalendarFromMonthAndYear(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_LLLL_yyyy);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        return calendar;
    }
}
