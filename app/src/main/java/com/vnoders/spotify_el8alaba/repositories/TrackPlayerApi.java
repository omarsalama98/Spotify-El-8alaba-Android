package com.vnoders.spotify_el8alaba.repositories;

import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrackResponse;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetAlbum;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetArtist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetPlaylist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetSeveralTracks;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.PostPlayTrack;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrackPlayerApi {

    @GET("me/player")
    Call<CurrentlyPlayingTrackResponse> getCurrentlyPlaying();

    @POST("me/player/track")
    Call<Void> postTrack(@Body PostPlayTrack trackIdObject);

    @PUT("me/player/seek")
    Call<Void> updateTrackProgress(@Query("position_ms") int progress);

    @GET("tracks/{trackId}")
    Call<GetTrack> getTrack(@Path("trackId") String trackId);

    @GET("artists/{artistId}")
    Call<List<GetArtist>> getArtist(@Path("artistId") String artistId);

    @GET("tracks")
    Call<GetSeveralTracks> getSeveralTracks(@Query("ids") String tracksIds);

    @GET("albums/{albumId}")
    Call<GetAlbum> getAlbum(@Path("albumId") String albumId);

    @GET("playlists/{playlistId}")
    Call<GetPlaylist> getPlaylist(@Path("playlistId") String playlistId);
}
