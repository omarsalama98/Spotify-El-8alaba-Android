package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingTrackWrapper {

    @SerializedName("track")
    private CurrentlyPlayingTrack mCurrentTrack;

    @SerializedName("progress_ms")
    private double mTrackProgress;

    @SerializedName("context")
    private TrackContext mContext;

    @SerializedName("repeat_state")
    private boolean mRepeat;

    @SerializedName("shuffle_state")
    private boolean mShuffle;

    public CurrentlyPlayingTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }

    public int getTrackProgress() {
        return ((int)this.mTrackProgress);
    }

    public TrackContext getTrackContext() {
        return this.mContext;
    }

    public boolean getRepeat() {
        return this.mRepeat;
    }

    public boolean getShuffle() {
        return this.mShuffle;
    }
}
