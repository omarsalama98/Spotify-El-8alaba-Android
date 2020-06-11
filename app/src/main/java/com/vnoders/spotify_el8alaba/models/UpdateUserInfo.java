package com.vnoders.spotify_el8alaba.models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class UpdateUserInfo {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SerializedName("name")
    @Expose
    private String userName;

}
