package com.vnoders.spotify_el8alaba.repositories;

import com.google.gson.JsonObject;
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.AlbumTracksPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbum;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumsPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistPagingWrapper;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.models.library.RequestBodyIds;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.models.library.TracksPagingWrapper;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains all backend's API endpoints related to the user's library. This interface
 * is implemented by {@link RetrofitClient#getAPI}
 */
public interface LibraryApi {

    /**
     * @return List of playlists (wrapped in {@link LibraryPlaylistPagingWrapper}) of the current user
     * wrapped in a {@link Call} object
     */
    @GET("me/playlists")
    Call<LibraryPlaylistPagingWrapper> getUserPlaylists();


    /**
     * @param playlistId The id of the required playlist
     *
     * @return The playlist requested wrapped in a {@link Call} object
     */
    @GET("playlists/{playlist_id}")
    Call<Playlist> getPlaylist(@Path("playlist_id") String playlistId);


    /**
     * @param playlistId The id of the playlist that contains required tracks
     *
     * @return List of tracks (wrapped in {@link TracksPagingWrapper}) in the requested playlist
     * wrapped in a {@link Call} object
     */
    @GET("playlists/{playlist_id}/tracks")
    Call<TracksPagingWrapper> getPlaylistTracks(@Path("playlist_id") String playlistId);


    /**
     * @param playlistId The id of the playlist to get its images
     *
     * @return List of cover images (different sizes) of the requested playlist
     */
    @GET("playlists/{playlist_id}/images")
    Call<List<Image>> getPlaylistCoverImages(@Path("playlist_id") String playlistId);


    @GET("playlists/{playlist_id}/followers/contains")
    Call<List<Boolean>> doesUsersFollowPlaylist(@Path("playlist_id") String playlistId,
            @Query("ids") List<String> userIds);


    @PUT("playlists/{playlist_id}/followers")
    Call<Void> followPlaylist(@Path("playlist_id") String playlistId);


    @DELETE("playlists/{playlist_id}/followers")
    Call<Void> unfollowPlaylist(@Path("playlist_id") String playlistId);


    @POST("users/playlists")
    Call<JsonObject> createPlaylist(@Body Playlist playlist);


    @GET("me/tracks")
    Call<TracksPagingWrapper> getLikedTracks();


    @POST("playlists/{playlist_id}/tracks")
    Call<Void> addTracksToPlaylist(
            @Path("playlist_id") String playlistId,
            @Body RequestBodyIds requestBody);


    // Used to check the number of liked tracks of the current user
    // because the backend API does not provide this endpoint
    // It returns a paging object with a field contains total number of items
    @GET("me/tracks?limit=0")
    Call<JsonObject> getNumberOfLikedTracks();


    @PUT("me/tracks")
    Call<Void> likeTrack(@Query("ids") String trackId);


    @DELETE("me/tracks")
    Call<Void> unlikeTrack(@Query("ids") String trackId);


    @GET("me/following?type=artist")
    Call<List<Artist>> getUserFollowedArtists();


    // Return list of ONLY ONE artist
    @GET("artists/{artist_id}")
    Call<List<Artist>> getArtist(@Path("artist_id") String artistId);


    @GET("me/following/contains")
    Call<List<Boolean>> doesCurrentUserFollowArtist(@Query("ids") String artistId);


    @PUT("me/following?type=artist")
    Call<Void> followArtists(@Body RequestBodyIds requestBodyIds);


    @HTTP(method = "DELETE", path = "me/following?type=artist", hasBody = true)
    Call<Void> unfollowArtists(@Body RequestBodyIds requestBodyIds);


    @GET("artists/{artist_id}/related-artists")
    Call<List<Artist>> getRelatedArtists(@Path("artist_id") String artistId);


    @GET("artists/{artist_id}/related-artists")
    Call<List<SearchArtist>> getRelatedSearchArtists(@Path("artist_id") String artistId);


    @GET("artists/{artist_id}/top-tracks")
    Call<List<Track>> getArtistTopTracks(@Path("artist_id") String artistId);


    @GET("search?q=&type=artist&limit=20&offset=0")
    Call<Artists> getRandomArtists();


    @GET("me/albums")
    Call<LibraryAlbumsPagingWrapper> getUserAlbums();


    @GET("albums/{album_id}")
    Call<LibraryAlbum> getAlbum(@Path("album_id") String albumId);


    @GET("me/albums/contains")
    Call<List<Boolean>> doesCurrentUserFollowAlbum(@Query("ids") String albumId);


    @PUT("me/albums")
    Call<Void> followAlbum(@Query("ids") String albumId);


    @DELETE("me/albums")
    Call<Void> unfollowAlbum(@Query("ids") String albumId);


    @GET("albums/{album_id}/tracks")
    Call<AlbumTracksPagingWrapper> getAlbumTracks(@Path("album_id") String albumId);

}
