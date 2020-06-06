package com.vnoders.spotify_el8alaba.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.List;

public class UserProfileData {

    public UserProfileData(List<String> following, String type, Integer followers,
            String name, String uri, List<Image> image, String id) {
        this.following = following;
        this.type = type;
        this.followers = followers;
        this.name = name;
        this.uri = uri;
        this.image = image;
        this.id = id;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    @SerializedName("following")
    @Expose
    private List<String> following = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("image")
    @Expose
    private List<Image> image;
    @SerializedName("id")
    @Expose
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }



}
