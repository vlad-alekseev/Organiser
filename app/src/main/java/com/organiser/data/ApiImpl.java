package com.organiser.data;

import com.organiser.Constants;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiImpl {

    public interface ICurrencyListener {
        void onSuccess(String jsonString);

        void onFailure();
    }

    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_OPEN_WEATHER_MAP)
            .build();

    public ApiImpl() {

    }

    public String getForecast(final String city, final String tempUnits, final ICurrencyListener listener) {

        ForecastWeatherApi service = retrofit.create(ForecastWeatherApi.class);
        Call<ResponseBody> result = service.getForecast(city, tempUnits);
        final String[] jsonStr = {""};

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (listener != null) {
                        listener.onSuccess(response.body().string());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure();
            }

        });

        return jsonStr[0];
    }

}
