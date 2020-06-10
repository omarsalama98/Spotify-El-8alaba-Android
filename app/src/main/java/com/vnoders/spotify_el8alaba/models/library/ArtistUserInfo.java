package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.List;
import java.util.Objects;

public class ArtistUserInfo {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName(value = "image", alternate = "images")
    @Expose
    private List<Image> images;

    public ArtistUserInfo(List<Image> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArtistUserInfo that = (ArtistUserInfo) obj;
        return Objects.equals(id, that.id);
    }

}

