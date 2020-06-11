package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Artists {

    @SerializedName("artists")
    @Expose
    private List<SearchArtist> artists = null;

    public List<SearchArtist> getArtists() {
        return artists;
    }

}
