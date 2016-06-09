package com.organiser.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organiser.Constants;
import com.organiser.TimeConverter;
import com.organiser.adapter.viewHolder.ForecastFromTimeViewHolder;
import com.organiser.model.ForecastItems;
import com.organiser.R;

import java.util.ArrayList;
import java.util.List;

public class ForecastFromTimeAdapter extends RecyclerView.Adapter<ForecastFromTimeViewHolder> {

    private View mView;
    private List<ForecastItems> mForecastItems = new ArrayList<>();

    public ForecastFromTimeAdapter(List<ForecastItems> items) {
        mForecastItems = items;
    }

    @Override
    public ForecastFromTimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_forecast_from_time, viewGroup, false);
        return new ForecastFromTimeViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ForecastFromTimeViewHolder viewHolder, int position) {

        ForecastItems newForecast = mForecastItems.get(position);
        String imageName = "w" + newForecast.getWeatherIconId();
        int resID = mView.getResources().getIdentifier(imageName, "mipmap", mView.getContext().getPackageName());

        viewHolder.icon.setBackgroundResource(resID);
        viewHolder.time.setText(TimeConverter.convertDate(
                newForecast.getDateTime(), Constants.FORMAT_YYYY_MM_DD_HH_MM_SS, "HH:mm"));
        viewHolder.date.setText(TimeConverter.convertDate(
                newForecast.getDateTime(), Constants.FORMAT_YYYY_MM_DD_HH_MM_SS, "MM/dd"));
        viewHolder.temperature.setText(String.valueOf((int) newForecast.getTempCurrent()) + "º");

    }

    @Override
    public int getItemCount() {
        return mForecastItems.size();
    }

}
