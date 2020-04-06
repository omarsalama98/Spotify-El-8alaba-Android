package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingTrack {

    @SerializedName("currentlyPlaying")
    private RealTrackWrapper mCurrentTrackWrapper;

    public RealTrackWrapper getCurrentTrackWrapper() {
        return this.mCurrentTrackWrapper;
    }
}
