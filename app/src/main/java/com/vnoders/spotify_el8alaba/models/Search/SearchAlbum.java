package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import java.util.List;

public class SearchAlbum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private List<TrackImage> images = null;
    @SerializedName("id")
    @Expose
    private String id;

    public SearchAlbum(String name,
            List<TrackImage> images, String id) {
        this.name = name;
        this.images = images;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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