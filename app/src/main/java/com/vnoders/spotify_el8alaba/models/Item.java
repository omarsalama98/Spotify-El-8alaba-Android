package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.library.AddedBy;
import com.vnoders.spotify_el8alaba.models.library.Track;

public class Item {

    @SerializedName("is_local")
    @Expose
    private Boolean isLocal;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("added_at")
    @Expose
    private String addedAt;
    @SerializedName("added_by")
    @Expose
    private AddedBy addedBy;
    @SerializedName("track")
    @Expose
    private Track track;

    public Boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public AddedBy getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(AddedBy addedBy) {
        this.addedBy = addedBy;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

}