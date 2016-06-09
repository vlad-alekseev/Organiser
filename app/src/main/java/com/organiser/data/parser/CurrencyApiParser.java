package com.organiser.data.parser;

import com.organiser.model.ForecastItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyApiParser {

    private String mApi;
    private List<ForecastItem> mForecastItemList = new ArrayList<>();


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
                ForecastItem forecastItem = new ForecastItem();
                forecastItem.setCityName(city);
                forecastItem.setTimeMilliseconds(getTimeMilliseconds(newObject));
                forecastItem.setTempCurrent(getMainInfo("temp", newObject));
                forecastItem.setTempMin(getMainInfo("temp_min", newObject));
                forecastItem.setTempMax(getMainInfo("temp_max", newObject));
                forecastItem.setWeatherParameters(getWeatherInfo("description", newObject));
                forecastItem.setWeatherIconId(getWeatherInfo("icon", newObject));
                forecastItem.setWindSpeed(getWindInfo("speed", newObject));
                forecastItem.setDateTime(getDataTime(newObject));
                mForecastItemList.add(forecastItem);
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

    public List<ForecastItem> getForecastItemList() {
        return mForecastItemList;
    }

}
