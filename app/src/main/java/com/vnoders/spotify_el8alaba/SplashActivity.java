package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, firstScreen.class);
                startActivity(intent);
            }
        };
        Handler handler = new Handler();
        int SPLASH_TIME_OUT = 1500;
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }
}
