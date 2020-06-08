package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchTrack {

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

    private String imageUrl;

    private String artistsNames;

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

    public String getArtistsNames() {
        return artistsNames;
    }

    public void setArtistsNames(String artistsNames) {
        this.artistsNames = artistsNames;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

