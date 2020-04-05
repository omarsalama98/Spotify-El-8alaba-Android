package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.TrackList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author Ali Adel to get tracks from the api
 */
public interface TrackApi {

    @Headers("Authorization: Bearer BQC_2SY9Hr4qnGKMz6nOhzgQInDLr5J91bQvCJez90-HNTMeKZzOzug2WKNqzBzkDeyf8NXiQgNb-8ZCMFrc6vbjfXgNXR4iNeWJMmtplkd-QEqONASVu2z80pjr0tIbU3qlY8_Zns5vpMmv8iOaeBmA0-a5INPDmso")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
