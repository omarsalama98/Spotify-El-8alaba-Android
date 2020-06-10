package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Home.HomePlaylist;
import java.util.List;

public class Category {

    @SerializedName("playlists")
    @Expose
    private List<HomePlaylist> playlists = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icons")
    @Expose
    private List<Image> images = null;


    public Category(List<HomePlaylist> playlists, String name) {
        this.playlists = playlists;
        this.name = name;
    }

    public List<HomePlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<HomePlaylist> playlists) {
        this.playlists = playlists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}