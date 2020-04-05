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


    @Headers("Authorization: Bearer BQB_LaC6LgaSJcNrffdYaS1ewjz5EQUoJtmkMOI33CK5AfzjtTJ6VsKxUSxLGSjA7A3q6gSOo6KJhRrb7n-7htvMFEshsIvSGl2IleOm7xYKurI-jAYy11YAGPwMcf5rZYCvPbjN5cvkRl4HN0XqIOrtEgEBwuQK5Ng")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
