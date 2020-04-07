package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Track {

    private String artistName ;

    @SerializedName("album")
    private Album album;

    @SerializedName("artists")
    private List<Artist> artists = null;

    @SerializedName("external_urls")
    private List<ExternalUrls> externalUrls = null;

    @SerializedName("disc_number")
    private int discNumber;

    @SerializedName("duration_ms")
    private int durationInMilliseconds;

    @SerializedName("explicit")
    private boolean explicit;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("is_local")
    private boolean isLocal;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private int popularity;

    @SerializedName("preview_url")
    private String previewUrl;

    @SerializedName("track_number")
    private int trackNumber;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public int getDurationMs() {
        return durationInMilliseconds;
    }

    public void setDurationMs(int durationMs) {
        this.durationInMilliseconds = durationMs;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
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

    public boolean isIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
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

    public String getArtistName() {
        return artistName;
    }

    public Track(String artistName, String name) {
        this.artistName = artistName;
        this.name = name;
    }
}

