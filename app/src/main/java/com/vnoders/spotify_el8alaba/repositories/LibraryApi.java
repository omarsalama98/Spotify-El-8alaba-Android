package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylist;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryApi {

    @GET("me/playlists")
    Call<UserLibraryPlaylist> getUserPlaylists();
}
