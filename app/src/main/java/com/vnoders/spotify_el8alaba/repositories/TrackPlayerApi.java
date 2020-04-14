package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrackResponse;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetArtist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.PostTrackId;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrackPlayerApi {

    @GET("me/player")
    Call<CurrentlyPlayingTrackResponse> getCurrentlyPlaying();

    @POST("me/player/track")
    Call<Void> postTrack(@Body PostTrackId trackIdObject);

    @GET("tracks/{trackId}")
    Call<GetTrack> getTrack(@Path("trackId") String trackId);

    @GET("artists/{artistId}")
    Call<GetArtist> getArtist(@Path("artistId") String artistId);
}
