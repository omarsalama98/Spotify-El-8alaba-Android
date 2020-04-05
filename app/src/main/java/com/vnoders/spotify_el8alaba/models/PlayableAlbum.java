package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * for json parsing when getting tracks to get the image and artist
 */
public class PlayableAlbum {

    @SerializedName("artists")
    private List<PlayableArtist> artists;

    @SerializedName("images")
    private List<TrackImage> images;

    public List<PlayableArtist> getArtists() {
        return artists;
    }


    public List<TrackImage> getImages() {
        return images;
    }
}
