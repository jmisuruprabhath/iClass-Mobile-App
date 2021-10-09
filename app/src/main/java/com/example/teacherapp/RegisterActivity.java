package com.example.teacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText RegistrationEmail, RegistrationPassword, RegistrationMobile;
    private Button RegistrationButton;
    private TextView RegistrationPageQuestion;
    private ProgressDialog loader;
    ImageView imageView;

    private DatabaseReference reference;
    private String onlineUserID;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        // Back button
        imageView = findViewById(R.id.registerback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("user").child(onlineUserID);

        loader = new ProgressDialog(this);

        RegistrationEmail = findViewById(R.id.RegistrationEmail);
        RegistrationPassword = findViewById(R.id.RegistrationPassword);
        RegistrationButton = findViewById(R.id.RegistrationButton);
        RegistrationPageQuestion = findViewById(R.id.RegistrationPageQuestion);
        RegistrationMobile = findViewById(R.id.RegistrationMobile);

        RegistrationPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = RegistrationEmail.getText().toString().trim();
                String password = RegistrationPassword.getText().toString().trim();
                String mobile = RegistrationMobile.getText().toString().trim();
                String id = reference.push().getKey();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(TextUtils.isEmpty(email)) {
                    RegistrationEmail.setError("Email is required");
                    return;
                }

                if (!email.matches(emailPattern))
                {
                    RegistrationEmail.setError("Invalid email address");
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    RegistrationPassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6) {
                    RegistrationPassword.setError("Minimum password length should be 6 characters");
                    RegistrationPassword.requestFocus();
                }

                if(TextUtils.isEmpty(mobile)) {
                    RegistrationMobile.setError("Phone number is required");
                    return;
                }

                else {
                    loader.setMessage("Registration in progress...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    User user = new User(email, mobile, id);
                    reference.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                            else {
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Failed : " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                            else{
                                String error = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Registration Failed : " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }

                        }
                    });
                }

                return;
            }
        });


    }

}