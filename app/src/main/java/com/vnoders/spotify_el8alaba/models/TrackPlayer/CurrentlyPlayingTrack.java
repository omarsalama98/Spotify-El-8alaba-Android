package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrentlyPlayingTrack {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("duration_ms")
    private double mDuration;

    @SerializedName("artists")
    private List<CurrentlyPlayingTrackArtist> mArtists;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public int getDuration() {
        return ((int)this.mDuration);
    }

    public List<CurrentlyPlayingTrackArtist> getArtists() {
        return this.mArtists;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }
}
