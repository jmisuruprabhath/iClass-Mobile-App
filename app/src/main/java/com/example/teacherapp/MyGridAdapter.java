package com.example.teacherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;

    public MyGridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.single_cell_layout);

        this.dates = dates;
        this.currentDate = currentDate;
        inflater = LayoutInflater.from(context);
        this.events = events;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.single_cell_layout, parent,false);
        }


        if(displayMonth == currentMonth && displayYear== currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }else{
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
        }
        if(currentDay==DayNo){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.teal_700));
        }


        TextView DAY_NUMBER = view.findViewById(R.id.calendar_day);
        TextView EventNumber = view.findViewById(R.id.events_id);
        DAY_NUMBER.setText(String.valueOf(DayNo));
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<events.size();i++){
            eventCalendar.setTime(ConvertStringToDate(events.get(i).getDATE()));
            if(DayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
            && displayYear == eventCalendar.get(Calendar.YEAR)){
                arrayList.add(events.get(i).getEVENT());
                EventNumber.setText(arrayList.size()+" events");
            }
        }

        return view;
    }


    private Date ConvertStringToDate(String eventDate){
        java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
