package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.SerializedName;

public class UpdateSongNameRequestBody {

    @SerializedName("name")
    private String songName;

    public void setSongName(String songName) {
        this.songName = songName;
    }

}
