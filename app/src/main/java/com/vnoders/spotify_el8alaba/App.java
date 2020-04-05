package com.vnoders.spotify_el8alaba;

import android.app.Application;

public class App extends Application {

    private static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
    }

    public static Application getInstance() {
        return appInstance;
    }
}
