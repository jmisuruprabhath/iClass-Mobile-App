package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH = 3300;

    Animation topAnim, bottomAnim;
    ImageView imageView,iclass;
    TextView textView,dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imageView = findViewById(R.id.imageViewSp);
        iclass = findViewById(R.id.iclassim);
        textView = findViewById(R.id.textsp);
        dev = findViewById(R.id.devtxt);

        imageView.setAnimation(topAnim);
        iclass.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        },SPLASH);
    }
}