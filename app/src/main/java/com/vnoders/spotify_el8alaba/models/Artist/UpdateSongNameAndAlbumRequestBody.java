package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
