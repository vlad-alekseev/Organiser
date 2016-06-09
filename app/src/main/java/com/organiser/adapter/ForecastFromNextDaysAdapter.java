package com.organiser.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.organiser.Constants;
import com.organiser.R;
import com.organiser.TimeConverter;
import com.organiser.model.ForecastItems;
import com.organiser.adapter.viewHolder.ForecastFromDayViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ForecastFromNextDaysAdapter extends RecyclerView.Adapter<ForecastFromDayViewHolder> {

    private View mView;
    private List<ForecastItems> mForecastItems = new ArrayList<>();

    public ForecastFromNextDaysAdapter(List<ForecastItems> items) {
        mForecastItems = items;
    }

    @Override
    public ForecastFromDayViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_forecast_from_day, viewGroup, false);
        return new ForecastFromDayViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ForecastFromDayViewHolder viewHolder, int position) {

        ForecastItems newForecast = mForecastItems.get(position);
        String imageName = "w" + newForecast.getWeatherIconId();
        int resID = mView.getResources().getIdentifier(imageName, "mipmap", mView.getContext().getPackageName());

        viewHolder.icon.setBackgroundResource(resID);
        viewHolder.date.setText(TimeConverter.convertDate(
                newForecast.getDateTime(), Constants.FORMAT_YYYY_MM_DD, "MM/dd"));
        viewHolder.tempMin.setText(String.valueOf((int) newForecast.getTempMin()) + "º");
        viewHolder.tempMax.setText(String.valueOf((int) newForecast.getTempMax()) + "º");
    }

    @Override
    public int getItemCount() {
        return mForecastItems.size();
    }

}
