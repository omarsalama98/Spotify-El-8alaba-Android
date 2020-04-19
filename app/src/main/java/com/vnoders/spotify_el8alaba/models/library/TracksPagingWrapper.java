package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class TracksPagingWrapper {

    @SerializedName("href")
    private String href;

    @SerializedName("items")
    private List<TrackItem> trackItems = null;

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<TrackItem> getTrackItems() {
        return trackItems;
    }

    public void setTrackItems(List<TrackItem> trackItems) {
        this.trackItems = trackItems;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
