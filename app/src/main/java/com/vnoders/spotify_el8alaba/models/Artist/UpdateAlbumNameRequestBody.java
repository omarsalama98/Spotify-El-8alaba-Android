package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAlbumNameRequestBody {

    @SerializedName("name")
    @Expose
    private String albumName;

    public UpdateAlbumNameRequestBody(String albumName) {
        this.albumName = albumName;
    }
}
