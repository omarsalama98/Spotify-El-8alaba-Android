package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.Artist;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    @GET("search?")
        //+ "header Authorization: Bearer Bearer Token"

    Call<List<Artist>> getArtistsofSearch(
            @Query("q") String artistName,
            @Query("type") String searchType,
            @Query("limit") int limit,
            @Query("offset") int offset
    );


}
