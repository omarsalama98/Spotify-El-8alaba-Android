package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import java.util.List;

public class AlbumTracks {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private List<SearchTrack> items = null;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<SearchTrack> getTracks() {
        return items;
    }

    public void setTracks(List<SearchTrack> items) {
        this.items = items;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}