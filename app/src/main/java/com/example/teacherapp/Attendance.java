package com.example.teacherapp;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class Attendance extends AppCompatActivity {

    private DatabaseReference reference;


    private RecyclerView recyclerView;
    studentAdapter adapter;
    //TreeMap<String, Object> arr;
    private DatabaseReference referenceAttendance;
    private classModel model;
    private String[] date;
    private ArrayList<Integer> graphValues = new ArrayList<Integer>();
    private ProgressDialog saveloader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //getting relevant model class
        model = passModel.getModel();
        if (model == null) {
            startActivity(new Intent(Attendance.this, classView.class));
        }
        reference = passModel.getReference();


        if (getSupportActionBar() != null) {     // calling the action bar
            ActionBar actionBar = getSupportActionBar();


            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.purple_500));
            actionBar.setBackgroundDrawable(colorDrawable);
            actionBar.setTitle(model.getName());

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Toast.makeText(this, "Loading " + model.getName(), Toast.LENGTH_SHORT).show();

        String pid = passModel.getModel().getId();
        assert pid != null;

        reference = reference.child(pid).child("StudentList");

        //creating reference for attendance

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String onlineUserID = null;
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        } else {
            Toast.makeText(this, "Error Attendance authentication", Toast.LENGTH_SHORT).show();
            return;
        }
        referenceAttendance = FirebaseDatabase.getInstance().getReference().child("attendance").child(onlineUserID);


        //adding current date
        EditText EditDate = findViewById(R.id.attendanceDate);
        EditDate.setText(DateFormat.getDateInstance().format(new Date()));

        fetchStudent();

        storeAttendance();


    }


    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addStudent:
                addStudent();
                return true;
            case R.id.analyze:
                analyseGraph(null);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Attendance.this, LineGraph.class));
                return true;
            default:
                startActivity(new Intent(this, classView.class));
                return false;
        }


        //return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_attendance_student, menu);
        return true;
    }


    private void addStudent() {

        ProgressDialog saveloader = new ProgressDialog(this);

        AlertDialog.Builder myDialog = new AlertDialog.Builder(Attendance.this);
        LayoutInflater inflater = LayoutInflater.from(Attendance.this);
        View myView = inflater.inflate(R.layout.add_student, null);
        myDialog.setView(myView);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();


        EditText name = myView.findViewById(R.id.studentName);
        EditText sid = myView.findViewById(R.id.studentId);
        Button save = myView.findViewById(R.id.saveClass);
        Button cancel = myView.findViewById(R.id.addClass);

        //saveButton
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = name.getText().toString().trim();
                String sSid = sid.getText().toString().trim();
                String id = reference.push().getKey();

                if (TextUtils.isEmpty(sSid)) {
                    int x = (int) (Math.random() * 1000);
                    sSid = "SID" + x;
                }

                if (TextUtils.isEmpty(sName)) {
                    name.setError("Student name is required");
                    return;
                } else {
                    saveloader.setMessage("Saving the new student");
                    saveloader.setCanceledOnTouchOutside(false);
                    saveloader.show();

                    studentModel smodel = new studentModel(sName, sSid, id);
                    reference.child(id).setValue(smodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Attendance.this, "Student Saved Successfully!", Toast.LENGTH_SHORT).show();
                                saveloader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(Attendance.this, "Failed : " + error, Toast.LENGTH_SHORT).show();
                                saveloader.dismiss();
                            }
                        }
                    });

                }

                dialog.dismiss();
            }
        });


        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }


    private void fetchStudent() {
        recyclerView = findViewById(R.id.recyclerClass);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<studentModel> options = new FirebaseRecyclerOptions.Builder<studentModel>()
                .setQuery(reference, studentModel.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new studentAdapter(options, this);
        // Connecting Adapter class with the Recycler view
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stoping of the activity
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void storeAttendance() {

        Button submit = findViewById(R.id.submitAttendance);
        saveloader = new ProgressDialog(this);


        final Calendar myCalendar = Calendar.getInstance();

        EditText edittext = (EditText) findViewById(R.id.attendanceDate);
        DatePickerDialog.OnDateSetListener dateCal = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                edittext.setText(DateFormat.getDateInstance().format(myCalendar.getTime()));

                date = DateFormat.getDateInstance().format(myCalendar.getTime()).split("\\s|,");

            }

        };
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Attendance.this, dateCal, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Attendance.this, "Submitting", Toast.LENGTH_SHORT).show();
                Map<String, Object> mapValues = passModel.getList();
                int count = mapValues.size();

                if (!mapValues.isEmpty()) {
                    //mapValues.put("Count", mapValues.size());  //Adding the attendance count to db

                    if (date == null) {
                        date = DateFormat.getDateInstance().format(new Date()).split("\\s|,");
                    }
                    mapValues.put("Date", date[0] + " " + date[1]); //adding current date to database

                    //creating node according to year and month
                    DatabaseReference newReferenceAttendance = referenceAttendance.child(model.getName()).child(date[3]).child(date[0]);

                    DatabaseReference pRef = referenceAttendance.child(model.getName()).child(date[3]); //parameter to  pass update graph data

                    String id = newReferenceAttendance.push().getKey();

                    newReferenceAttendance.child(id).updateChildren(mapValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Attendance.this, "Attendance marked Successfully!", Toast.LENGTH_SHORT).show();
                                updateGraphData(pRef, date[0], count);
                                //saveloader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(Attendance.this, "Failed : " + error, Toast.LENGTH_SHORT).show();
                                saveloader.dismiss();
                            }
                           // startActivity(new Intent(Attendance.this, classView.class));
                        }
                    });

                } else {
                    Toast.makeText(Attendance.this, "No attendance marked", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Attendance.this, date[3], Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void updateGraphData(DatabaseReference ref, String s, int count) {

        Map<String, Object> plots;
        ref.child("summary").child(s).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(Attendance.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    long summaryCount = calculateSummary(task.getResult().getValue(), count);


                    summary(ref, s, summaryCount);
                    /*if (task.getResult().getValue() == null) {
                        //if there is key value pair in data base add the current count to db
                        summary(ref, s, count);
                    }
                    else{
                        //add previous count + current count
                        long newCount = count + (long) task.getResult().getValue();
                        //Toast.makeText(Attendance.this,(int)newCount, Toast.LENGTH_SHORT).show();
                        summary(ref, s, newCount);
                    }*/


                }
            }
        });


    }

    private void summary(DatabaseReference ref, String s, long count) {

        Map<String, Object> plots = new TreeMap<>();
        plots.put(s, count);

        ref.child("summary").updateChildren(plots).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Attendance.this, "Attendance marked Successfully!", Toast.LENGTH_SHORT).show();
                    //saveloader.dismiss();
                } else {
                    String error = task.getException().toString();
                    Toast.makeText(Attendance.this, "Failed : " + error, Toast.LENGTH_SHORT).show();
                    //saveloader.dismiss();
                }
                startActivity(new Intent(Attendance.this, classView.class));
            }
        });
    }


    public void analyseGraph(String s) {

        String year = s;
        String className = passModel.getModel().getName();
        if (year == null) {
            String[] getDate = DateFormat.getDateInstance().format(new Date()).split("\\s|,");   //getting the current year
            year = getDate[3];
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String onlineUserID = null;
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        } else {
            //Toast.makeText(context, "Error Attendance authentication", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference summeryRef = FirebaseDatabase.getInstance().getReference().child("attendance").child(onlineUserID)
                .child(className).child(year).child("summary");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get summaryAttendance object and use the values to update the UI
                attendanceModel plots = dataSnapshot.getValue(attendanceModel.class);
                if (plots == null) {
                    new plotModel(new attendanceModel());
                } else {
                    new plotModel(plots);
                }
                //startActivity(new Intent(Attendance.this, LineGraph.class));
                //finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        summeryRef.addValueEventListener(postListener);

    }


    long calculateSummary(Object value, int count) {

        if (value == null) {
            //if there is key value pair in data base add the current count to db
            return count;
        } else {
            //add previous count + current count
            long newCount = count + (long) value;
            //Toast.makeText(Attendance.this,(int)newCount, Toast.LENGTH_SHORT).show();
            return newCount;
        }

    }

}