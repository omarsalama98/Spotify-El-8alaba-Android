package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class UpdateAlbumNameRequestBody {

    @SerializedName("name")
    @Expose
    private String albumName;

    public UpdateAlbumNameRequestBody(String albumName) {
        this.albumName = albumName;
    }
}
