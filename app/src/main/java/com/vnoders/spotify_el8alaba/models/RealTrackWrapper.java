package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

public class RealTrackWrapper {

    @SerializedName("track")
    private RealTrack mCurrentTrack;

    public RealTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }
}
