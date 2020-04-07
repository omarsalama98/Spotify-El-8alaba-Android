
package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.SerializedName;

public class TrackItem {

    @SerializedName("is_local")
    private boolean isLocal;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("added_by")
    private AddedBy addedBy;

    @SerializedName("track")
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

}
