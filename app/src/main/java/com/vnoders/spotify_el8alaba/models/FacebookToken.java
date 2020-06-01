package com.vnoders.spotify_el8alaba.models;

public class FacebookToken {

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
