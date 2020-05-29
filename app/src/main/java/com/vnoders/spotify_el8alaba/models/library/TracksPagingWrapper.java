package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class TracksPagingWrapper {

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("items")
    @Expose
    private List<TrackItem> trackItems = null;

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("offset")
    @Expose
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TracksPagingWrapper wrapper = (TracksPagingWrapper) obj;
        return limit == wrapper.limit &&
                offset == wrapper.offset &&
                Objects.equals(href, wrapper.href) &&
                Objects.equals(trackItems, wrapper.trackItems);
    }

}
