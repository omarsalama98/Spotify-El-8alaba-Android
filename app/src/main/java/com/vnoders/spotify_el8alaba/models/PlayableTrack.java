package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;


/**
 * for json parsing when getting tracks
 */
public class PlayableTrack {

    @SerializedName("name")
    private String name;

    @SerializedName("preview_url")
    private String preview_url;

    @SerializedName("album")
    private PlayableAlbum album;

    @SerializedName("duration_ms")
    private int mDuration;

    // know if currently playing or not
    private boolean isPlaying = false;
    // know if there is next track or not
    private boolean mHasNext = true;
    // know if there is previous track or not
    private boolean mHasPrev = true;


    public String getName() {
        return name;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public PlayableAlbum getAlbum() {
        return album;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public boolean getHasNext() {
        return mHasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.mHasNext = hasNext;
    }

    public boolean getHasPrev() {
        return mHasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.mHasPrev = hasPrev;
    }

}
