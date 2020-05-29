package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.SerializedName;

public class UpdateSongNameAndAlbumRequestBody {

    @SerializedName("name")
    private String songName;

    @SerializedName("album")
    private String albumId;

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
