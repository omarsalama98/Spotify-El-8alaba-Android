package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Artist {

    @SerializedName("genres")
    private List<String> genres = null;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private int popularity;

    @SerializedName("type")
    private String type;

    @SerializedName("uri")
    private String uri;

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
                Objects.equals(type, artist.type) &&
                Objects.equals(uri, artist.uri);
    }

}


