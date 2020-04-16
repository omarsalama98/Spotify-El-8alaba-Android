package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentlyPlayingAlbum {

    @SerializedName("images")
    private List<CurrentlyPlayingImage> mImages;

    public List<CurrentlyPlayingImage> getImages() {
        return this.mImages;
    }
}
