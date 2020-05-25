package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * Class to turn the JSON from the get track endpoint of backend to POJO
 */
public class GetTrack {


    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("duration_ms")
    private double mDuration;

    @SerializedName("artists")
    private List<String> mArtists;

    @SerializedName("album")
    private String mAlbumId;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("href")
    private String mHref;

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

    public String getAlbumId() {
        return this.mAlbumId;
    }

    public String getUri() {
        return this.mUri;
    }

    public String getHref() {
        return this.mHref;
    }
}