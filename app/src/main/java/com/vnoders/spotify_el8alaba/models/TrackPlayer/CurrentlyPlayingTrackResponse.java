package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingTrackResponse {

    @SerializedName("currentlyPlaying")
    private CurrentlyPlayingTrackWrapper mCurrentTrackWrapper;

    public CurrentlyPlayingTrackWrapper getCurrentTrackWrapper() {
        return this.mCurrentTrackWrapper;
    }
}
