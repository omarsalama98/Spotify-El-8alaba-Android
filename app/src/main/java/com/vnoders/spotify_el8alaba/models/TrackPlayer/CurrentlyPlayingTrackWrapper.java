package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingTrackWrapper {

    @SerializedName("track")
    private CurrentlyPlayingTrack mCurrentTrack;

    @SerializedName("progress_ms")
    private double mTrackProgress;

    public CurrentlyPlayingTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }

    public int getTrackProgress() {
        return ((int)this.mTrackProgress);
    }
}
