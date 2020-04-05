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


    @Headers("Authorization: Bearer BQAMhMNXb3hIeHk6apM22kZRCct9WgNJtfGzzkg5TrkP_O6ra9RcAiiCAMGHBELjy4fE7uoD4T2cVVAAE9T-HcP_AEywvUe_8K6ZGOTBNv93V3_MGIQGZnwItB9nJ1Gz9cd8tr1AkWCL9GUGLv8KWS4wjUo1RQkEEEA")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
