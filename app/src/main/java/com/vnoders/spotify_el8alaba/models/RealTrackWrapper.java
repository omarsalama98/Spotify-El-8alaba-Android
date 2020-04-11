package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Wrapping the track object because it returns this way from backend
 */
public class RealTrackWrapper {

    // track object returned in this parameter
    @SerializedName("track")
    private RealTrack mCurrentTrack;

    /**
     * Constructor to make it for testing
     * @param currentTrack object containing info about track
     */
    public RealTrackWrapper(RealTrack currentTrack) {
        this.mCurrentTrack = currentTrack;
    }

    /**
     * @return current track object
     */
    public RealTrack getCurrentTrack() {
        return this.mCurrentTrack;
    }
}
