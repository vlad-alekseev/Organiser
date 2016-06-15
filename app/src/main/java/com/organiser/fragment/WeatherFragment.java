package com.organiser.fragment;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.organiser.Constants;
import com.organiser.R;
import com.organiser.adapter.ForecastFromNextDaysAdapter;
import com.organiser.adapter.ForecastFromTimeAdapter;
import com.organiser.data.apiImplementation.ApiImplForecast;
import com.organiser.data.parser.CurrencyApiParser;
import com.organiser.model.ForecastItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class WeatherFragment extends BaseTabFragment {

    private View mView;
    private ApiImplForecast mApi;
    private long oneDayInMillis;
    private ImageView mWeatherIcon;
    private SharedPreferences mCash;
    private GridLayoutManager lLayout;
    private RelativeLayout mLoadIndicator;
    private ForecastFromTimeAdapter mTimeAdapter;
    private ForecastFromNextDaysAdapter mDayAdapter;
    private ImageButton mLocationButton, mRefreshButton;
    private RecyclerView mRecyclerViewFromTime, mRecyclerViewFromDate;
    private String tempUnits = "metric", mJsonString = null, mCity = "kirovohrad";
    private List<ForecastItem> mListForecastItems = new ArrayList<>();
    private TextView mTemperatureNow, mWeatherParameter, mWindSpeed, mCityNameView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_weather, container, false);
        setRetainInstance(true);

        initViews();
        setDefault();
        if (mJsonString != null) {
            setAllInfo(mJsonString);
        }
        getForecastAndSetInfo();

        return mView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
        }
        View view = onCreateView(getActivity().getLayoutInflater(), viewGroup, null);
        if (viewGroup != null) {
            viewGroup.addView(view);
        }

    }

    private void initViews() {
        mWindSpeed = (TextView) mView.findViewById(R.id.windSpeed);
        mWeatherIcon = (ImageView) mView.findViewById(R.id.weather_ic);
        mCityNameView = (TextView) mView.findViewById(R.id.current_city);
        mTemperatureNow = (TextView) mView.findViewById(R.id.degreeNumber);
        mRefreshButton = (ImageButton) mView.findViewById(R.id.refreshButton);
        mLoadIndicator = (RelativeLayout) mView.findViewById(R.id.loadingView);
        mLocationButton = (ImageButton) mView.findViewById(R.id.locationButton);
        mWeatherParameter = (TextView) mView.findViewById(R.id.weatherParameter);
        mRecyclerViewFromTime = (RecyclerView) mView.findViewById(R.id.recyclerForecastFromTime);
        mRecyclerViewFromDate = (RecyclerView) mView.findViewById(R.id.recyclerForecastFromDate);
    }

    private void setDefault() {
        mApi = new ApiImplForecast();
        mRefreshButton.setOnClickListener(refreshButtonListener);
        mLocationButton.setOnClickListener(changeLocationListener);
        oneDayInMillis = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) / 1000;
        mCash = mView.getContext().getSharedPreferences(Constants.PREFERENCES, mView.getContext().MODE_PRIVATE);

        if (mCash.contains(Constants.PREFERENCES_FORECAST)) {
            mCity = mCash.getString(Constants.PREFERENCES_CITY_NAME, "");
            mJsonString = mCash.getString(Constants.PREFERENCES_FORECAST, "");
        }
    }

    private void getForecastAndSetInfo() {
        mApi.getForecast(mCity, tempUnits, new ApiImplForecast.ICurrencyListener() {
            @Override
            public void onSuccess(String jsonString) {
                setAllInfo(jsonString);
            }

            @Override
            public void onFailure() {
                Toast.makeText(mView.getContext(), R.string.no_internet,
                        Toast.LENGTH_LONG).show();

                mLoadIndicator.setVisibility(View.GONE);
            }
        });
    }

    private void setAllInfo(String jsonString) {
        CurrencyApiParser parser = new CurrencyApiParser(jsonString);
        mListForecastItems = parser.getForecastItemList();
        if (mListForecastItems.size() > 0) {
            setAdapterFromTime();

            List<ForecastItem> fourDaysForecast = getForecastFromNextFourDays(mListForecastItems);
            setAdapterFromDate(fourDaysForecast);
            setTodayInfo();

            SharedPreferences.Editor editor = mCash.edit();
            editor.putString(Constants.PREFERENCES_FORECAST, jsonString);
            editor.apply();
        } else {
            Toast.makeText(getContext(), jsonString, Toast.LENGTH_SHORT).show();
        }

        mLoadIndicator.setVisibility(View.GONE);
    }

    private void setTodayInfo() {
        ForecastItem currentWeatherInfo = mListForecastItems.get(1);
        mTemperatureNow.setText(String.valueOf((int) currentWeatherInfo.getTempCurrent()));
        mWeatherParameter.setText(currentWeatherInfo.getWeatherParameters());
        mWindSpeed.setText(String.valueOf(currentWeatherInfo.getWindSpeed()));
        mCityNameView.setText(currentWeatherInfo.getCityName());

        String imageName = "w" + currentWeatherInfo.getWeatherIconId();
        int resID = getResources().getIdentifier(imageName, "mipmap", getContext().getPackageName());
        mWeatherIcon.setBackgroundResource(resID);

        SharedPreferences.Editor editor = mCash.edit();
        editor.putString(Constants.PREFERENCES_CITY_NAME, currentWeatherInfo.getCityName());
        editor.apply();
    }

    private List<ForecastItem> getForecastFromNextFourDays(List<ForecastItem> mListForecastItems) {
        List<ForecastItem> res = new ArrayList<>();
        int k = 0;
        long todayInMillis = Calendar.getInstance().getTimeInMillis() / 1000;
        for (int i = 0; i < 4; i++) {
            ForecastItem newForecast = new ForecastItem();

            int minTemp = 100;
            int maxTemp = -100;
            String date = "";
            Map<String, Integer> iconMap = new HashMap<>();

            // takes the data from the respective four days
            while (mListForecastItems.get(k).getTimeMilliseconds() <
                    (todayInMillis + oneDayInMillis * (i + 1))) {

                // check minimum temperature
                if (minTemp > mListForecastItems.get(k).getTempCurrent())
                    minTemp = (int) mListForecastItems.get(k).getTempCurrent();

                // check maximum temperature
                if (maxTemp < mListForecastItems.get(k).getTempCurrent())
                    maxTemp = (int) mListForecastItems.get(k).getTempCurrent();

                // writes data to the map, where the key - it is id icon,
                // and the value - the amount of a meet this icon
                if (iconMap.containsKey(mListForecastItems.get(k).getWeatherIconId())) {
                    int lastNumber = iconMap.get(mListForecastItems.get(k).getWeatherIconId());
                    iconMap.put(mListForecastItems.get(k).getWeatherIconId(), lastNumber++);
                } else {
                    iconMap.put(mListForecastItems.get(k).getWeatherIconId(), 1);
                }

                date = mListForecastItems.get(k).getDateTime().substring(0, 10);
                k++;
            }

            // check which icon is met more often than others
            String idIcon = "";
            int colIconView = 0;
            for (Map.Entry<String, Integer> entry : iconMap.entrySet()) {
                if (entry.getValue() > colIconView) {
                    idIcon = entry.getKey();
                    colIconView = entry.getValue();
                }
            }

            newForecast.setTempMin(minTemp);
            newForecast.setTempMax(maxTemp);
            newForecast.setDateTime(date);
            newForecast.setWeatherIconId(idIcon);
            res.add(newForecast);
        }
        return res;
    }

    private void setAdapterFromTime() {
        mTimeAdapter = new ForecastFromTimeAdapter(mListForecastItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewFromTime.setLayoutManager(layoutManager);
        mRecyclerViewFromTime.setHasFixedSize(false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerViewFromTime.setItemAnimator(itemAnimator);
        mRecyclerViewFromTime.setAdapter(mTimeAdapter);
    }

    private void setAdapterFromDate(List<ForecastItem> listForecast) {
        mDayAdapter = new ForecastFromNextDaysAdapter(listForecast);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            lLayout = new GridLayoutManager(getActivity(), 4);
        } else {
            lLayout = new GridLayoutManager(getActivity(), 2);
        }

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerViewFromDate.setHasFixedSize(true);
        mRecyclerViewFromDate.setLayoutManager(lLayout);
        mRecyclerViewFromDate.setItemAnimator(itemAnimator);
        mRecyclerViewFromDate.setAdapter(mDayAdapter);
    }

    View.OnClickListener changeLocationListener = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            final EditText newLocation = new EditText(v.getContext());
            newLocation.setSingleLine(true);
            alert.setTitle(R.string.enter_city_name);
            alert.setView(newLocation);

            alert.setPositiveButton(R.string.get_forecast, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mCity = newLocation.getText().toString();
                    if (mCity.length() > 0) {
                        Toast.makeText(mView.getContext(), mCity, Toast.LENGTH_LONG).show();
                        mLoadIndicator.setVisibility(View.VISIBLE);
                        getForecastAndSetInfo();
                    }
                }
            });

            alert.setNegativeButton(R.string.cancel, null);
            alert.show();
        }
    };

    View.OnClickListener refreshButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            mLoadIndicator.setVisibility(View.VISIBLE);
            getForecastAndSetInfo();
        }
    };


    @Override
    public String getTitle() {
        if (getActivity() != null)
            return getString(R.string.calendar);
        return "";
    }


}