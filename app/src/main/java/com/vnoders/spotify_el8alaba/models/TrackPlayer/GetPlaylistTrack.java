package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetPlaylistTrack {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("href")
    @Expose
    private String mHref;

    @SerializedName("duration_ms")
    @Expose
    private double mDuration;

    @SerializedName("artists")
    @Expose
    private List<GetPlaylistArtist> mArtists;

    @SerializedName("album")
    @Expose
    private GetPlaylistAlbum mAlbum;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public String getHref() {
        return this.mHref;
    }

    public int getDuration() {
        return ((int)this.mDuration);
    }

    public List<GetPlaylistArtist> getArtists() {
        return this.mArtists;
    }

    public GetPlaylistAlbum getAlbum() {
        return this.mAlbum;
    }
}
