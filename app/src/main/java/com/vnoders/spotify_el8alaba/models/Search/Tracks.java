package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Tracks {

    @SerializedName("tracks")
    @Expose
    private List<SearchTrack> tracks = null;

    public List<SearchTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<SearchTrack> tracks) {
        this.tracks = tracks;
    }

}
