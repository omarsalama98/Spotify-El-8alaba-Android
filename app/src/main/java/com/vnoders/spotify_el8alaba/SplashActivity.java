package com.vnoders.spotify_el8alaba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.vnoders.spotify_el8alaba.models.Notifications.NotificationToken;
import com.vnoders.spotify_el8alaba.repositories.API;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.login.FirstScreen;
import okhttp3.ResponseBody;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences notificationShared;

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
                    AndroidThreeTen.init(getApplication());
                    String loginDate=sharedPreferences.getString("loginDate","noDate");
                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");
                    LocalDateTime checkLoginDate = LocalDateTime.parse(loginDate, formatter);
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration=Duration.between(now,checkLoginDate);
                    long diff=Math.abs(duration.toDays());
                    Log.d("PERIOD",String.valueOf(diff));
                    if(diff>1) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        // set the token that was in the preferences before to retrofit client
                        RetrofitClient.getInstance().setToken(tokenVal);
                        startActivity(intent);
                    }
                    else{
                        notificationShared = getSharedPreferences("NOTIFICATION_TOKEN", Context.MODE_PRIVATE);
                        Editor notificationTokenEditor=notificationShared.edit();
                        notificationTokenEditor.putString("notSent","true");
                        notificationTokenEditor.commit();
                        sharedPreferences =getSharedPreferences(
                                getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
                        SharedPreferences.Editor editorShared = sharedPreferences.edit();
                        editorShared.putString("token", "");
                        editorShared.putString("id","");
                        editorShared.commit();
                        Intent intent = new Intent(SplashActivity.this, FirstScreen.class);
                        startActivity(intent);
                    }
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
