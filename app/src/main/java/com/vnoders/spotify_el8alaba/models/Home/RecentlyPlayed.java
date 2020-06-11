package com.vnoders.spotify_el8alaba.models.Home;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class RecentlyPlayed {

    @SerializedName("playContexts")
    @Expose
    private List<PlayContext> playContexts = null;

    public List<PlayContext> getPlayContexts() {
        return playContexts;
    }

    public void setPlayContexts(List<PlayContext> playContexts) {
        this.playContexts = playContexts;
    }

}