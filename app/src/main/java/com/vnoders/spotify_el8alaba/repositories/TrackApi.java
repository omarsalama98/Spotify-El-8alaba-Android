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

    @Headers("Authorization: Bearer BQCgFtO_chSXnHed7NACw4rkC7o7cesq0c2d9el0EArycBfRQ6wjpwErREcaFplhF0V6KiTaysP063S7C_HzeOss4mvLHqsKPg1ovHFPkahaOrLdH_HyJtxKqkndGg5tgf7uY63db133nayac2zUHfeVYmNj3yI")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
