package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class AlbumTrack {

    @SerializedName("album")
    @Expose
    private String albumId;

    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;

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

    @SerializedName("type")
    @Expose
    private String type;

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

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
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
        AlbumTrack track = (AlbumTrack) obj;
        return Objects.equals(albumId, track.albumId) &&
                Objects.equals(artists, track.artists) &&
                Objects.equals(href, track.href) &&
                Objects.equals(id, track.id) &&
                Objects.equals(name, track.name) &&
                Objects.equals(uri, track.uri);
    }

}

