package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class TrackItem {

    @SerializedName("is_local")
    @Expose
    private boolean isLocal;

    @SerializedName("added_at")
    @Expose
    private String addedAt;

    @SerializedName("added_by")
    @Expose
    private AddedBy addedBy;

    @SerializedName("track")
    @Expose
    private Track track;

    public boolean isIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackItem trackItem = (TrackItem) obj;
        return isLocal == trackItem.isLocal &&
                Objects.equals(addedBy, trackItem.addedBy) &&
                Objects.equals(track, trackItem.track);
    }

}
