package com.vnoders.spotify_el8alaba.models.TrackPlayer;


import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Class to get the external url to share
 */
public class ShareTrackResponse {

    @SerializedName("link")
    private String mShareUrl;

    public String getShareUrl() {
        return this.mShareUrl;
    }
}
