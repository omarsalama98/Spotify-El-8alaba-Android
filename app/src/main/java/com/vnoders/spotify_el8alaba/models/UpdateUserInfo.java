package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mohamed Samy
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
