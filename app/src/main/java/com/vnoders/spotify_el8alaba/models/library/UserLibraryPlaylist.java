package com.vnoders.spotify_el8alaba.models.library;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserLibraryPlaylist {

    @SerializedName("href")
    private String href;
    
    @SerializedName("items")
    private List<UserLibraryPlaylistItem> items = null;
    
    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<UserLibraryPlaylistItem> getItems() {
        return items;
    }

    public void setItems(List<UserLibraryPlaylistItem> items) {
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

}
