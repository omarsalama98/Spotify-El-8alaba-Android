package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;


/**
 * for json parsing when getting tracks to get the image
 */
public class TrackImage {

    @SerializedName("height")
    private int height;

    @SerializedName("width")
    private int width;

    @SerializedName("url")
    private String url;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }
}
