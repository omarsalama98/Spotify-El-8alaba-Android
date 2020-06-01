package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * get user status from backend to see if is premium or not
 */
public class GetUserStatus {

    @SerializedName("product")
    @Expose
    private String mUserType;

    public String getUserType() {
        return this.mUserType;
    }
}
