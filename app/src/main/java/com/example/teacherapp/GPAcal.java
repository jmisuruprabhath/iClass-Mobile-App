package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GPAcal extends AppCompatActivity {

    public static double getCounter;
    private EditText Credit;
    private Button addCourse,seeGpa,erase;
    private TextView textView;
    private Spinner spinner;
    double gradesValue;
    double counter=0,total_credit=0,credit=0,grade=0;
    ImageView gpaback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpacal);

        Credit =findViewById(R.id.edittext1);
        spinner =findViewById(R.id.spinnergrade);
        addCourse =findViewById(R.id.btn1);
        seeGpa =findViewById(R.id.btn2);
        erase =findViewById(R.id.btn3);
        textView =findViewById(R.id.textviewgpa);

        //Back Button Function
        gpaback = findViewById(R.id.gpaback);
        gpaback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GPAcal.this, CalculatorsScreen.class);
                startActivity(intent);
            }
        });

        final String[] grades = {"A+","A", "A-", "B+", "B","B-", "C+", "C","C-","D+","D","E"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_gpa, grades);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

               // parent.getItemAtPosition(i);

                String selectedGrade = (String) parent.getItemAtPosition(i);


                switch (selectedGrade) {
                    case "A+":
                    case "A":
                        gradesValue = 4.00;
                        break;
                    case "A-":
                        gradesValue = 3.70;
                        break;
                    case "B+":
                        gradesValue = 3.30;
                        break;
                    case "B":
                        gradesValue = 3.00;
                        break;
                    case "B-":
                        gradesValue = 2.70;
                        break;
                    case "C+":
                        gradesValue = 2.30;
                        break;
                    case "C":
                        gradesValue = 2.00;
                        break;
                    case "C-":
                        gradesValue = 1.70;
                        break;
                    case "D+":
                        gradesValue = 1.30;
                        break;
                    case "D":
                        gradesValue = 1.00;
                        break;
                    case "E":
                        gradesValue = 0.00;
                        break;
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String credit1=Credit.getText().toString();
                if(credit1.length()==0)
                {
                    Credit.requestFocus();
                    Credit.setError("Field cannot be Empty");
                }
                else if(credit1.matches("[a-zA-Z ]+")){
                    Credit.requestFocus();
                    Credit.setError("Field cannot contain any letters ");
                }
                else {

                    credit = Double.parseDouble(Credit.getText().toString());
                    Credit.setText("");
                    spinner.setSelection(0);

                    //counter += (gradesValue * credit);
                    getCounter(gradesValue,credit);
                    total_credit += credit;
                    Toast.makeText(getApplicationContext(), "Credit: " + credit + "\nGrade: " + gradesValue, Toast.LENGTH_LONG).show();
                }
            }
        });


        seeGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result= counter/total_credit;
                String strRe= String.format("%.2f", result);
                textView.setText("Your GPA is: "+strRe);
            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=0;
                total_credit=0;
                credit=0;
                grade=0;

                Credit.setText("");
                spinner.setSelection(0);
                textView.setText("");

            }
        });

    }

    public double getCounter(double a, double b){
        a= gradesValue;
        b= credit;

        counter += (a * b);

        return counter;
    }
}