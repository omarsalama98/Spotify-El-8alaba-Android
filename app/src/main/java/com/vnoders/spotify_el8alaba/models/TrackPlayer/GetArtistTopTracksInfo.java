package com.vnoders.spotify_el8alaba.models.TrackPlayer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 */

/**
 * To turn the JSON to POJO from backend API
 */
public class GetArtistTopTracksInfo {

    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

}
