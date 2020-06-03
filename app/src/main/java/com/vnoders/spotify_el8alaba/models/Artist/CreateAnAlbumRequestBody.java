package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class CreateAnAlbumRequestBody {

    @SerializedName("album_type")
    @Expose
    private String albumType;

    @SerializedName("genres")
    @Expose
    private List<String> genres;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("name")
    @Expose
    private String albumName;

    public CreateAnAlbumRequestBody() {
        this.label = "label";
        this.albumName = "albumName";
        this.albumType = "album";
        this.genres = new ArrayList<>();
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}

