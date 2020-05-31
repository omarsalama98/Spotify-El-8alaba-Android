package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Class to send the track context in a post request to backend API
 */
public class TrackContext {

    @SerializedName("type")
    @Expose
    private String mType;

    @SerializedName("uri")
    @Expose
    private String mUri;

    public String getType() {
        return this.mType;
    }

    public String getUri() {
        return this.mUri;
    }
}
