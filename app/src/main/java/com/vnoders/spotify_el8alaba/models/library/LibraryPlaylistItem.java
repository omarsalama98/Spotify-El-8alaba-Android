package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class LibraryPlaylistItem {

    @SerializedName("collaborative")
    private boolean collaborative;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private List<TrackImage> images = null;

    @SerializedName("name")
    private String name;

    @SerializedName("owner")
    private Owner owner;

    @SerializedName("public")
    private boolean isPublic;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

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

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean _public) {
        this.isPublic = _public;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LibraryPlaylistItem)) {
            return false;
        }
        LibraryPlaylistItem that = (LibraryPlaylistItem) obj;

        return collaborative == that.collaborative &&
                isPublic == that.isPublic &&
                externalUrls.equals(that.externalUrls) &&
                href.equals(that.href) &&
                id.equals(that.id) &&
                images.equals(that.images) &&
                name.equals(that.name) &&
                owner.equals(that.owner) &&
                type.equals(that.type) &&
                uri.equals(that.uri);
    }

}
