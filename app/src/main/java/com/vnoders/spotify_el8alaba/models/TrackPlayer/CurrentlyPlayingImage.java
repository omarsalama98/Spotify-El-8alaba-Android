package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingImage {

    @SerializedName("url")
    private String mUrl;

    public String getUrl() {
        return this.mUrl;
    }

}
