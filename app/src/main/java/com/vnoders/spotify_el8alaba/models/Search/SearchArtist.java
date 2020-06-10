package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.library.ArtistUserInfo;
import java.util.List;
import java.util.Objects;

public class SearchArtist {

    @SerializedName(value = "image", alternate = "images")
    @Expose
    private List<TrackImage> images = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("userInfo")
    @Expose
    private ArtistUserInfo userInfo;

    @SerializedName("id")
    @Expose
    private String id;

    public SearchArtist(String name, ArtistUserInfo userInfo, String id) {
        this.userInfo = userInfo;
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
        return userInfo != null ? userInfo.getId() : id;
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

    public void toggleSelection() {
        isSelected = !isSelected;
    }

    public ArtistUserInfo getUserInfo() {
        return userInfo;
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
                Objects.equals(getId(), that.getId());
    }

}