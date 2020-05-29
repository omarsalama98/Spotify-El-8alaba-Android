package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * To turn the JSON to POJO from backend API
 */
public class CurrentlyPlayingAlbum {

    @SerializedName("images")
    @Expose
    private List<CurrentlyPlayingImage> mImages;

    @SerializedName("_id")
    @Expose
    private String mId;

    public List<CurrentlyPlayingImage> getImages() {
        return this.mImages;
    }

    public String getId() {
        return this.mId;
    }
}
