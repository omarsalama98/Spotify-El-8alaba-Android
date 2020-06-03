package com.vnoders.spotify_el8alaba.models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Artist.EnabledNotifications;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.List;

public class User {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("online")
    @Expose
    private Boolean online;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("currentlyPlaying")
    @Expose
    private CurrentlyPlaying currentlyPlaying;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("following")
    @Expose
    private List<Object> following = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("notificationTokens")
    @Expose
    private List<String> notificationTokens = null;
    @SerializedName("confirmed")
    @Expose
    private Boolean confirmed;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("devices")
    @Expose
    private List<Object> devices = null;
    @SerializedName("followedPlaylists")
    @Expose
    private List<Object> followedPlaylists = null;
    @SerializedName("notifications")
    @Expose
    private List<Object> notifications = null;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabledNotifications")
    @Expose
    private EnabledNotifications enabledNotifications;

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

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CurrentlyPlaying getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void setCurrentlyPlaying(CurrentlyPlaying currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public List<Object> getFollowing() {
        return following;
    }

    public void setFollowing(List<Object> following) {
        this.following = following;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getNotificationTokens() {
        return notificationTokens;
    }

    public void setNotificationTokens(List<String> notificationTokens) {
        this.notificationTokens = notificationTokens;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Object> getDevices() {
        return devices;
    }

    public void setDevices(List<Object> devices) {
        this.devices = devices;
    }

    public List<Object> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(List<Object> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public List<Object> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Object> notifications) {
        this.notifications = notifications;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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

    public EnabledNotifications getEnabledNotifications() {
        return enabledNotifications;
    }

    public void setEnabledNotifications(EnabledNotifications enabledNotifications) {
        this.enabledNotifications = enabledNotifications;
    }

}