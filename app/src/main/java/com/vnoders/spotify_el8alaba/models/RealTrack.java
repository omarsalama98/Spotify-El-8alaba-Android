package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RealTrack {

    @SerializedName("_id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("duration_ms")
    private int mDuration;

    @SerializedName("artists")
    private List<RealArtist> mArtists;

    private boolean mHasNext = false;
    private boolean mHasPrev = false;
    private boolean mIsPlaying = false;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public List<RealArtist> getArtists() {
        return this.mArtists;
    }

    public boolean getHasNext() {
        return this.mHasNext;
    }

    public boolean getHasPrev() {
        return this.mHasPrev;
    }

    public boolean getIsPlaying() {
        return this.mIsPlaying;
    }

    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    public void setHasPrev(boolean hasPrev) {
        this.mHasPrev = hasPrev;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.mIsPlaying = isPlaying;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }
}
