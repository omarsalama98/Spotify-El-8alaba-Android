package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;


/**
 * for json parsing when getting tracks to get the name of artist
 */
public class PlayableArtist {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
