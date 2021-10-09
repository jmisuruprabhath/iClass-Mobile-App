package com.example.teacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class QRActivity extends AppCompatActivity {

    // Variables
    public static final int CAMERA_PERMISSION_CODE = 100;

    //Widgets
    private Button camera;
    private Button generate;
    private Button scan;
    ImageView myImage, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);

        camera = findViewById(R.id.camera);
        generate = findViewById(R.id.generate);
        scan = findViewById(R.id.scanqr);
        myImage = findViewById(R.id.myImage);
        backBtn = findViewById(R.id.todoback);
        myImage.setAlpha(0.5f);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRActivity.this, ScanQRActivity.class);
                startActivity(intent);
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRActivity.this, GenerateQRCode.class);
                startActivity(intent);
            }
        });
    }

    public void checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(QRActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRActivity.this, new String[] {permission}, requestCode);
        }
        else{
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
