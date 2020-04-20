package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class CurrentlyPlayingTrackWrapper {

    @SerializedName("track")
    private CurrentlyPlayingTrack mCurrentTrack;

    @SerializedName("progress_ms")
    private double mTrackProgress;

    @SerializedName("context")
    private TrackContext mContext;

    public CurrentlyPlayingTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }

    public int getTrackProgress() {
        return ((int)this.mTrackProgress);
    }

    public TrackContext getTrackContext() {
        return this.mContext;
    }
}
