
package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    @SerializedName("display_name")
    private String name;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
