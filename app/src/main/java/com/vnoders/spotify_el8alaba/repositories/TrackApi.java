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

    @Headers("Authorization: Bearer BQDl6K5IGOlqwnt6zBvpxxXHl-DmS70iL399rp1BTlLbkdQ-py6YLeuCTSrcO0ZFas66wqR3Nw6dDobuHm4TeTy3Bl1y9EZ_HAU2lPuilvOVnv-YcRtV5pky1a82yUh1UXNK_qYbH8FDJRQtmstDkHTPSOxg48Q")
    @GET("tracks")
    Call<TrackList> getTracks(@Query("ids") String ids);
}
