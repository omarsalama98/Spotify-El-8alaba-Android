package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;

public class app_mainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent first_screen = new Intent(app_mainActivity.this, FirstScreen.class);
                startActivity(first_screen);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
