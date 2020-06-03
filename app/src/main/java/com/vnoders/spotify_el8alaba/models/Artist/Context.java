package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Context {

    @SerializedName("external_urls")
    @Expose
    private List<Object> externalUrls = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("href")
    @Expose
    private String href;

    public List<Object> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(List<Object> externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}