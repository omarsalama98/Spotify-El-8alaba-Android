package com.vnoders.spotify_el8alaba.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylist;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryRepository {

    private static RetrofitClient retrofitClient = RetrofitClient.getInstance();

    public static void updateLibraryPlaylists(
            MutableLiveData<List<UserLibraryPlaylistItem>> userPlaylists) {

        LibraryApi libraryApi = retrofitClient.getAPI(LibraryApi.class);

        Call<UserLibraryPlaylist> request = libraryApi.getUserPlaylists();

        request.enqueue(new Callback<UserLibraryPlaylist>() {
            @Override
            public void onResponse(@NotNull Call<UserLibraryPlaylist> call,
                    @NotNull Response<UserLibraryPlaylist> response) {

                if (response.isSuccessful()) {
                    userPlaylists.setValue(response.body().getItems());
                } else {
                    Log.e("updateLibraryPlaylists", response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLibraryPlaylist> call, @NotNull Throwable t) {
                //TODO:
            }
        });

    }

}
