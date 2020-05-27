package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;


/**
 * @author Ali Adel Class to turn JSON into POJO received from backend
 */
public class LikedTrackModel {

    @SerializedName("id")
    private String mId;

    public String getId() {
        return this.mId;
    }
}
