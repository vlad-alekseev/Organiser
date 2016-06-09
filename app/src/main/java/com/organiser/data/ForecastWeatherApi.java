package com.organiser.data;


import com.organiser.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastWeatherApi {
    @GET("data/2.5/forecast?&mode=json&appid=" + Constants.API_WEATHER_KEY)
    Call<ResponseBody> getForecast(@Query("q") String city, @Query("units") String tempUnits);
}
