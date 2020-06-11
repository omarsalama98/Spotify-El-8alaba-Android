package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 */

/**
 * To turn the JSON to POJO from backend API
 */
public class GetArtist {

    @SerializedName("name")
    @Expose
    private String mName;

    public GetArtist(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }
}
