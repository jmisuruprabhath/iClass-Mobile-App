package com.example.teacherapp;

import static android.text.TextUtils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AverageCal extends AppCompatActivity {

    EditText mark1, mark2, mark3, total, average,grade;
    Button calculate, clear;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_cal);

        mark1 = findViewById(R.id.mark1);
        mark2 = findViewById(R.id.mark2);
        mark3 = findViewById(R.id.mark3);
        total = findViewById(R.id.totmarks);
        average = findViewById(R.id.totavg);
        grade = findViewById(R.id.grade);

        calculate = findViewById(R.id.totalcal);
        clear = findViewById(R.id.totclear);

        back = findViewById(R.id.calback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AverageCal.this, CalculatorsScreen.class);
                startActivity(intent);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    marksCal();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    public int getTotal(int m1, int m2, int m3) {
        return m1+m2+m3;
    }

    public double getAverage(int m1, int m2, int m3) {
        int tot = m1+m2+m3;
        return tot/3.0;
    }

    public void marksCal() {
        int m1, m2, m3, tot;
        String mrk1, mrk2, mrk3;
        double avg;
        String grd;

        mrk1 = mark1.getText().toString().trim();
        mrk2 = mark2.getText().toString().trim();
        mrk3 = mark3.getText().toString().trim();

        if(TextUtils.isEmpty(mrk1)) {
            mark1.setError("Mark 1 is required");
            return;
        }
        if(Integer.parseInt(mrk1)<0 || Integer.parseInt(mrk1)>100) {
            mark1.setError("Invalid Mark");
            return;
        }

        if(TextUtils.isEmpty(mrk2)) {
            mark2.setError("Mark 2 is required");
            return;
        }
        if(Integer.parseInt(mrk2)<0 || Integer.parseInt(mrk2)>100) {
            mark2.setError("Invalid Mark");
            return;
        }

        if(TextUtils.isEmpty(mrk3)) {
            mark3.setError("Mark 3 is required");
            return;
        }
        if(Integer.parseInt(mrk3)<0 || Integer.parseInt(mrk3)>100) {
            mark3.setError("Invalid Mark");
            return;
        }
        else {
            m1 = Integer.parseInt(mrk1);
            m2 = Integer.parseInt(mrk2);
            m3 = Integer.parseInt(mrk3);

            tot = getTotal(m1,m2,m3);
            avg = getAverage(m1,m2,m3);

            total.setText(String.valueOf(tot));
            average.setText(String.valueOf(avg));

            if(avg >= 75) {
                grade.setText("A");
            }
            else if(avg >= 65) {
                grade.setText("B");
            }
            else if(avg >= 55) {
                grade.setText("C");
            }
            else if(avg >= 45) {
                grade.setText("D");
            }
            else {
                grade.setText("F");
            }
        }


        }

    public void clear() {
        mark1.setText("");
        mark2.setText("");
        mark3.setText("");
        total.setText("");
        average.setText("");
        grade.setText("");
        mark1.requestFocus();
    }
}

class TestAverageCal {
    public int getTotal(int m1, int m2, int m3) {
        return m1+m2+m3;
    }

    public double getAverage(int m1, int m2, int m3) {
        int tot = m1+m2+m3;
        return tot/3.0;
    }
}