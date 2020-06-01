package com.vnoders.spotify_el8alaba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
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
                String tokenVal=sharedPreferences.getString("token","token not found");
                if (!tokenVal.equals("")&&!tokenVal.equals("token not found")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    // set the token that was in the preferences before to retrofit client
                    RetrofitClient.getInstance().setToken(tokenVal);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, FirstScreen.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        Handler handler = new Handler();
        int SPLASH_TIME_OUT = 1500;
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FAIL", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        Log.d("TOKEN", token);

                        // Toast.makeText(SplashActivity.this,token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
