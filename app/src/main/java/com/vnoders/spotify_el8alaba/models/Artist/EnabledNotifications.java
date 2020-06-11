package com.vnoders.spotify_el8alaba.models.Artist;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class EnabledNotifications {

    @SerializedName("userFollowed")
    @Expose
    private Integer userFollowed;
    @SerializedName("playlistFollowed")
    @Expose
    private Integer playlistFollowed;
    @SerializedName("newArtistTrack")
    @Expose
    private Integer newArtistTrack;
    @SerializedName("newArtistAlbum")
    @Expose
    private Integer newArtistAlbum;

    public Integer getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(Integer userFollowed) {
        this.userFollowed = userFollowed;
    }

    public Integer getPlaylistFollowed() {
        return playlistFollowed;
    }

    public void setPlaylistFollowed(Integer playlistFollowed) {
        this.playlistFollowed = playlistFollowed;
    }

    public Integer getNewArtistTrack() {
        return newArtistTrack;
    }

    public void setNewArtistTrack(Integer newArtistTrack) {
        this.newArtistTrack = newArtistTrack;
    }

    public Integer getNewArtistAlbum() {
        return newArtistAlbum;
    }

    public void setNewArtistAlbum(Integer newArtistAlbum) {
        this.newArtistAlbum = newArtistAlbum;
    }

}