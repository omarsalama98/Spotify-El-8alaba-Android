package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.HomePlaylist;
import com.vnoders.spotify_el8alaba.models.Search.Albums;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.Playlists;
import com.vnoders.spotify_el8alaba.models.Search.Tracks;
import com.vnoders.spotify_el8alaba.models.Search.Users;
import com.vnoders.spotify_el8alaba.models.SearchResult;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
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
    @GET("browse/categories?limit=10&offset=5")
    Call<List<Category>> getHomeCategories();


    /**
     * @param categoryId The id of the category we want to retrieve its playlists
     *
     * @return A list of the desired category's playlists
     */
    @GET("browse/categories/{category_id}/playlists")
    Call<List<HomePlaylist>> getCategoryPlaylists(@Path("category_id") String categoryId);


}
