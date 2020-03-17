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

    @Headers("Authorization: Bearer BQBkyJMKhTz4cBhcvP038wQWq4NUZ4TT1feyGQwR4vcjGJjL89W5l-E6JBErn4yTAnVDo8EytFXN_cad0zQRENKiS6-Xd1_tQ0E5twAuqRnQ5Rmwg8HrCjmHQjNCCb0CdoMzbijKPpY-hwPQybHDVCAJut-fx7k")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
