package com.vnoders.spotify_el8alaba;
import android.content.SharedPreferences;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Remove the windows's background color to reduce overdraw because it is already
        // being drawn by other views
        getWindow().setBackgroundDrawable(null);

        final Runnable runnable = new Runnable() {
            SharedPreferences sharedPreferences;

            @Override
            public void run() {
                sharedPreferences =getSharedPreferences(
                        getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(sharedPreferences.getString("token","token not found")!=""){
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, FirstScreen.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        Handler handler = new Handler();
        int SPLASH_TIME_OUT = 1500;
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }
}
