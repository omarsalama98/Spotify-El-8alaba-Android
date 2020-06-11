package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class UpdateSongNameAndAlbumRequestBody {

    @SerializedName("name")
    @Expose
    private String songName;

    @SerializedName("album")
    @Expose
    private String albumId;

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
