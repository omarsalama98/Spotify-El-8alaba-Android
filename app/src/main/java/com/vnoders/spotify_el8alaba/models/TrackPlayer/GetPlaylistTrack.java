package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetPlaylistTrack {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("duration_ms")
    private double mDuration;

    @SerializedName("artists")
    private List<GetPlaylistArtist> mArtists;

    @SerializedName("album")
    private GetPlaylistAlbum mAlbum;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
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
