package com.vnoders.spotify_el8alaba.repositories;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.JsonObject;
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.AlbumTracksPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbum;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumItem;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumsPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.Owner;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.models.library.RequestBodyIds;
import com.vnoders.spotify_el8alaba.models.library.SimpleAlbumTrack;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.models.library.TrackItem;
import com.vnoders.spotify_el8alaba.models.library.TracksPagingWrapper;
import com.vnoders.spotify_el8alaba.ui.library.AddArtistsViewModel;
import com.vnoders.spotify_el8alaba.ui.library.AlbumTracksViewModel;
import com.vnoders.spotify_el8alaba.ui.library.AlbumViewModel;
import com.vnoders.spotify_el8alaba.ui.library.ArtistTracksViewModel;
import com.vnoders.spotify_el8alaba.ui.library.ArtistViewModel;
import com.vnoders.spotify_el8alaba.ui.library.LibraryArtistViewModel;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeViewModel;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This class contains static functions to connect to the network backend to the endpoints found at
 * {@link LibraryApi}
 */
public class LibraryRepository {

    private static RetrofitClient retrofitClient = RetrofitClient.getInstance();

    private static LibraryApi libraryApi = retrofitClient.getAPI(LibraryApi.class);


    /**
     * Make a request to the network to the endpoint used at {@link LibraryApi#getUserPlaylists} and
     * update the corresponding data in the {@link MutableLiveData}
     *
     * @param userPlaylists The {@link MutableLiveData} object which wraps the list of playlist of
     *                      the user
     */
    public static void updateLibraryPlaylists(
            MutableLiveData<List<LibraryPlaylistItem>> userPlaylists) {

        Call<LibraryPlaylistPagingWrapper> request = libraryApi.getUserPlaylists();

        request.enqueue(new Callback<LibraryPlaylistPagingWrapper>() {
            @Override
            public void onResponse(@NotNull Call<LibraryPlaylistPagingWrapper> call,
                    @NotNull Response<LibraryPlaylistPagingWrapper> response) {

                LibraryPlaylistPagingWrapper libraryPlaylist = response.body();
                if (response.isSuccessful() && libraryPlaylist != null) {
                    userPlaylists.setValue(libraryPlaylist.getItems());
                } else {
                    Log.e("updateLibraryPlaylists", response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LibraryPlaylistPagingWrapper> call, @NotNull Throwable t) {
                //TODO:
            }
        });

    }


    /**
     * Make a request to the network to the endpoint used at {@link LibraryApi#getPlaylist} and
     * update the corresponding data in the {@link PlaylistHomeViewModel}
     *
     * @param viewModel The view model which contains all the data to be updated after the network
     *                  request
     */
    public static void updatePlaylist(PlaylistHomeViewModel viewModel) {

        Call<Playlist> request = libraryApi.getPlaylist(viewModel.getPlaylistId());

        request.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NotNull Call<Playlist> call,
                    @NotNull Response<Playlist> response) {

                Playlist playlist = response.body();
                if (response.isSuccessful() && playlist != null) {

                    TracksPagingWrapper tracks = playlist.getTracks();
                    if (tracks != null && tracks.getTrackItems() != null) {
                        viewModel.setTracksSummary(buildTracksInfo(tracks.getTrackItems()));
                    }

                    List<Image> images = playlist.getImages();
                    if (images != null && images.size() > 0) {
                        viewModel.setImageUrl(images.get(0).getUrl());
                    }

                    Owner owner = playlist.getOwner();
                    if (owner != null) {
                        viewModel.setPlaylistOwnerName(owner.getName());

                        String currentUserId = App.getInstance().getCurrentUserId();

                        viewModel.setIsOwnedByMe(currentUserId != null &&
                                currentUserId.equals(owner.getId()));

                    }

                    viewModel.setPlaylistName(playlist.getName());

                    viewModel.setIsCollaborative(playlist.isCollaborative());

                    viewModel.setIsEmpty(playlist.getTracks().getTrackItems().isEmpty());

                    viewModel.setFinishedLoading(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Playlist> call, @NotNull Throwable t) {
                //TODO:
                Log.d("onFailure", t.getMessage());
            }
        });

    }

    private static Spanned buildTracksInfo(List<TrackItem> trackItems) {

        final String WHITE_COLOR = "#FFFFFF";
        final String andMore = App.getInstance().getString(R.string.and_more);
        final int MAX_INFO_SIZE = 300;

        StringBuilder stringBuilder = new StringBuilder();

        for (TrackItem trackItem : trackItems) {

            Track track = trackItem.getTrack();

            if (track != null) {
                String artistName = "Artist Name";
                List<Artist> artists = track.getArtists();
                if (artists != null && artists.size() > 0 && artists.get(0).getName() != null) {
                    artistName = artists.get(0).getName();
                }

                String html = String.format("<font color=\'%s\'> %s </font> %s  •  ",
                        WHITE_COLOR, artistName, track.getName());

                stringBuilder.append(html);
            }

            if (stringBuilder.length() > MAX_INFO_SIZE) {
                break;
            }
        }

        // in case there are no tracks we do not display it
        if (stringBuilder.length() > 0) {
            String html = String.format("<font color=\'%s\'> %s </font>", WHITE_COLOR, andMore);
            stringBuilder.append(html);
        }

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(stringBuilder.toString());
        }
    }


