package com.vnoders.spotify_el8alaba.models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

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