package com.vnoders.spotify_el8alaba.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vnoders.spotify_el8alaba.R;
import java.util.Map;
import java.util.Random;

/**
 *
 * This class extends {@link FirebaseMessagingService} to handle the coming notifications and render
 * them correctly while the application in foreground or back ground
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private SharedPreferences notificationToken;

    private void showNotification(Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.vnoders.spotify_el8alaba.test";
        if(VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.spotify)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.vnoders.spotify_el8alaba.test";
        if(VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.spotify)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //Toast.makeText(MyFirebaseMessagingService.this,"DELIVERED",Toast.LENGTH_LONG).show();
        super.onMessageReceived(remoteMessage);
        //if (remoteMessage.getData().isEmpty()) {
            showNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());

//        } else {
//            showNotification(remoteMessage.getData());
//        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        notificationToken=getSharedPreferences("NOTIFICATION_TOKEN",Context.MODE_PRIVATE);
        Editor editor=notificationToken.edit();
        editor.putString("notification_token",s);
        editor.putString("notSent","true");
        editor.apply();
        Log.d("!!!!!",s);
        super.onNewToken(s);
    }
}
