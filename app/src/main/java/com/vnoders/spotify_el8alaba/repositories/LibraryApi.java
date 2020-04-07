package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LibraryApi {

    @GET("me/playlists")
    Call<UserLibraryPlaylist> getUserPlaylists();

    @GET("playlists/{playlist_id}")
    Call<Playlist> getPlaylist(@Path("playlist_id") String playlistId);

}
