package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.Artist;
import com.vnoders.spotify_el8alaba.models.library.Album;
import com.vnoders.spotify_el8alaba.models.Category;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers("Authorization: Bearer BQCsc8oABwhk39J1UG4laFproywJrjQb1ucgYLJuBIQ1pF6kGoPGD1L3XzdeKbP0Q-rpO4QeAbas6J9CCV49bedjWannp4xfMuBshTfWhzOpUq2hIoGDA0X3pxKicrhHmZ6IC8oE5RDVIaQyirDpK-EnEaqrLxs0TrH-NSX5690s5nWnMMaxmduVOVg5BB7bXT2yusDKCrqmPrds")
    @GET("tracks/2TpxZ7JUBn3uw46aR7qd6V")
    Call<Album> getAlbum();


    @GET("search?")
        //+ "header Authorization: Bearer Bearer Token"
    Call<List<Artist>> getArtistsofSearch(
            @Query("q") String artistName,
            @Query("type") String searchType
    );

    Call<List<Artist>> getalbumsofSearch(
            @Query("q") String albumName,
            @Query("type") String searchType,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    Call<List<Artist>> getPLaylistsofSearch(
            @Query("q") String playlistName,
            @Query("type") String searchType,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    Call<List<Artist>> getTracksofSearch(
            @Query("q") String trackName,
            @Query("type") String searchType,
            @Query("limit") int limit,
            @Query("offset") int offset
    );


    @GET("search?")
    Call<List<Artist>> getAllOfSearch(
            @Query("q") String searchQuery,
            @Query("type") String searchType,
            @Query("limit") int limit,
            @Query("offset") int offset
    );


    @GET("browse/categories?")
    Call<ArrayList<Category>> getAllCategories(
            @Query("country") String country,
            @Query("limit") int limit,                      //All of them are optional I guess
            @Query("offset") int offset
    );

}
