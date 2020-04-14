package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingTrackWrapper {

    @SerializedName("track")
    private CurrentlyPlayingTrack mCurrentTrack;

    public CurrentlyPlayingTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }
}
