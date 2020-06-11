package com.vnoders.spotify_el8alaba.models.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class NotificationToken {
    @SerializedName("token")
    @Expose
    private String token;
    public NotificationToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
