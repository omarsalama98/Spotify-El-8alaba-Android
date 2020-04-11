package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Artist object received from backend
 */
public class RealArtist {

    // aritst's name
    @SerializedName("userInfo")
    private RealUserInfo mUserInfo;

    /**
     * Contstructor to fill aritst info for testing
     * @param userInfo userInfo of artist containing his/her name
     */
    public RealArtist(RealUserInfo userInfo) {
        this.mUserInfo = userInfo;
    }

    /**
     * @return UserInfo object of artist containing his/her name
     */
    public RealUserInfo getUserInfo() {
        return this.mUserInfo;
    }
}
