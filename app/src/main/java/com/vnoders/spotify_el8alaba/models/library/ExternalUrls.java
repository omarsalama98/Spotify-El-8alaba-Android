
package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class ExternalUrls {

    @SerializedName("spotify")
    private String spotify;

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

}
