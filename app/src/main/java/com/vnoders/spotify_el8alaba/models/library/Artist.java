package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Artist {

    @SerializedName("biography")
    @Expose
    private String biography;

    @SerializedName(value = "image", alternate = "images")
    @Expose
    private List<Image> images;

    @SerializedName("userInfo")
    @Expose
    private ArtistUserInfo userInfo;

    @SerializedName("genres")
    @Expose
    private List<String> genres = null;

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("popularity")
    @Expose
    private int popularity;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("uri")
    @Expose
    private String uri;


    public Artist(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public ArtistUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArtistUserInfo userInfo) {
        this.userInfo = userInfo;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Artist artist = (Artist) obj;
        return Objects.equals(href, artist.href) &&
                Objects.equals(id, artist.id) &&
                Objects.equals(name, artist.name) &&
                Objects.equals(uri, artist.uri);
    }

}


