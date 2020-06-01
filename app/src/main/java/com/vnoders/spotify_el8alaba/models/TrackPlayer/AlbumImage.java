package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class AlbumImage {

    @SerializedName("url")
    @Expose
    private String mUrl;

    public String getUrl() {
        return this.mUrl;
    }
}
