package com.vnoders.spotify_el8alaba.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import java.util.List;

public class SearchResult {

    @SerializedName("albums")
    @Expose
    private List<SearchAlbum> albums = null;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("artists")
    @Expose
    private List<SearchArtist> artists = null;
    @SerializedName("playlists")
    @Expose
    private List<SearchPlaylist> playlists = null;
    @SerializedName("tracks")
    @Expose
    private List<SearchTrack> tracks = null;

    public List<SearchAlbum> getAlbums() {
        return albums;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<SearchArtist> getArtists() {
        return artists;
    }

    public List<SearchPlaylist> getPlaylists() {
        return playlists;
    }

    public List<SearchTrack> getTracks() {
        return tracks;
    }

}