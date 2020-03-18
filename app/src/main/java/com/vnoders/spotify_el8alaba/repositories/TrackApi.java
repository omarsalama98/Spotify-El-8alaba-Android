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

    @Headers("Authorization: Bearer BQBXUbUz-KEDyhn-Ox2y2EijDXbtMXhYqsbOskZO8ORjBvrqAZRNQb2U9Fhx__4UkGXcxd5xhpR86oYDzjqP777zj9a5W9CUH01zrNJUhl9Hs_y38qxZrXgA-XWEjAnntbcfdCxjC1VZkMu8gok2AYfYvkHSxEQ")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
