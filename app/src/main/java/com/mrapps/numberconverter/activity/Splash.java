package com.mrapps.numberconverter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mrapps.numberconverter.utils.FirstLaunch;
import com.mrapps.numberconverter.R;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (new FirstLaunch(Splash.this).isFirstTimeLaunch()) {
                    startActivity(new Intent(Splash.this, Intro.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }

            }
        }, 2500);


    }
}