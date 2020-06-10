package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import java.util.List;
import java.util.Objects;

public class SearchArtist {

    @SerializedName("image")
    @Expose
    private List<TrackImage> images = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public SearchArtist(List<TrackImage> images, String name, String id) {
        this.images = images;
        this.name = name;
        this.id = id;
    }

    private boolean isSelected = false;

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void toggleSelection(){
        isSelected = !isSelected;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SearchArtist that = (SearchArtist) obj;
        return Objects.equals(name, that.name) &&
                Objects.equals(id, that.id);
    }

}