    /**
     * Make a request to the network to the endpoint used at {@link LibraryApi#getPlaylistTracks}
     * and update the corresponding data in the {@link PlaylistHomeViewModel}
     *
     * @param viewModel The view model which contains all the data to be updated after the network
     *                  request
     */
    public static void updatePlaylistTracks(PlaylistTracksViewModel viewModel) {

        Call<TracksPagingWrapper> request = libraryApi.getPlaylistTracks(viewModel.getPlaylistId());

        request.enqueue(new Callback<TracksPagingWrapper>() {
            @Override
            public void onResponse(@NotNull Call<TracksPagingWrapper> call,
                    @NotNull Response<TracksPagingWrapper> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<TrackItem> trackItems = response.body().getTrackItems();
                    updateLikedStatusInTracks(trackItems, viewModel);

                }

            }

            @Override
            public void onFailure(@NotNull Call<TracksPagingWrapper> call, @NotNull Throwable t) {

            }
        });

    }

    // Request the liked tracks to know which of our tracks are liked or not
    private static void updateLikedStatusInTracks(List<TrackItem> trackItems,
            PlaylistTracksViewModel viewModel) {

        Call<TracksPagingWrapper> likedTracksRequest = libraryApi.getLikedTracks();

        new AsyncTask<Void, Void, ArrayList<Track>>() {
            @Override
            protected ArrayList<Track> doInBackground(Void... voids) {
                ArrayList<Track> tracks = new ArrayList<>();
                try {
                    Response<TracksPagingWrapper> response = likedTracksRequest.execute();

                    if (response.isSuccessful() && response.body() != null) {
                        List<TrackItem> likedTracks = response.body().getTrackItems();

                        if (trackItems != null) {
                            for (TrackItem trackItem : trackItems) {
                                boolean isLiked = likedTracks.contains(trackItem);
                                Track track = trackItem.getTrack();
                                if (track != null) {
                                    track.setLiked(isLiked);
                                    tracks.add(track);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return tracks;
            }

            @Override
            protected void onPostExecute(ArrayList<Track> tracks) {
                viewModel.setTracks(tracks);
            }

        }.execute();
    }


    /**
     * Make a request to the network to the endpoint used at {@link LibraryApi#getPlaylistCoverImages} and
     * update the corresponding data in the {@link PlaylistHomeViewModel}
     *
     * @param viewModel The view model which contains all the data to be updated after the network
     *                  request
     */
    public static void updatePlaylistCoverImages(PlaylistTracksViewModel viewModel) {
        Call<List<Image>> request = libraryApi
                .getPlaylistCoverImages(viewModel.getPlaylistId());

        request.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NotNull Call<List<Image>> call,
                    @NotNull Response<List<Image>> response) {

                List<Image> images = response.body();
                if (response.isSuccessful() && images != null && images.size() > 0) {
                    String imageUrl = images.get(0).getUrl();
                    viewModel.setPlaylistImageUrl(imageUrl);
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<Image>> call, @NotNull Throwable t) {

            }
        });
    }

    public static void updatePlaylistFollowState(PlaylistHomeViewModel viewModel) {
        Call<List<Boolean>> request = libraryApi
                .doesCurrentUserFollowPlaylist(viewModel.getPlaylistId());

        request.enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(@NotNull Call<List<Boolean>> call,
                    @NotNull Response<List<Boolean>> response) {

                List<Boolean> followStates = response.body();
                if (response.isSuccessful() && followStates != null && followStates.size() > 0) {
                    viewModel.setFollowedState(followStates.get(0));
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Boolean>> call, @NotNull Throwable t) {
                //TODO:
                t.printStackTrace();
            }
        });
    }

    public static void followPlaylist(PlaylistHomeViewModel viewModel) {
        Call<Void> request = libraryApi.followPlaylist(viewModel.getPlaylistId());

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.isSuccessful()) {
                    viewModel.setFollowedState(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {

            }
        });
    }


    private static void unfollowPlaylist(String playlistId , @Nullable MutableLiveData<Boolean> followState) {
        Call<Void> request = libraryApi.unfollowPlaylist(playlistId);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.isSuccessful() && followState != null) {
                    followState.setValue(false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {

            }
        });
    }

    public static void unfollowPlaylist(PlaylistHomeViewModel viewModel) {
        unfollowPlaylist(viewModel.getPlaylistId() , (MutableLiveData<Boolean>) viewModel.getFollowedState());
    }

    public static void unfollowPlaylist(String playlistId) {
        unfollowPlaylist(playlistId, null);
    }


    public static void createPlaylist(String playlistName , MutableLiveData<String> playlistId){

        Call<JsonObject> request = libraryApi.createPlaylist( new Playlist(playlistName));

        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful() && response.body() != null){
                    JsonObject json = response.body();
                    playlistId.setValue(json.get("id").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static void getNumberOfLikedTracks(MutableLiveData<Integer> numberOfLikedSongs) {
        Call<JsonObject> request = libraryApi.getNumberOfLikedTracks();

        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject json = response.body();
                    numberOfLikedSongs.setValue(json.get("total").getAsInt());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public static void updateLikedTracksList(PlaylistTracksViewModel viewModel) {

        Call<TracksPagingWrapper> request = libraryApi.getLikedTracks();

        request.enqueue(new Callback<TracksPagingWrapper>() {
            @Override
            public void onResponse(@NotNull Call<TracksPagingWrapper> call,
                    @NotNull Response<TracksPagingWrapper> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<TrackItem> trackItems = response.body().getTrackItems();
                    ArrayList<Track> tracks = new ArrayList<>();

                    if (trackItems != null) {
                        for (TrackItem trackItem : trackItems) {
                            Track track = trackItem.getTrack();
                            if (track != null) {
                                track.setLiked(true);
                                tracks.add(track);
                            }
                        }
                    }

                    viewModel.setTracks(tracks);

                }

            }

            @Override
            public void onFailure(@NotNull Call<TracksPagingWrapper> call, @NotNull Throwable t) {

            }
        });

    }


    public static void likeTrack(String trackId) {
        Call<Void> request = libraryApi.likeTrack(trackId);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(App.getInstance(),"Added to Liked Songs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public static void unlikeTrack(String trackId) {
        Call<Void> request = libraryApi.unlikeTrack(trackId);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(App.getInstance(),"Removed from Liked Songs", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public static void addTrackToPlaylist(String playlistId , String TrackId){

        List<String> ids = Collections.singletonList(TrackId);
        RequestBodyIds requestBodyIds = new RequestBodyIds(ids);

        Call<Void> call = libraryApi.addTracksToPlaylist(playlistId, requestBodyIds);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(App.getInstance(), "Added to your playlist", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }


    public static void getUserFollowedArtists(MutableLiveData<List<Artist>> artists) {
        Call<List<Artist>> request = libraryApi.getUserFollowedArtists();

        request.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    artists.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });

    }

    public static void updateArtistFollowState(ArtistViewModel artistViewModel) {
        Call<List<Boolean>> request = libraryApi
                .doesCurrentUserFollowArtist(artistViewModel.getArtistId());

        request.enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response) {
                List<Boolean> followStates = response.body();
                if (response.isSuccessful() && followStates != null && followStates.size() > 0) {
                    artistViewModel.setFollowedState(followStates.get(0));
                    artistViewModel.finishedRequest();
                }
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t) {

            }
        });

    }

    public static void updateArtist(ArtistViewModel artistViewModel) {
        Call<List<Artist>> request = libraryApi.getArtist(artistViewModel.getArtistId());

        request.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().isEmpty()) {
                        Artist artist = response.body().get(0);
                        artistViewModel.setArtistName(artist.getName());

                        String imageUrl = null;
                        if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                            imageUrl = artist.getImages().get(0).getUrl();
                        }

                        artistViewModel.setImageUrl(imageUrl);
                        artistViewModel.setBiography(artist.getBiography());
                    }
                    artistViewModel.finishedRequest();
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });

    }

    public static void followArtist(ArtistViewModel artistViewModel) {
        RequestBodyIds id = new RequestBodyIds(artistViewModel.getArtistId());
        Call<Void> request = libraryApi.followArtists(id);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    artistViewModel.setFollowedState(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    public static void unfollowArtist(ArtistViewModel artistViewModel) {
        RequestBodyIds id = new RequestBodyIds(artistViewModel.getArtistId());
        Call<Void> request = libraryApi.unfollowArtists(id);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    artistViewModel.setFollowedState(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public static void updateRelatedArtists(ArtistViewModel artistViewModel) {
        Call<List<Artist>> request = libraryApi.getRelatedArtists(artistViewModel.getArtistId());

        request.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                if(response.isSuccessful() && response.body() != null){
                    artistViewModel.setRelatedArtists(response.body());
                    artistViewModel.finishedRequest();
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });


    }


    public static void updateArtistTracksSummary(ArtistViewModel artistViewModel) {
        Call<List<Track>> request = libraryApi.getArtistTopTracks(artistViewModel.getArtistId());

        request.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Track> tracks = response.body();

                    StringBuilder stringBuilder = new StringBuilder();

                    for (Track track : tracks) {
                        if (track != null) {
                            stringBuilder.append(track.getName());
                            stringBuilder.append("  •  ");
                        }
                    }

                    stringBuilder.append("and more");
                    artistViewModel.setTracksSummary(stringBuilder.toString());
                    artistViewModel.finishedRequest();
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {

            }
        });

    }

    public static void getRandomArtists(AddArtistsViewModel viewModel) {
        Call<Artists> request = libraryApi.getRandomArtists();

        request.enqueue(new Callback<Artists>() {
            @Override
            public void onResponse(Call<Artists> call, Response<Artists> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viewModel.setArtists(response.body().getArtists());
                }
            }

            @Override
            public void onFailure(Call<Artists> call, Throwable t) {

            }
        });

    }


    public static void updateRelatedArtists(String artistId, AddArtistsViewModel viewModel) {
        Call<List<SearchArtist>> request = libraryApi.getRelatedSearchArtists(artistId);

        request.enqueue(new Callback<List<SearchArtist>>() {
            @Override
            public void onResponse(Call<List<SearchArtist>> call,
                    Response<List<SearchArtist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viewModel.setRelatedArtists(artistId, response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SearchArtist>> call, Throwable t) {

            }
        });


    }


    public static void followArtists(List<String> artistsIds) {
        followArtists(artistsIds, null);
    }

    public static void followArtists(List<String> artistsIds, LibraryArtistViewModel viewModel) {
        if (artistsIds == null || artistsIds.isEmpty()) {
            return;
        }

        RequestBodyIds ids = new RequestBodyIds(artistsIds);
        Call<Void> request = libraryApi.followArtists(ids);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if (viewModel != null) {
                        viewModel.requestUserFollowedArtists();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public static void updateArtistTopTracks(ArtistTracksViewModel viewModel) {
        Call<List<Track>> request = libraryApi.getArtistTopTracks(viewModel.getArtistId());

        request.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    viewModel.setTracks(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {

            }
        });

    }

    public static void updateLibraryAlbums(MutableLiveData<List<LibraryAlbumItem>> userAlbums) {
        Call<LibraryAlbumsPagingWrapper> request = libraryApi.getUserAlbums();

        request.enqueue(new Callback<LibraryAlbumsPagingWrapper>() {
            @Override
            public void onResponse(Call<LibraryAlbumsPagingWrapper> call,
                    Response<LibraryAlbumsPagingWrapper> response) {

                if (response.isSuccessful() && response.body() != null) {
                    userAlbums.setValue(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<LibraryAlbumsPagingWrapper> call, Throwable t) {

            }
        });
    }

    public static void updateAlbumFollowState(@NotNull AlbumViewModel viewModel) {
        Call<List<Boolean>> request = libraryApi.doesCurrentUserFollowAlbum(viewModel.getAlbumId());

        request.enqueue(new Callback<List<Boolean>>() {
            @Override
            public void onResponse(Call<List<Boolean>> call, Response<List<Boolean>> response) {

                List<Boolean> followStates = response.body();
                if (response.isSuccessful() && followStates != null && followStates.size() > 0) {
                    viewModel.setFollowedState(followStates.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Boolean>> call, Throwable t) {

            }
        });
    }

    public static void updateAlbum(@NotNull AlbumViewModel viewModel) {
        Call<LibraryAlbum> request = libraryApi.getAlbum(viewModel.getAlbumId());

        request.enqueue(new Callback<LibraryAlbum>() {
            @Override
            public void onResponse(Call<LibraryAlbum> call, Response<LibraryAlbum> response) {
                if (response.isSuccessful() && response.body() != null) {

                    LibraryAlbum album = response.body();

                    viewModel.setAlbumName(album.getName());
                    viewModel.setReleaseDate(album.getReleaseDate());
                    viewModel.setTracksSummary(album.getTracks());

                    List<Image> albumImages = album.getImages();
                    if (albumImages != null && !albumImages.isEmpty()) {
                        viewModel.setImageUrl(albumImages.get(0).getUrl());
                    }

                    ArrayList<Artist> artists = album.getArtists();
                    if (artists != null && !artists.isEmpty()) {
                        Artist artist = artists.get(0);
                        viewModel.setArtistId(artist.getId());
                        viewModel.setArtistName(artist.getName());

                        String artistImageUrl = null;
                        if (artist.getImages() != null && !artist.getImages().isEmpty()) {
                            artistImageUrl = artist.getImages().get(0).getUrl();
                        }
                        viewModel.setArtistImageUrl(artistImageUrl);
                    }

                    viewModel.setFinishedLoading();

                }
            }

            @Override
            public void onFailure(Call<LibraryAlbum> call, Throwable t) {

            }
        });
    }

    public static void followAlbum(@NotNull AlbumViewModel viewModel) {
        Call<Void> request = libraryApi.followAlbum(viewModel.getAlbumId());

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(App.getInstance(), "Album Followed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public static void unfollowAlbum(AlbumViewModel viewModel) {
        Call<Void> request = libraryApi.unfollowAlbum(viewModel.getAlbumId());

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(App.getInstance(), "Album UnFollowed", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public static void updateAlbumTracks(AlbumTracksViewModel viewModel) {
        Call<AlbumTracksPagingWrapper> request = libraryApi.getAlbumTracks(viewModel.getAlbumId());

        request.enqueue(new Callback<AlbumTracksPagingWrapper>() {
            @Override
            public void onResponse(@NotNull Call<AlbumTracksPagingWrapper> call,
                    @NotNull Response<AlbumTracksPagingWrapper> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<SimpleAlbumTrack> items = response.body().getItems();
                    List<Track> tracks = new ArrayList<>();
                    if (items != null && !items.isEmpty()) {
                        String albumName = viewModel.getAlbumName();
                        String albumImageUrl = viewModel.getAlbumImageUrl();
                        String artistName = viewModel.getArtistName();

                        for (SimpleAlbumTrack simpleTrack : items) {
                            tracks.add(Track.createFromSimpleAlbumTrack(simpleTrack,
                                    albumName, albumImageUrl, artistName));
                        }
                    }
                    viewModel.setTracks(tracks);
                }
            }

            @Override
            public void onFailure(Call<AlbumTracksPagingWrapper> call, Throwable t) {

            }
        });

    }
}
