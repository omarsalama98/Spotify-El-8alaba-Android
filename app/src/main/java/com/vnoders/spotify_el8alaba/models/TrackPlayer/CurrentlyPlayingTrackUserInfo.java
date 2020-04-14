package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingTrackUserInfo {

    @SerializedName("name")
    private String mName;

    public CurrentlyPlayingTrackUserInfo(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }
}
