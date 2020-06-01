package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * Class to turn the JSON from the get track endpoint of backend to POJO
 */
public class GetTrack {


    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("duration_ms")
    @Expose
    private double mDuration;

    @SerializedName("artists")
    @Expose
    private List<String> mArtists;

    @SerializedName("album")
    @Expose
    private String mAlbumId;

    @SerializedName("uri")
    @Expose
    private String mUri;

    @SerializedName("href")
    @Expose
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
