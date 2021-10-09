package com.example.teacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText resetemail;
    private Button resetPasswordBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetemail = findViewById(R.id.resetpassword);
        resetPasswordBtn = findViewById(R.id.resetButton);
        progressBar = findViewById(R.id.resetProgressBar);
        mAuth = FirebaseAuth.getInstance();

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        // Back button
        imageView = findViewById(R.id.resetback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void resetPassword() {
        String email = resetemail.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern))
        {
            resetemail.setError("Invalid email address");
            return;
        }

        if(email.isEmpty()) {
            resetemail.setError("Email is required");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Check your email to reset the password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, "Something wrong happened! Try Again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}