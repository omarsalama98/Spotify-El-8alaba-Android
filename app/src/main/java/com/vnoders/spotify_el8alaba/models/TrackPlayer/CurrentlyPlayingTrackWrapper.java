package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 */

/**
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingTrackWrapper {

    @SerializedName("track")
    @Expose
    private CurrentlyPlayingTrack mCurrentTrack;

    @SerializedName("progress_ms")
    @Expose
    private double mTrackProgress;

    @SerializedName("context")
    @Expose
    private TrackContext mContext;

    @SerializedName("repeat_state")
    @Expose
    private boolean mRepeat;

    @SerializedName("shuffle_state")
    @Expose
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
