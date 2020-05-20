package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;


/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetPlaylist {

    @SerializedName("name")
    private String mName;

    @SerializedName("tracks")
    private GetPlaylistTracksWrapper mTracks;

    @SerializedName("uri")
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