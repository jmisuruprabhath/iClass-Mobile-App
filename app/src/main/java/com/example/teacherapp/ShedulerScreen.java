package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShedulerScreen extends AppCompatActivity {

    CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheduler_screen);

        customCalendarView = (CustomCalendarView) findViewById(R.id.custom_calendar_view);
    }
}