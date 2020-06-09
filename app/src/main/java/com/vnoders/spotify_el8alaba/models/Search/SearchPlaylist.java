package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import java.util.List;

public class SearchPlaylist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<TrackImage> images = null;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}