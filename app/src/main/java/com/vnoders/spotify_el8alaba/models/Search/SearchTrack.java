package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchTrack {

    @SerializedName("artists")
    @Expose
    private List<String> artists = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("explicit")
    @Expose
    private Boolean explicit;
    @SerializedName(value = "id", alternate = {"_id"})
    @Expose
    private String id;

    public List<String> getArtists() {
        return artists;
    }

    public String getName() {
        return name;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public String getId() {
        return id;
    }

    public String getAlbum() {
        return album;
    }
}

