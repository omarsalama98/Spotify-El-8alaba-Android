package com.vnoders.spotify_el8alaba.models.library;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to model data parsed from json network response using {@link Gson} library
 */
public class Album {

    @SerializedName("album_type")
    @Expose
    private String albumType;

    @SerializedName("genres")
    @Expose
    private List<String> genres = null;

    @SerializedName("href")
    @Expose
    private String href;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("popularity")
    @Expose
    private int popularity;

    @SerializedName("release_date")
    @Expose
    private Date releaseDate;

    @SerializedName("tracks")
    @Expose
    private ArrayList<Track> tracks;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("uri")
    @Expose
    private String uri;

    public Album(String name, String imageUrl) {
        this.images = Collections.singletonList(new Image(imageUrl));
        this.name = name;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public static final Comparator<Album> SORT_BY_NAME = new Comparator<Album>() {
        @Override
        public int compare(Album album1, Album album2) {
            return album1.name.compareTo(album2.name);
        }
    };

    public static final Comparator<Album> SORT_BY_RELEASE_DATE = new Comparator<Album>() {
        @Override
        public int compare(Album album1, Album album2) {
            return album1.releaseDate.compareTo(album2.releaseDate);
        }
    };

    public static final Comparator<Album> SORT_BY_POPULARITY = new Comparator<Album>() {
        @Override
        public int compare(Album album1, Album album2) {
            return Integer.compare(album1.popularity, album2.popularity);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Album album = (Album) obj;
        return Objects.equals(id, album.id) &&
                Objects.equals(name, album.name) &&
                Objects.equals(tracks, album.tracks) &&
                Objects.equals(uri, album.uri);
    }

}
