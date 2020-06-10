package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class SimpleAlbumTrack {

    @SerializedName("album")
    @Expose
    private String albumId;

    @SerializedName("artists")
    @Expose
    private List<String> artistsIds;

    @SerializedName("duration_ms")
    @Expose
    private int durationInMilliseconds;

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("uri")
    @Expose
    private String uri;

    private boolean isLiked;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public List<String> getArtistsIds() {
        return artistsIds;
    }

    public void setArtistsIds(List<String> artistsIds) {
        this.artistsIds = artistsIds;
    }

    public int getDurationMs() {
        return durationInMilliseconds;
    }

    public void setDurationMs(int durationMs) {
        this.durationInMilliseconds = durationMs;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SimpleAlbumTrack track = (SimpleAlbumTrack) obj;
        return Objects.equals(albumId, track.albumId) &&
                Objects.equals(artistsIds, track.artistsIds) &&
                Objects.equals(href, track.href) &&
                Objects.equals(id, track.id) &&
                Objects.equals(name, track.name) &&
                Objects.equals(uri, track.uri);
    }

}
