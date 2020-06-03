package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetArtistTrack {

    @SerializedName("artists")
    @Expose
    List<GetArtistTopTracksInfo> mArtists;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("duration_ms")
    @Expose
    private double mDuration;

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("album")
    @Expose
    private GetArtistAlbum mAlbum;

    public List<GetArtistTopTracksInfo> getArtists() {
        return this.mArtists;
    }

    public String getName() {
        return this.mName;
    }

    public int getDuration() {
        return ((int)this.mDuration);
    }

    public String getId() {
        return this.mId;
    }

    public GetArtistAlbum getAlbum() {
        return this.mAlbum;
    }
}
