package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ali Adel
 * JSON has an array so to read the JSON
 */
public class GetPlaylistTracksWrapper {

    @SerializedName("items")
    @Expose
    private List<GetPlaylistItem> mItems;

    public List<GetPlaylistItem> getItems() {
        return this.mItems;
    }
}
