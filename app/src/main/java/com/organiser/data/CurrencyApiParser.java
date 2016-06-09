package com.organiser.data;

import com.organiser.model.ForecastItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyApiParser {

    private String mApi;
    private List<ForecastItems> mForecastItemList = new ArrayList<>();


    public CurrencyApiParser(String api) {
        mApi = api;
        getCurrencyItemList();
    }

    private void getCurrencyItemList() {

        try {
            JSONObject jsonObject = new JSONObject(mApi);
            String city = getCityName(jsonObject);
            JSONArray jsonList = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject newObject = new JSONObject(jsonList.get(i).toString());
                ForecastItems forecastItems = new ForecastItems();
                forecastItems.setCityName(city);
                forecastItems.setTimeMilliseconds(getTimeMilliseconds(newObject));
                forecastItems.setTempCurrent(getMainInfo("temp", newObject));
                forecastItems.setTempMin(getMainInfo("temp_min", newObject));
                forecastItems.setTempMax(getMainInfo("temp_max", newObject));
                forecastItems.setWeatherParameters(getWeatherInfo("description", newObject));
                forecastItems.setWeatherIconId(getWeatherInfo("icon", newObject));
                forecastItems.setWindSpeed(getWindInfo("speed", newObject));
                forecastItems.setDateTime(getDataTime(newObject));
                mForecastItemList.add(forecastItems);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getCityName(JSONObject jsonObject) throws JSONException {
        JSONObject cityObject = jsonObject.getJSONObject("city");
        return cityObject.getString("name");
    }

    private long getTimeMilliseconds(JSONObject newObject) throws JSONException {
        return Long.parseLong(newObject.getString("dt"));
    }

    private String getDataTime(JSONObject newObject) throws JSONException {
        return newObject.getString("dt_txt");
    }

    private double getWindInfo(String info, JSONObject jsonObject) throws JSONException {
        JSONObject tempObject = jsonObject.getJSONObject("wind");
        return tempObject.getDouble(info);
    }

    private String getWeatherInfo(String description, JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject newObject = jsonArray.getJSONObject(0);
        return newObject.getString(description);
    }

    private double getMainInfo(String temperature, JSONObject jsonObject) throws JSONException {
        JSONObject tempObject = jsonObject.getJSONObject("main");
        return tempObject.getDouble(temperature);
    }

    public List<ForecastItems> getForecastItemList() {
        return mForecastItemList;
    }

}
