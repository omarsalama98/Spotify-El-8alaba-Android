package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class GetPlaylistArtist {

    @SerializedName("id")
    private String mId;

    public String getId() {
        return this.mId;
    }

}
