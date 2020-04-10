package com.vnoders.spotify_el8alaba;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, FirstScreen.class);
                startActivity(intent);
                finish();
            }
        };
        Handler handler = new Handler();
        int SPLASH_TIME_OUT = 1500;
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }
}
