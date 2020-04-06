package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

public class RealUserInfo {

    @SerializedName("name")
    private String mName;

    public String getName() {
        return this.mName;
    }
}
