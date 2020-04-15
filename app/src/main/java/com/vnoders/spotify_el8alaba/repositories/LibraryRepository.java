package com.vnoders.spotify_el8alaba.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.vnoders.spotify_el8alaba.models.Item;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.models.library.TrackItem;
import com.vnoders.spotify_el8alaba.models.library.TracksPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylist;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeViewModel;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksViewModel;
import java.util.ArrayList;
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

                    for (Item trackItem : playlist.getTracks().getItems()) {
                        stringBuilder.append(trackItem.getTrack())
                                .append(" ")
                                .append(trackItem.getAddedBy())
                                .append(" • ");
                        if (stringBuilder.length() > 200) {
                            break;
                        }
                    }
                    stringBuilder.append("and more");

                    viewModel.setTracksSummary(stringBuilder.toString());
                    viewModel.setImageUrl(playlist.getImages().get(0).getUrl());
                    viewModel.setPlaylistOwnerName(playlist.getOwner());
                    viewModel.setPlaylistName(playlist.getName());

                }
            }

            @Override
            public void onFailure(@NotNull Call<Playlist> call, @NotNull Throwable t) {
                //TODO:
                Log.d("onFailure", t.getMessage());
            }
        });

    }


    public static void updatePlaylistTracks(PlaylistTracksViewModel viewModel) {

        Call<TracksPagingWrapper> request = libraryApi.getPlaylistTracks(viewModel.getPlaylistId());

        request.enqueue(new Callback<TracksPagingWrapper>() {
            @Override
            public void onResponse(@NotNull Call<TracksPagingWrapper> call,
                    @NotNull Response<TracksPagingWrapper> response) {

                if (response.isSuccessful()) {

                    TracksPagingWrapper tracksWrapper = response.body();
                    ArrayList<Track> tracks = new ArrayList<>();

                    for (TrackItem trackItem : tracksWrapper.getTrackItems()) {
                        tracks.add(trackItem.getTrack());
                    }

                    viewModel.setTracks(tracks);
                }

            }

            @Override
            public void onFailure(@NotNull Call<TracksPagingWrapper> call, @NotNull Throwable t) {

            }
        });

    }

    public static void updatePlaylistCoverImages(PlaylistTracksViewModel viewModel) {
        Call<List<TrackImage>> request = libraryApi
                .getPlaylistCoverImages(viewModel.getPlaylistId());

        request.enqueue(new Callback<List<TrackImage>>() {
            @Override
            public void onResponse(@NotNull Call<List<TrackImage>> call,
                    @NotNull Response<List<TrackImage>> response) {

                if (response.isSuccessful()) {
                    String imageUrl = response.body().get(0).getUrl();
                    viewModel.setPlaylistImageUrl(imageUrl);
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<TrackImage>> call, @NotNull Throwable t) {

            }
        });
    }

}
