package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ali Adel
 * Info of user object returned from backend
 */
public class RealUserInfo {

    // name of user
    @SerializedName("name")
    private String mName;

    /**
     * Constructor for making object for testing
     * @param name of user
     */
    public RealUserInfo(String name) {
        this.mName = name;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return this.mName;
    }
}
