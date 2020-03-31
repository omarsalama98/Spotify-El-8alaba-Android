package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.TrackList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author Ali AdelAPI_URL
 * to get tracks from the api
 */
public interface TrackApi {

    @Headers("Authorization: Bearer BQAM6-SmQwUcd7om8f00ceF6uZk5cdsGegy5ZY6aAb7pnXKCWbzE7StRd5kVafbQhNarmY7Cxc9ZFr4_Ao2O9OfutoJMycCf9-KksKGWDrbHZgQ-9MhI8SGvVBe_9BOWFkHA-3o9zPvI9dxt8jPRu_b8Tl6vaCM")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
