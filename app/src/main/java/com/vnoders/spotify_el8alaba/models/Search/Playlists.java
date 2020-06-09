package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Playlists {

    @SerializedName("playlists")
    @Expose
    private List<SearchPlaylist> playlists = null;

    public List<SearchPlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<SearchPlaylist> playlists) {
        this.playlists = playlists;
    }

}
