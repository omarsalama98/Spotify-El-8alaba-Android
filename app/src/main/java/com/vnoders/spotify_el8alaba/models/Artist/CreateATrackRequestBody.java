package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.SerializedName;

public class CreateATrackRequestBody {

    @SerializedName("name")
    private String trackName;

    @SerializedName("album")
    private String albumId;

    @SerializedName("disc_number")
    private Integer discNum;

    @SerializedName("duration_ms")
    private Integer duration;

    @SerializedName("explicit")
    private Boolean isExplicit;

    public CreateATrackRequestBody(String trackName, String albumId, Boolean isExplicit) {
        this.trackName = trackName;
        this.albumId = albumId;
        this.isExplicit = isExplicit;
        this.discNum = 1;
        this.duration = 5000;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
