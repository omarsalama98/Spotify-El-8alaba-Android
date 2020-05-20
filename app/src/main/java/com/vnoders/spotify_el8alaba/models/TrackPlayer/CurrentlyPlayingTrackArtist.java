package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingTrackArtist {

    @SerializedName("userInfo")
    private CurrentlyPlayingTrackUserInfo mUserInfo;

    public CurrentlyPlayingTrackArtist(CurrentlyPlayingTrackUserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    public CurrentlyPlayingTrackUserInfo getUserInfo() {
        return this.mUserInfo;
    }
}
