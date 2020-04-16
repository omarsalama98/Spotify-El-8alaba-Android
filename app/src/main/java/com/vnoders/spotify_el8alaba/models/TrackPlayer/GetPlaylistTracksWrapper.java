package com.vnoders.spotify_el8alaba.models.TrackPlayer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlaylistTracksWrapper {

    @SerializedName("items")
    private List<GetPlaylistItem> mItems;

    public List<GetPlaylistItem> getItems() {
        return this.mItems;
    }
}
