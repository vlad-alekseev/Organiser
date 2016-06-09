package com.organiser.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.organiser.R;


public class ForecastFromDayViewHolder extends RecyclerView.ViewHolder {

    public TextView tempMin, tempMax, date;
    public ImageView icon;

    public ForecastFromDayViewHolder(View itemView) {
        super(itemView);

        tempMin = (TextView) itemView.findViewById(R.id.tempMinDay);
        tempMax = (TextView) itemView.findViewById(R.id.tempMaxDay);
        date = (TextView) itemView.findViewById(R.id.dateId);
        icon = (ImageView) itemView.findViewById(R.id.iconFromDay);
    }
}