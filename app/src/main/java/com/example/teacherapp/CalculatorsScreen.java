package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;


public class CalculatorsScreen extends AppCompatActivity {
    ImageView gpai, vcal, averagecal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators_screen);

        gpai = findViewById(R.id.gpaii);
        vcal = findViewById(R.id.voicecal);
        averagecal = findViewById(R.id.averagecalimg);

        gpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorsScreen.this, GPAcal.class);
                startActivity(intent);
                finish();
            }
        });

        vcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorsScreen.this, VoiceCal.class);
                startActivity(intent);
                finish();
            }
        });

        averagecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorsScreen.this, AverageCal.class);
                startActivity(intent);
                finish();
            }
        });



    }
    public void goToClass(View view) {
    }

}