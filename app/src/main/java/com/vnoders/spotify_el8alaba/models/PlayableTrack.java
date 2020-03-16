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

    private boolean isPlaying = false;


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
}
