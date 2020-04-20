package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

public class TrackContext {

    @SerializedName("type")
    private String mType;

    @SerializedName("uri")
    private String mUri;

    public String getType() {
        return this.mType;
    }

    public String getUri() {
        return this.mUri;
    }

}
