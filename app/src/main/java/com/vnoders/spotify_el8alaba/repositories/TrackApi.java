package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.TrackList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author Ali Adel
 * to get tracks from the api
 */
public interface TrackApi {

    @Headers("Authorization: Bearer BQAEPP2fXGd-8tlFsjrA52V5yBRxHtHFWLdFgfQftbsPJUnxTj281rhhZ6LyTxxFrH9mCGi_gVWVclYeEc-Q8VRZtLxptEnZAkod6o69njYijGQAucIIYYkCsaBq2_t4Ifq7YXKsIXL8wL92oMRVuq-Mhykb-I8")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
