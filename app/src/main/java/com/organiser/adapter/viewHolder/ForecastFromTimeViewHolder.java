package com.organiser.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.organiser.R;


public class ForecastFromTimeViewHolder extends RecyclerView.ViewHolder {

    public TextView time, temperature, date;
    public ImageView icon;

    public ForecastFromTimeViewHolder(View itemView) {
        super(itemView);

        time = (TextView) itemView.findViewById(R.id.textTime);
        date = (TextView) itemView.findViewById(R.id.textDate);
        temperature = (TextView) itemView.findViewById(R.id.temperature);
        icon = (ImageView) itemView.findViewById(R.id.imageWeather);
    }
}
