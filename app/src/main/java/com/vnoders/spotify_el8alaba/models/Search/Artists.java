package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Artists {

    @SerializedName("artists")
    @Expose
    private List<SearchArtist> artists = null;

    public List<SearchArtist> getArtists() {
        return artists;
    }

}
