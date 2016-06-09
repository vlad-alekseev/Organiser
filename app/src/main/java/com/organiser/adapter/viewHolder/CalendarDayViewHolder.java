package com.organiser.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.organiser.R;


public class CalendarDayViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout body;
    public TextView calendarDay;
    public ImageButton messageButton;

    public CalendarDayViewHolder(View itemView) {
        super(itemView);

        calendarDay = (TextView) itemView.findViewById(R.id.numOfDay);
        messageButton = (ImageButton) itemView.findViewById(R.id.messageIcon);
        body = (RelativeLayout) itemView.findViewById(R.id.calendarItemBody);
    }
}
