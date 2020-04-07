package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.Artist;
import com.vnoders.spotify_el8alaba.models.library.Album;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Playlist;
import com.vnoders.spotify_el8alaba.models.Track;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers("Authorization: Bearer BQCsc8oABwhk39J1UG4laFproywJrjQb1ucgYLJuBIQ1pF6kGoPGD1L3XzdeKbP0Q-rpO4QeAbas6J9CCV49bedjWannp4xfMuBshTfWhzOpUq2hIoGDA0X3pxKicrhHmZ6IC8oE5RDVIaQyirDpK-EnEaqrLxs0TrH-NSX5690s5nWnMMaxmduVOVg5BB7bXT2yusDKCrqmPrds")
    @GET("tracks/2TpxZ7JUBn3uw46aR7qd6V")
    Call<Album> getAlbum();


    @GET("search?type=artist")
        //+ "header Authorization: Bearer Bearer Token"
    Call<List<Artist>> getArtistsOfSearch(
            @Query("q") String artistName,
            @Query("type") String searchType
    );

    @GET("search?type=album")
    Call<List<Album>> getAlbumsOfSearch(
            @Query("q") String albumName
    );

    @GET("search?type=playlist")
    Call<List<Playlist>> getPlaylistsOfSearch(
            @Query("q") String playlistName
    );

    @GET("search?type=track")
    Call<List<Track>> getTracksOfSearch(
            @Query("q") String trackName
    );


    @GET("search?")
    Call<List<Artist>> getAllOfSearch(
            @Query("q") String searchQuery,
            @Query("type") String searchType
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
    @GET("browse/categories?limit=10&offset=5")
    Call<List<Category>> getHomeCategories();


    /**
     * @param categoryId The id of the category we want to retrieve its playlists
     *
     * @return A list of the desired category's playlists
     */
    @GET("browse/categories/{category_id}/playlists")
    Call<List<Playlist>> getCategoryPlaylists(@Path("category_id") String categoryId);

}
