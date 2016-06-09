package com.organiser.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "CalendarItem")
public class CalendarItem extends Model {

    @Column(name = "message")
    private String message;

    @Column(name = "numOfDay")
    private int numOfDay;

    @Column(name = "numOfYear")
    private int numOfYear;

    @Column(name = "numOfMonth")
    private int numOfMonth;

    @Column(name = "time")
    private String time;

    @Column(name = "reminderType")
    private String reminderType;

    @Column(name = "isSetAlarm")
    private boolean isSetAlarm;

    @Column(name = "timeInMillis")
    private long timeInMillis;

    public long getTimeInMillis(){
        return this.timeInMillis;
    }

    public void setTimeInMillis(long millis){
        this.timeInMillis = millis;
    }

    public boolean getIsSetAlarm(){
        return this.isSetAlarm;
    }

    public void setIsSetAlarm(boolean isSetAlarm){
        this.isSetAlarm = isSetAlarm;
    }

    public int getNumOfDay(){
        return this.numOfDay;
    }

    public void setNumOfDay(int numOfDay){
        this.numOfDay = numOfDay;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public int getNumOfYear(){
        return this.numOfYear;
    }

    public void setNumOfYear(int year){
        this.numOfYear = year;
    }

    public int getNumOfMonth(){
        return this.numOfMonth;
    }

    public void setNumOfMonth(int month){
        this.numOfMonth = month;
    }

    public String getTime(){
        return this.time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getReminderType(){
        return this.reminderType;
    }

    public void setReminderType(String reminderType){
        this.reminderType = reminderType;
    }

    public CalendarItem(){

    }

    public CalendarItem(int numOfDay, String message){
        this.message = message;
        this.numOfDay = numOfDay;
    }

    public CalendarItem getItemFromDate(int year, int month, int day){
        return (CalendarItem) new Select()
                .from(CalendarItem.class)
                .where("numOfYear = '" + year + "' AND numOfMonth = '" + month + "' AND numOfDay = '" + day + "'")
                .executeSingle();
    }

    public List<CalendarItem> getItemsFromMonth(int year, int month){
        return new Select()
                .from(CalendarItem.class)
                .where("numOfYear = '" + year+ "' AND numOfMonth = '" + month + "'")
                .execute();
    }

    public List<CalendarItem> getAllItems(){
        return new Select()
                .from(CalendarItem.class)
                .execute();
    }
}
