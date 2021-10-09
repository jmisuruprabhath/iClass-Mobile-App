package com.example.teacherapp;



import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LineGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);


        if (getSupportActionBar() != null) {     // calling the action bar
            ActionBar actionBar = getSupportActionBar();


            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.purple_500));
            actionBar.setBackgroundDrawable(colorDrawable);

            actionBar.setTitle("Attendance Analyse");

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(LineGraph.this, Attendance.class));
        this.finish();
        return super.onOptionsItemSelected(item);

    }
}