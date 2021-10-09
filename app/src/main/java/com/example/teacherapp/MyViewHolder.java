package com.example.teacherapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView DateTxt, Event, Time;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        DateTxt = itemView.findViewById(R.id.eventdate);
        Event = itemView.findViewById(R.id.eventname);
        Time = itemView.findViewById(R.id.eventtime);
    }
}
