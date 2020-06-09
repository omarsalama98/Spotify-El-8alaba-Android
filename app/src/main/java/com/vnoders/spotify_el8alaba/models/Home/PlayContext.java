package com.vnoders.spotify_el8alaba.models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayContext {

    @SerializedName("uri")
    @Expose
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}