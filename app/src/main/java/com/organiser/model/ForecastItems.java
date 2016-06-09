package com.organiser.model;

import com.activeandroid.Model;

public class ForecastItems extends Model {


    private double tempCurrent;
    private double tempMin;
    private double tempMax;
    private String weatherParameters;
    private String weatherIconId;
    private double windSpeed;
    private String dateTime;
    private long timeMilliseconds;
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getTimeMilliseconds() {
        return timeMilliseconds;
    }

    public void setTimeMilliseconds(long timeMilliseconds) {
        this.timeMilliseconds = timeMilliseconds;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getTempCurrent() {
        return tempCurrent;
    }

    public void setTempCurrent(double tempCurrent) {
        this.tempCurrent = tempCurrent;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public String getWeatherParameters() {
        return weatherParameters;
    }

    public void setWeatherParameters(String weatherParameters) {
        this.weatherParameters = weatherParameters;
    }

    public String getWeatherIconId() {
        return weatherIconId;
    }

    public void setWeatherIconId(String weatherIconId) {
        this.weatherIconId = weatherIconId;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public ForecastItems(){
        super();
    }

    public ForecastItems(Long timeMilliseconds, String dateTime, double tempCurrent, double tempMin, double tempMax,
                         String weatherParameters, String weatherIconId, double windSpeed) {
        super();
        this.dateTime = dateTime;
        this.tempCurrent = tempCurrent;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.weatherParameters = weatherParameters;
        this.weatherIconId = weatherIconId;
        this.windSpeed = windSpeed;
        this.timeMilliseconds = timeMilliseconds;
    }




}
