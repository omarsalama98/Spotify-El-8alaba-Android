package com.vnoders.spotify_el8alaba.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private void showNotification(Map<String, String> data) {
        String title = data.get("title");
        Toast.makeText(this,title,Toast.LENGTH_LONG).show();
        String body = data.get("body");
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.vnoders.spotify_el8alaba.test";
        if(VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            //notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.vnoders.spotify_el8alaba.test";
        Toast.makeText(this,title,Toast.LENGTH_LONG).show();
        if(VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("test description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationBuilder=new Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("INFO");
        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //Toast.makeText(MyFirebaseMessagingService.this,"DELIVERED",Toast.LENGTH_LONG).show();
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().isEmpty()) {
            showNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        } else {
            showNotification(remoteMessage.getData());
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("EL TOKEN",s);
        super.onNewToken(s);

    }
}
