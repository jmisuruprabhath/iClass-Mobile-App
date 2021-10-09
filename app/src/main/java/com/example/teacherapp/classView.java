package com.example.teacherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;


public class classView extends AppCompatActivity {


    //fireBase variables
    private DatabaseReference reference;
    private String onlineUserID;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ProgressDialog saveloader;


    //ListView listView;
    //String mTitle[] = {"Physics", "Maths", "Software Development", "History", "International law"};
    //String mDescription[] = {"Physics Description", "Maths Description", "IT Description", "sad Description", "Law Description"};

    private RecyclerView recyclerView;
    classAdapter adapter;


    private ActionBar actionBar;

    // For the update task
    private String key = "";
    private String updateName;
    private String updateDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_class_view);


        if (getSupportActionBar() != null) {     // calling the action bar
            ActionBar actionBar = getSupportActionBar();


            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24);
            ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.purple_500));
            actionBar.setBackgroundDrawable(colorDrawable);

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fetchData();

        createClass();

    }


    private void fetchData() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        }
        reference = FirebaseDatabase.getInstance().getReference().child("classes").child(onlineUserID);
        recyclerView = findViewById(R.id.recyclerClass);

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // To display the Recycler view linearly
        // It is a class provide by the FirebaseUI to make a query in the database to fetch appropriate data
        FirebaseRecyclerOptions<classModel> options = new FirebaseRecyclerOptions.Builder<classModel>()
                .setQuery(reference, classModel.class)
                .build();

        adapter = new classAdapter(options);     //Connecting object required Adapter class

        recyclerView.setAdapter(adapter);       // Connecting Adapter class with the Recycler view
    }

    // Function to tell the app to start getting data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }
    // Function to tell the app to stop getting data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



    private void createClass() {
        saveloader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        }
        reference = FirebaseDatabase.getInstance().getReference().child("classes").child(onlineUserID);


        FloatingActionButton addNewClass = findViewById(R.id.addClass);

        addNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(classView.this);
                LayoutInflater inflater = LayoutInflater.from(classView.this);
                View myView = inflater.inflate(R.layout.add_class, null);
                myDialog.setView(myView);

                AlertDialog dialog = myDialog.create();
                dialog.setCancelable(false);
                dialog.show();


                EditText name = myView.findViewById(R.id.className);
                EditText description = myView.findViewById(R.id.classDescription);
                EditText batch = myView.findViewById(R.id.batch);
                EditText time = myView.findViewById(R.id.timeInput);
                Button save = myView.findViewById(R.id.saveClass);
                Button cancel = myView.findViewById(R.id.cancelBtn);

                //cancel button
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                //saveButton
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nClass = name.getText().toString().trim();
                        String nDescription = description.getText().toString().trim();
                        String nBatch = batch.getText().toString().trim();
                        String nTime = time.getText().toString().trim();
                        String id = reference.push().getKey();

                        if (TextUtils.isEmpty(nDescription)) {
                            nDescription = "Auto Generated Description!"; //if the description is empty
                        }
                        if (TextUtils.isEmpty(nBatch)) {
                            int x = (int) (Math.random() * 1000);
                            nBatch = Integer.toString(x);
                        }
                        if (TextUtils.isEmpty(nTime)) {
                            nTime = "00:00";  //if the time field is empty
                        }
                        if (TextUtils.isEmpty(nClass)) {
                            name.setError("Class name is required");  //Validate whether Class name field is not empty
                            return;
                        } else {
                            saveloader.setMessage("Saving the new class");
                            saveloader.setCanceledOnTouchOutside(false);
                            saveloader.show();
                            classModel cmodel = new classModel(nClass, nDescription, nBatch, nTime, id);
                            reference.child(id).setValue(cmodel).addOnCompleteListener(new OnCompleteListener<Void>() {   //firebase database reference to add values
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(classView.this, "Class Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        saveloader.dismiss();
                                    } else {
                                        String error = task.getException().toString();
                                        Toast.makeText(classView.this, "Failed : " + error, Toast.LENGTH_SHORT).show();
                                        saveloader.dismiss();
                                    }
                                }
                            });

                        }

                        dialog.dismiss();
                    }
                });
            }
        });

    }



    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);

    }



}






