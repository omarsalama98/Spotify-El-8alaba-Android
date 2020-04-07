package com.vnoders.spotify_el8alaba.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.models.library.TrackItem;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylist;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeViewModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryRepository {

    private static RetrofitClient retrofitClient = RetrofitClient.getInstance();

    private static LibraryApi libraryApi = retrofitClient.getAPI(LibraryApi.class);

    public static void updateLibraryPlaylists(
            MutableLiveData<List<UserLibraryPlaylistItem>> userPlaylists) {

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

    public static void updatePlaylist(PlaylistHomeViewModel viewModel) {

        Call<Playlist> request = libraryApi.getPlaylist(viewModel.getPlaylistId());

        request.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NotNull Call<Playlist> call,
                    @NotNull Response<Playlist> response) {

                if (response.isSuccessful()) {
                    Playlist playlist = response.body();

                    StringBuilder stringBuilder = new StringBuilder();

                    for (TrackItem trackItem : playlist.getTracks().getTrackItems()) {
                        stringBuilder.append(trackItem.getTrack().getName())
                                .append(" ")
                                .append(trackItem.getTrack().getArtists().get(0).getName())
                                .append(" • ");
                        if (stringBuilder.length() > 200) {
                            break;
                        }
                    }
                    stringBuilder.append("and more");

                    viewModel.getTracksSummary().setValue(stringBuilder.toString());
                    viewModel.getImageUrl().setValue(playlist.getImages().get(0).getUrl());
                    viewModel.getPlaylistOwnerName().setValue(playlist.getOwner().getName());
                    viewModel.getPlaylistName().setValue(playlist.getName());

                }
            }

            @Override
            public void onFailure(@NotNull Call<Playlist> call, @NotNull Throwable t) {
                //TODO:
                Log.d("onFailure", t.getMessage());
            }
        });

    }


}
