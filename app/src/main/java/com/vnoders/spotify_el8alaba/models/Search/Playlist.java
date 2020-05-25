package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.Tracks;
import com.vnoders.spotify_el8alaba.models.library.ExternalUrls;
import java.util.List;

public class Playlist {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;
    @SerializedName("collaborative")
    @Expose
    private Boolean collaborative;
    @SerializedName("public")
    @Expose
    private Boolean _public;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("collaborators")
    @Expose
    private List<String> collaborators = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<TrackImage> images = null;
    @SerializedName("__v")
    @Expose
    private Integer v;
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

    public Boolean getCollaborative() {
        return collaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        this.collaborative = collaborative;
    }

    public Boolean getPublic() {
        return _public;
    }

    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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