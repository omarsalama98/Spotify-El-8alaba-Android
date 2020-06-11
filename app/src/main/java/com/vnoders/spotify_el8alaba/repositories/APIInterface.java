package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.Artist.AlbumTracks;
import com.vnoders.spotify_el8alaba.models.Artist.Artist;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistAlbums;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistTrack;
import com.vnoders.spotify_el8alaba.models.Artist.CreateATrackRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.CreateAnAlbumRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListens;
import com.vnoders.spotify_el8alaba.models.Artist.TrackListensRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateAlbumNameRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateSongNameAndAlbumRequestBody;
import com.vnoders.spotify_el8alaba.models.Artist.UpdateSongNameRequestBody;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Home.HomePlaylist;
import com.vnoders.spotify_el8alaba.models.Home.RecentlyPlayed;
import com.vnoders.spotify_el8alaba.models.Search.AlbumForTrack;
import com.vnoders.spotify_el8alaba.models.Search.Albums;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.Playlists;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.Tracks;
import com.vnoders.spotify_el8alaba.models.Search.Users;
import com.vnoders.spotify_el8alaba.models.SearchResult;
import com.vnoders.spotify_el8alaba.models.library.Track;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("search?type=album")
    Call<Albums> getAlbumsOfSearch(
            @Query("q") String albumName
    );

    @GET("search?type=artist")
    Call<Artists> getArtistsOfSearch(
            @Query("q") String artistName
    );

    @GET("search?type=playlist")
    Call<Playlists> getPlaylistsOfSearch(
            @Query("q") String playlistName
    );

    @GET("search?type=track")
    Call<Tracks> getTracksOfSearch(
            @Query("q") String trackName
    );

    @GET("search?type=user")
    Call<Users> getUsersOfSearch(
            @Query("q") String userName
    );

    @GET("search?limit=10")
    Call<SearchResult> getAllOfSearch(
            @Query("q") String searchQuery
    );


    @GET("browse/categories?")
    Call<List<Category>> getAllCategoriesOfCountry(
            @Query("country") String country
    );


    /**
     * @return Returns a list of Categories to show in Browse all in Search(Browse).
     */
    @GET("browse/categories?offset=3")
    Call<List<Category>> getAllCategories();

    /**
     * @return Returns a number of Categories(0->4) to show in "Your Top Genres" in Search(Browse).
     */
    @GET("browse/categories?limit=4")
    Call<List<Category>> getTopCategories();


    /**
     * @return Returns a list of Categories to show in Home. Not quite what should be shown and will
     * be changed later.
     */
    @GET("browse/categories?limit=10")
    Call<List<Category>> getHomeCategories();

    /**
     * @param categoryId The id of the category we want to retrieve its playlists
     *
     * @return A list of the desired category's playlists
     */
    @GET("browse/categories/{category_id}/playlists")
    Call<List<HomePlaylist>> getCategoryPlaylists(@Path("category_id") String categoryId);

    @GET("me/player/recently-played-contexts")
    Call<RecentlyPlayed> getRecentlyPlayed();

    @GET("browse/new-releases?limit=20&offset=0")
    Call<Albums> getNewReleases();

    @POST("tracks/listens")
    Call<List<TrackListens>> getTrackListens(@Body TrackListensRequestBody trackListensRequestBody);

    @GET("tracks/{id}")
    Call<ArtistTrack> getTrack(
            @Path("id") String trackId
    );

    @GET("artists/{id}")
    Call<List<Artist>> getArtist(
            @Path("id") String artistId
    );

    @GET("albums/{id}")
    Call<AlbumForTrack> getAlbum(
            @Path("id") String albumId
    );

    @GET("artists/{id}")
    Call<List<SearchArtist>> getSimpleArtist(
            @Path("id") String artistId
    );

    @GET("albums/{id}")
    Call<SearchAlbum> getSimpleAlbum(
            @Path("id") String albumId
    );

    @GET("playlists/{id}")
    Call<SearchPlaylist> getSimplePlaylist(
            @Path("id") String albumId
    );

    @GET("tracks?")
    Call<List<ArtistTrack>> getTracks(
            @Query("ids") String trackIds
    );

    @GET("artists/{id}/top-tracks")
    Call<List<Track>> getArtistTopTracks(
            @Path("id") String artistId
    );

    @GET("artists/{id}/albums")
    Call<ArtistAlbums> getArtistAlbums(
            @Path("id") String artistId
    );

    @GET("albums/{id}/tracks")
    Call<AlbumTracks> getAlbumTracks(
            @Path("id") String albumId
    );

    @PATCH("tracks/{id}")
    Call<SearchTrack> updateTrack(
            @Path("id") String trackId,
            @Body UpdateSongNameAndAlbumRequestBody updateSongRequestBody
    );

    @PATCH("tracks/{id}")
    Call<ArtistTrack> updateTrackName(
            @Path("id") String trackId,
            @Body UpdateSongNameRequestBody updateSongNameRequestBody
    );

    @POST("tracks")
    Call<ArtistTrack> createTrack(
            @Body CreateATrackRequestBody createATrackRequestBody
    );

    @POST("albums")
    Call<SearchAlbum> createAlbum(
            @Body CreateAnAlbumRequestBody createAnAlbumRequestBody
    );

    @Multipart
    @POST("albums/{id}/images")
    Call<ResponseBody> uploadAlbumImage(
            @Path("id") String albumId,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("streaming")
    Call<ResponseBody> uploadTrack(
            @Part("trackId") RequestBody trackId,
            @Part MultipartBody.Part track
    );

    @PATCH("albums/{id}")
    Call<SearchAlbum> updateAlbumName(
            @Path("id") String albumId,
            @Body UpdateAlbumNameRequestBody updateAlbumNameRequestBody
    );

    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(
            @Path("id") String trackId
    );

    @DELETE("albums/{id}")
    Call<Void> deleteAlbum(
            @Path("id") String albumId
    );

}
