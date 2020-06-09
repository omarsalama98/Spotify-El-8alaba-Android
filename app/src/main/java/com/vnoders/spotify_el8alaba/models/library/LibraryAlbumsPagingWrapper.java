package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class LibraryAlbumsPagingWrapper {

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("items")
    @Expose
    private List<LibraryAlbumItem> items = null;

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("offset")
    @Expose
    private int offset;

    @SerializedName("total")
    @Expose
    private int total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<LibraryAlbumItem> getItems() {
        return items;
    }

    public void setItems(List<LibraryAlbumItem> items) {
        this.items = items;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryAlbumsPagingWrapper that = (LibraryAlbumsPagingWrapper) obj;
        return total == that.total &&
                Objects.equals(href, that.href) &&
                Objects.equals(items, that.items);
    }

}
