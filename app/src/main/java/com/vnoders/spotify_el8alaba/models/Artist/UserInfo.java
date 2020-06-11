package com.vnoders.spotify_el8alaba.models.Artist;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class UserInfo {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("online")
    @Expose
    private Boolean online;
    @SerializedName("currentlyPlaying")
    @Expose
    private CurrentlyPlaying currentlyPlaying;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("following")
    @Expose
    private List<String> following = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("notificationTokens")
    @Expose
    private List<String> notificationTokens = null;
    @SerializedName("enabledNotifications")
    @Expose
    private EnabledNotifications enabledNotifications;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("devices")
    @Expose
    private List<Object> devices = null;
    @SerializedName("followedPlaylists")
    @Expose
    private List<FollowedPlaylist> followedPlaylists = null;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("id")
    @Expose
    private String id;

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

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
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

    public EnabledNotifications getEnabledNotifications() {
        return enabledNotifications;
    }

    public void setEnabledNotifications(EnabledNotifications enabledNotifications) {
        this.enabledNotifications = enabledNotifications;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
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

    public List<Object> getDevices() {
        return devices;
    }

    public void setDevices(List<Object> devices) {
        this.devices = devices;
    }

    public List<FollowedPlaylist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    public void setFollowedPlaylists(List<FollowedPlaylist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

}