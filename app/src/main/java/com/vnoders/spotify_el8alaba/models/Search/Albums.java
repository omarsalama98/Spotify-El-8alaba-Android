package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Albums {

    @SerializedName("albums")
    @Expose
    private List<SearchAlbum> albums = null;

    public List<SearchAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(List<SearchAlbum> albums) {
        this.albums = albums;
    }

}