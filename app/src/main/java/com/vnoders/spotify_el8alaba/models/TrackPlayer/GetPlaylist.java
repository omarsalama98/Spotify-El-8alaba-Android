package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlaylist {

    @SerializedName("name")
    private String mName;

    @SerializedName("tracks")
    private GetPlaylistTracksWrapper mTracks;

    public String getName() {
        return this.mName;
    }

    public GetPlaylistTracksWrapper getTracks() {
        return this.mTracks;
    }
}