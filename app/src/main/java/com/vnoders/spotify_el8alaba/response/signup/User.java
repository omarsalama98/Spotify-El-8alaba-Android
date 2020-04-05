package com.vnoders.spotify_el8alaba.response.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.response.signup.UserInfo;
import java.util.List;

public class User {

    @SerializedName("external_urls")
    @Expose
    private List<Object> externalUrls = null;
    @SerializedName("genres")
    @Expose
    private List<Object> genres = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("followers")
    @Expose
    private List<Object> followers = null;
    @SerializedName("images")
    @Expose
    private List<Object> images = null;
    @SerializedName("uri")
    @Expose
    private String uri;

    public List<Object> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(List<Object> externalUrls) {
        this.externalUrls = externalUrls;
    }

    public List<Object> getGenres() {
        return genres;
    }

    public void setGenres(List<Object> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Object> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Object> followers) {
        this.followers = followers;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}


