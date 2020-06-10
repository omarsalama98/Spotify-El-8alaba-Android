package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;

/**
 * @author Mohamed Samy
 */
public class FacebookToken {
    @Expose
    private String access_token;

    public FacebookToken(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
