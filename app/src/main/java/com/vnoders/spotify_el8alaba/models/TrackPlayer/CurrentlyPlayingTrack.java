package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingTrack {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("duration_ms")
    @Expose
    private double mDuration;

    @SerializedName("album")
    @Expose
    private CurrentlyPlayingAlbum mAlbum;

    @SerializedName("artists")
    @Expose
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

    public CurrentlyPlayingAlbum getAlbum() {
        return this.mAlbum;
    }

    public List<CurrentlyPlayingTrackArtist> getArtists() {
        return this.mArtists;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }
}
