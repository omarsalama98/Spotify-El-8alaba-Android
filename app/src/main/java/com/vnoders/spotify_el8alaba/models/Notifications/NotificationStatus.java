package com.vnoders.spotify_el8alaba.models.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationStatus {

    public int isUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(Integer userFollowed) {
        this.userFollowed = userFollowed;
    }

    public Integer isPlaylistFollowed() {
        return playlistFollowed;
    }

    public void setPlaylistFollowed(Integer playlistFollowed) {
        this.playlistFollowed = playlistFollowed;
    }

    public Integer isNewArtistTrack() {
        return newArtistTrack;
    }

    public void setNewArtistTrack(Integer newArtistTrack) {
        this.newArtistTrack = newArtistTrack;
    }

    public Integer isNewArtistAlbum() {
        return newArtistAlbum;
    }

    public void setNewArtistAlbum(Integer newArtistAlbum) {
        this.newArtistAlbum = newArtistAlbum;
    }

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

    public NotificationStatus(Integer userFollowed, Integer playlistFollowed,
            Integer newArtistTrack,
            Integer newArtistAlbum) {
        this.userFollowed = userFollowed;
        this.playlistFollowed = playlistFollowed;
        this.newArtistTrack = newArtistTrack;
        this.newArtistAlbum = newArtistAlbum;
    }
}
