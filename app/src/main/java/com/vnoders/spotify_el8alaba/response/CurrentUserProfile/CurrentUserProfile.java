package com.vnoders.spotify_el8alaba.response.CurrentUserProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.response.signup.CurrentlyPlaying;
import java.io.Serializable;
import java.util.List;

public class CurrentUserProfile implements Serializable {

    @SerializedName("following")
    @Expose
    private List<Object> following = null;
    @SerializedName("followedPlaylists")
    @Expose
    private List<Playlist> followedPlaylists = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private List<Image> image;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("currentlyPlaying")
    @Expose
    private CurrentlyPlaying currentlyPlaying;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public CurrentlyPlaying getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(
            CurrentlyPlaying currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(
            List<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }
    public List<Object> getFollowing() {
        return following;
    }

    public void setFollowing(List<Object> following) {
        this.following = following;
    }
}
