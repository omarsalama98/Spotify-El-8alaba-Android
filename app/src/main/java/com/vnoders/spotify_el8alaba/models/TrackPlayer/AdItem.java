package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * Used to get an ad from backend and convert JSON to POJO
 */
public class AdItem {

    @SerializedName("adText")
    private String mText;

    @SerializedName("images")
    private List<AlbumImage> mImages;

    public String getText() {
        return this.mText;
    }

    public List<AlbumImage> getImages() {
        return this.mImages;
    }
}
