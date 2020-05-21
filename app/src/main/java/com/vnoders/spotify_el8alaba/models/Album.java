package com.vnoders.spotify_el8alaba.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.library.Track;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Album {

    @SerializedName("album_type")
    private String albumType;

    @SerializedName("artists")
    private List<Artist> artists = null;

    @SerializedName("genres")
    private List<Object> genres = null;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private List<TrackImage> images = null;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private int popularity;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("release_date_precision")
    private String releaseDatePrecision;

    @SerializedName("tracks")
    private ArrayList<Track> tracks;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Object> getGenres() {
        return genres;
    }

    public void setGenres(List<Object> genres) {
        this.genres = genres;
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

    public List<TrackImage> getImages() {
        return images;
    }

    public void setImages(List<TrackImage> images) {
        this.images = images;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
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


}
