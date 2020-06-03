package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayContext {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("lastPlayedTime")
    @Expose
    private Integer lastPlayedTime;
    @SerializedName("lastPlayedTrackUri")
    @Expose
    private String lastPlayedTrackUri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getLastPlayedTime() {
        return lastPlayedTime;
    }

    public void setLastPlayedTime(Integer lastPlayedTime) {
        this.lastPlayedTime = lastPlayedTime;
    }

    public String getLastPlayedTrackUri() {
        return lastPlayedTrackUri;
    }

    public void setLastPlayedTrackUri(String lastPlayedTrackUri) {
        this.lastPlayedTrackUri = lastPlayedTrackUri;
    }

}