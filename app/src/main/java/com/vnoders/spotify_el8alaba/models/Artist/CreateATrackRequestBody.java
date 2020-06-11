package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class CreateATrackRequestBody {

    @SerializedName("name")
    @Expose
    private String trackName;

    @SerializedName("album")
    @Expose
    private String albumId;

    @SerializedName("disc_number")
    @Expose
    private Integer discNum;

    @SerializedName("duration_ms")
    @Expose
    private Integer duration;

    @SerializedName("explicit")
    @Expose
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
