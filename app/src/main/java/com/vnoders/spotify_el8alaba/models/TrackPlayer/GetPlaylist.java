package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetPlaylist {

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("tracks")
    @Expose
    private GetPlaylistTracksWrapper mTracks;

    @SerializedName("uri")
    @Expose
    private String mUri;

    public String getName() {
        return this.mName;
    }

    public GetPlaylistTracksWrapper getTracks() {
        return this.mTracks;
    }

    public String getUri() {
        return this.mUri;
    }
}