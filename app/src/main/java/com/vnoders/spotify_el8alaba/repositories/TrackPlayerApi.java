package com.vnoders.spotify_el8alaba.repositories;


import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrackResponse;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.AdItem;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetAdRequest;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetAlbum;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetArtist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetLikedTracks;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetPlaylist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetSeveralTracks;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetUserStatus;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.PostPlayTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.ShareTrackResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Ali Adel
 * API representation to hit end-points of backend for use by track player module
 */
public interface TrackPlayerApi {

    @GET("me/player")
    Call<CurrentlyPlayingTrackResponse> getCurrentlyPlaying();

    @POST("me/player/track")
    Call<Void> postTrack(@Body PostPlayTrack trackIdObject);

    @PUT("me/tracks")
    Call<Void> loveTrack(@Query("ids") String trackId);

    @DELETE("me/tracks")
    Call<Void> unLoveTrack(@Query("ids") String trackId);

    @PUT("me/player/seek")
    Call<Void> updateTrackProgress(@Query("position_ms") int progress);

    @PUT("me/player/shuffle")
    Call<Void> setShuffleState(@Query("state") boolean shuffleState);

    @PUT("me/player/repeat")
    Call<Void> setRepeatState(@Query("state") boolean repeatState);

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

    @GET("tracks/share/{trackId}")
    Call<ShareTrackResponse> getShareUrl(@Path("trackId") String trackId);

    @GET("me/tracks?limit=50")
    Call<GetLikedTracks> getLikedTracks();

    @GET("ads")
    Call<GetAdRequest> getAd();

    @GET("users/me")
    Call<GetUserStatus> getUserStatus();
}
