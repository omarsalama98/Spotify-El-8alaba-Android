package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetTrack {


    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("duration_ms")
    private double mDuration;

    @SerializedName("artists")
    private List<String> mArtists;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public int getDuration() {
        return ((int)this.mDuration);
    }

    public List<String> getArtists() {
        return this.mArtists;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

}
