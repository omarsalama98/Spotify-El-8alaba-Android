package com.vnoders.spotify_el8alaba;

import android.app.Application;
import android.content.res.Resources;

public class App extends Application {

    private static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static App getInstance() {
        return appInstance;
    }

    public String getCurrentUserId() {

        return getSharedPreferences(getString(R.string.access_token_preference),
                MODE_PRIVATE)
                .getString("id", null);
    }

    public static int getDisplayHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

}
