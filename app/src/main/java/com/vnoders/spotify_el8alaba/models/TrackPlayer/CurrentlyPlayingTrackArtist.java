package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

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
