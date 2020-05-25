package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.vnoders.spotify_el8alaba.R;

/**
 * Implementation of App Widget functionality.
 */
public class SpotifyEl8alabaWidget extends AppWidgetProvider {

    /**
     * Creates all on click listeners and sets their intent
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // fetch the remote view to handle it
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.spotify_el8alaba_widget);

        // makes the play button launch service with play code
        Intent intent = new Intent(context.getApplicationContext(),
                MediaPlaybackService.class).setAction(MediaPlaybackService.PLAY_ID);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play, pendingIntent);

        // makes the pause button launch service with pause code
        intent = new Intent(context.getApplicationContext(),
                MediaPlaybackService.class).setAction(MediaPlaybackService.PAUSE_ID);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_pause, pendingIntent);

        // makes the skip next button launch service with skip next code
        intent = new Intent(context.getApplicationContext(),
                MediaPlaybackService.class).setAction(MediaPlaybackService.SKIP_BACKWARD);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_skip_backward, pendingIntent);

        // makes the skip previous button launch service with skip previous code
        intent = new Intent(context.getApplicationContext(),
                MediaPlaybackService.class).setAction(MediaPlaybackService.SKIP_FORWARD);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_skip_forward, pendingIntent);

        // makes the entire widget launch application
        intent = new Intent(context.getApplicationContext(),
                MediaPlaybackService.class).setAction(MediaPlaybackService.OPEN_APP);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

