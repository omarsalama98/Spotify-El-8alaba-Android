package com.vnoders.spotify_el8alaba.models.Home;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.Tracks;
import com.vnoders.spotify_el8alaba.models.library.ExternalUrls;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class HomePlaylist {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    @SerializedName("collaborative")
    @Expose
    private boolean collaborative;

    @SerializedName("public")
    @Expose
    private boolean isPublic;

    @SerializedName("followers")
    @Expose
    private int followers;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("owner")
    @Expose
    private String owner;

    @SerializedName("images")
    @Expose
    private List<TrackImage> images = null;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("external_urls")
    @Expose
    private ExternalUrls externalUrls;

    @SerializedName("id")
    @Expose
    private String id;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean _public) {
        this.isPublic = _public;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
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

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
