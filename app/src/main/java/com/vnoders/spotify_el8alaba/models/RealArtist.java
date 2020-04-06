package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

public class RealArtist {

    @SerializedName("userInfo")
    private RealUserInfo mUserInfo;


    public RealUserInfo getUserInfo() {
        return this.mUserInfo;
    }
}
