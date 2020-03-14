package com.vnoders.spotify_el8alaba;

import android.media.Image;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Artist {


    @SerializedName("genres")
    private List<String> genres = null;
    @SerializedName("href")
    private String href;
    @SerializedName("id")
    private String id;
    @SerializedName("images")
    private List<Image> images = null;
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private Integer popularity;
    @SerializedName("type")
    private String type;
    @SerializedName("uri")
    private String uri;

    /**
     * No args constructor for use in serialization
     */
    public Artist() {
    }

    /**
     *
     */
    public Artist(List<String> genres, String href, String id, List<Image> images, String name,
            Integer popularity, String type, String uri) {
        super();
        this.genres = genres;
        this.href = href;
        this.id = id;
        this.images = images;
        this.name = name;
        this.popularity = popularity;
        this.type = type;
        this.uri = uri;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
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

}
