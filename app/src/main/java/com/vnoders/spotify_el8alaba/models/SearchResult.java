package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Search.Album;
import com.vnoders.spotify_el8alaba.models.Search.Artist;
import com.vnoders.spotify_el8alaba.models.Search.Playlist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import java.util.List;

public class SearchResult {

    @SerializedName("albums")
    @Expose
    private List<Album> albums = null;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("playlists")
    @Expose
    private List<Playlist> playlists = null;
    @SerializedName("tracks")
    @Expose
    private List<SearchTrack> tracks = null;

    public List<Album> getAlbums() {
        return albums;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<SearchTrack> getTracks() {
        return tracks;
    }

}