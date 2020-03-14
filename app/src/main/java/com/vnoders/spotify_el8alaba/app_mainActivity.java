package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class app_mainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent first_screen=new Intent(app_mainActivity.this,firstScreen.class);
                startActivity(first_screen);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

}
