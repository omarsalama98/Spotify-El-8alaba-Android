package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingAlbum {

    @SerializedName("images")
    private List<CurrentlyPlayingImage> mImages;

    @SerializedName("_id")
    private String mId;

    public List<CurrentlyPlayingImage> getImages() {
        return this.mImages;
    }

    public String getId() {
        return this.mId;
    }
}
