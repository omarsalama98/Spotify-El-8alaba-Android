package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class GetPlaylistArtist {

    @SerializedName("id")
    private String mId;

    public String getId() {
        return this.mId;
    }

}
