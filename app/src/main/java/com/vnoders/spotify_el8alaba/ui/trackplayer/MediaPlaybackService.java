package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SplashActivity;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrackResponse;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetAlbum;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetArtist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetLikedTracks;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetPlaylist;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetPlaylistItem;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetPlaylistTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetSeveralTracks;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.GetTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.LikedTrackWrapper;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.PostPlayTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.ShareTrackResponse;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.repositories.TrackPlayerApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Ali Adel
 * Media playback service to play music from background even when activity is not visible
 */
public class MediaPlaybackService extends MediaBrowserServiceCompat implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {

    //______________________________________________________________________________________________
    //--------------------------------------CONSTANTS-----------------------------------------------
    //______________________________________________________________________________________________

    // used to format string used to stream music from backend
    // To use to specify song url using it's id to play it
    private static final String PLAYER_STREAMING_BASE_URL = RetrofitClient.BASE_URL + "streaming/";
    private static final String PLAYER_STREAMING_URL_MIDDLE = "?type=mobile&Authorization=Bearer ";

    // used to send the type of context the song is playing in the backend
    private static final String CONTEXT_ARTIST_PREFIX = "spotify:artist:";
    private static final String CONTEXT_ALBUM_PREFIX = "spotify:album:";
    private static final String CONTEXT_PLAYLIST_PREFIX = "spotify:playlist:";
    private static final String CONTEXT_RESPONSE_TYPE_ALBUM = "album";
    private static final String CONTEXT_RESPONSE_TYPE_PLAYLIST = "playlist";
    private static final String CONTEXT_RESPONSE_TYPE_ARTIST = "artist";

    // Widget constants for starting intent
    public static final String OPEN_APP = "open_app";
    public static final String PLAY_ID = "play_track";
    public static final String PAUSE_ID = "pause_track";
    public static final String SKIP_FORWARD = "skip_forward";
    public static final String SKIP_BACKWARD = "skip_backward";


    // constant which dictates time of handler thread
    private static final int HANDLER_DELAY = 1000;
    private static final int POST_UPDATE_PROGRESS_SECONDS = 10;

    //______________________________________________________________________________________________
    //--------------------------------------Variables-----------------------------------------------
    //______________________________________________________________________________________________

    // bind to give to activities to interact with this service
    private final IBinder mMediaPlaybackBinder = new MediaPlaybackBinder();
    // list of track ids received to play
    private List<Track> mTracksList = new ArrayList<>();
    // list of tracks to keep in case of shuffle
    private List<Track> mTracksListOriginal = new ArrayList<>();
    // list of hidden tracks
    private List<String> mTracksHidden = new ArrayList<>();
    // list of ids of liked tracks
    private List<String> mLikedTracks = new ArrayList<>();

    // access-token to use to play songs
    private String mAccessToken;

    // index of current track active
    private int mCurrentTrackIndex;

    // instance of media player and media session for audio playback
    private MediaPlayer mMediaPlayer;
    private MediaSessionCompat mMediaSession;
    private boolean mPlayerReady = false;
    private int mLastProgress = 0;

    // know if playing right now
    private boolean mPlaying = false;

    // reference to all widgets
    private int[] mAllWidgetIds;

    // this is for event every .. ms
    private Handler mHandler = new Handler();
    // runnable for handler
    private Runnable mRunnable = new Runnable() {
        public void run() {
            //do something
            if (mMediaPlayer != null) {
                if (mPlayerReady) {
                    int progress = mMediaPlayer.getCurrentPosition();
                    TrackViewModel.getInstance().updateTrackProgress(progress);

                    if ((progress - mLastProgress) >= (POST_UPDATE_PROGRESS_SECONDS * 1000)) {
                        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).updateTrackProgress(progress);
                        request.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {}
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {}});
                        mLastProgress = progress;
                    }
                }
            }

            mHandler.postDelayed(this, HANDLER_DELAY);
        }
    };

    // receiver to get events that happened outside of app
    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pause();
        }
    };

    // receive media session events callbacks
    private MediaSessionCompat.Callback mMediaSessionCallBacks = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();

            start();
        }

        @Override
        public void onPause() {
            super.onPause();

            pause();
        }
    };

    // know if this is the first init or not
    private boolean mFirstInit = false;
    private int mFirstProgress = 0;
    private boolean mRepeat = false;
    private boolean mShuffle = false;


    //______________________________________________________________________________________________
    //-------------------------------Service specific function--------------------------------------
    //______________________________________________________________________________________________

    /**
     * binder class that provides the interface to activities to act on
     */
    public class MediaPlaybackBinder extends Binder {
        public MediaPlaybackService getService() {
            return MediaPlaybackService.this;
        }
    }

    /**
     * Get List of liked tracks ids from backend
     */
    private void getLikedTracks() {
        // get request function
        Call<GetLikedTracks> request = RetrofitClient.getInstance()
                .getAPI(TrackPlayerApi.class).getLikedTracks();

        // make the request
        request.enqueue(new Callback<GetLikedTracks>() {
            @Override
            public void onResponse(Call<GetLikedTracks> call, Response<GetLikedTracks> response) {

                // if there is any error return from function

                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                List<LikedTrackWrapper> likedTracks = response.body().getLikedTracks();
                mLikedTracks = new ArrayList<>();

                if ((likedTracks == null) || (likedTracks.isEmpty())) {
                    return;
                }

                // add all tracks to liked tracks list
                for (int i = 0; i < likedTracks.size(); ++i) {
                    mLikedTracks.add(likedTracks.get(i).getLikedTrack().getId());
                }

                // check list of tracks to mark liked ones
                checkLikedTracks();
            }

            @Override
            public void onFailure(Call<GetLikedTracks> call, Throwable t) {
            }
        });
    }

    /**
     * Get a track request
     * @param trackId id of track to request from backend
     */
    private void getTrack(String trackId) {
        Call<GetTrack> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class)
                .getTrack(trackId);

        request.enqueue(new Callback<GetTrack>() {
            @Override
            public void onResponse(Call<GetTrack> call, Response<GetTrack> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                GetTrack track = response.body();

                if (track.getArtists() == null || track.getArtists().size() < 1) {
                    return;
                }

                String uri = null;
                if (track.getArtists().size() > 0) {
                    uri = CONTEXT_ARTIST_PREFIX + track.getArtists().get(0);
                }

                String artistId = null;
                if (track.getArtists().size() > 0) {
                    artistId = track.getArtists().get(0);
                }

                Track currentTrack = new Track(track.getId(), track.getName(), track.getDuration(),
                        null, Track.TYPE_ARTIST, null, null,
                        artistId, track.getAlbumId(), uri);

                mShuffle = false;
                currentTrack.setShuffle(false);
                setShuffleState(false);
                mTracksList = new ArrayList<>();
                mTracksList.add(currentTrack);
                mCurrentTrackIndex = 0;
                destroyMediaPlayer();
                initMediaPlayer(currentTrack);

                // check if list has liked tracks
                checkLikedTracks();
            }

            @Override
            public void onFailure(Call<GetTrack> call, Throwable t) {
            }
        });
    }

    /**
     * Called when an activity wants to connect with me
     *
     * @param intent send by activity (can carry info in bundle)
     * @return returns binder to provide interface with me
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMediaPlaybackBinder;
    }

    /**
     * use this call back to release media player
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyMediaPlayer();
        unregisterReceiver(mNoisyReceiver);
        mHandler = null;
        mRunnable = null;
    }

    //______________________________________________________________________________________________
    //------------------------------Track Manipulating Functions------------------------------------
    //______________________________________________________________________________________________

    /**
     * To change the currently playing track for this user and starts playing
     * @param trackId   id of track to be played
     * @param repeat    repeat status of track  true: repeat track , false: don't repeat
     */
    public void playTrack(String trackId, boolean repeat) {

        if (TextUtils.isEmpty(trackId)) {
            return;
        }

        mRepeat = repeat;
        setRepeatState(mRepeat);

        getTrack(trackId);
    }

    /**
     * To play this list of songs with their tracks' ids
     * Not to be confused with playlist as this only plays a list of tracks, doesn't play a playlist
     * If want to play a playlist refer to function playPlaylist
     * @param trackIds list of strings of track ids
     * @param shuffle shuffle status of list whether to play in order or shuffle
     * @param repeat repeat status of list whether to repeat it once over or not
     * @param trackId (optional) ID of track to start playing in (put as null if want to start list
     *                from beginning)
     */
    public void playList(List<String> trackIds, boolean shuffle, boolean repeat, String trackId) {
        if (trackIds == null || trackIds.isEmpty()) {
            return;
        }

        mRepeat = repeat;
        setRepeatState(mRepeat);

        getSeveralTracks(trackIds, shuffle, trackId);
    }

    /**
     * Start playing an album
     * @param albumId id of album to play
     * @param shuffle shuffle status of album
     * @param repeat repeat status of album playing
     * @param trackId (optional) ID of track to start playing in (put as null if want to start album
     *                from beginning)
     */
    public void playAlbum(String albumId, boolean shuffle, boolean repeat, String trackId) {
        if (TextUtils.isEmpty(albumId)) {
            return;
        }

        mRepeat = repeat;
        setRepeatState(mRepeat);

        getAlbum(albumId, shuffle, trackId);
    }

    /**
     * Start playing a playlist
     * @param playlistId id of playlist
     * @param shuffle shuffle status of playlist
     * @param repeat repeat status of playlist
     * @param trackId (optional) ID of track to start playing in (put as null if want to start
     *                playlist from beginning)
     */
    public void playPlaylist(String playlistId, boolean shuffle, boolean repeat, String trackId) {
        if (TextUtils.isEmpty(playlistId)) {
            return;
        }

        mRepeat = repeat;
        setRepeatState(mRepeat);

        getPlaylist(playlistId, shuffle, trackId);
    }

    /**
     * get several tracks from backend to play them
     * @param tracksIds ids of tracks as a list
     * @param shuffle shuffle stat to play
     * @param trackId id of track to start playing at (start at start if null)
     */
    private void getSeveralTracks(List<String> tracksIds, boolean shuffle, String trackId) {

        // preps the track request
        String tracksIdsReadyString = tracksIds.get(0);

        for (int i = 1; i < tracksIds.size(); ++i) {
            tracksIdsReadyString += "," + tracksIds.get(i);
        }

        Call<GetSeveralTracks> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class)
                .getSeveralTracks(tracksIdsReadyString);

        request.enqueue(new Callback<GetSeveralTracks>() {
            @Override
            public void onResponse(Call<GetSeveralTracks> call,
                    Response<GetSeveralTracks> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                List<GetTrack> tracks = response.body().getTracks();
                mTracksList = new ArrayList<>();

                if (tracks == null || tracks.size() < 1) {
                    return;
                }

                if (tracks.get(0).getArtists() == null || tracks.get(0).getArtists().size() < 1) {
                    return;
                }

                // loops on array and gets all tracks
                for (int i = 0; i < tracks.size(); ++i) {
                    GetTrack currentLoop = tracks.get(i);
                    String artistId = null;
                    if (currentLoop.getArtists().size() > 0) {
                        artistId = currentLoop.getArtists().get(0);
                    }
                    String uri = CONTEXT_ARTIST_PREFIX + artistId;
                    Track addTrack = new Track(currentLoop.getId(), currentLoop.getName(),
                            currentLoop.getDuration(), null, Track.TYPE_ARTIST,
                            null, null, artistId,
                            currentLoop.getAlbumId(), uri);
                    mTracksList.add(addTrack);
                }

                // set shuffle state
                mShuffle = shuffle;
                if (shuffle) {
                    mTracksListOriginal = new ArrayList<>();
                    mTracksListOriginal.addAll(mTracksList);
                    Collections.shuffle(mTracksList);
                }

                setShuffleState(shuffle);

                mCurrentTrackIndex = 0;

                // if trackId variable is not null then finds it and starts at it
                if (!TextUtils.isEmpty(trackId)) {
                    for (int i = 0; i < mTracksList.size(); ++i) {
                        if (mTracksList.get(i).getId().equals(trackId)) {
                            mCurrentTrackIndex = i;
                            break;
                        }
                    }
                }

                destroyMediaPlayer();
                initMediaPlayer(mTracksList.get(mCurrentTrackIndex));

                // check if list has liked tracks
                checkLikedTracks();
            }

            @Override
            public void onFailure(Call<GetSeveralTracks> call, Throwable t) {

            }
        });
    }

    /**
     * Get an album on request
     *
     * @param albumId id of album
     * @param shuffle shuffle stat to play with
     * @param trackId id of track to start at (plays from start if null)
     */
    private void getAlbum(String albumId, boolean shuffle, String trackId) {
        Call<GetAlbum> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class)
                .getAlbum(albumId);

        request.enqueue(new Callback<GetAlbum>() {
            @Override
            public void onResponse(Call<GetAlbum> call, Response<GetAlbum> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                GetAlbum album = response.body();

                String imageUrl = null;
                if (album.getImages().size() > 0) {
                    imageUrl = album.getImages().get(0).getUrl();
                }

                List<GetTrack> tracks = album.getTracks();
                String albumName = album.getName();

                mTracksList = new ArrayList<>();

                String uri = CONTEXT_ALBUM_PREFIX + albumId;

                // loops on all tracks of album and preps them
                for (int i = 0; i < tracks.size(); ++i) {
                    GetTrack track = tracks.get(i);
                    String artistId = null;
                    if (track.getArtists().size() > 0) {
                        artistId = track.getArtists().get(0);
                    }
                    Track addTrack = new Track(track.getId(), track.getName(),
                            track.getDuration(), null, Track.TYPE_ALBUM, albumName,
                            imageUrl, artistId, null, uri);
                    mTracksList.add(addTrack);
                }

                // if shuffle is enabled then shuffle the list and set shuffle state
                mShuffle = shuffle;
                if (shuffle) {
                    mTracksListOriginal = new ArrayList<>();
                    mTracksListOriginal.addAll(mTracksList);
                    Collections.shuffle(mTracksList);
                }

                setShuffleState(shuffle);

                mCurrentTrackIndex = 0;

                // if trackId is not null then find the track and start at it
                if (!TextUtils.isEmpty(trackId)) {
                    for (int i = 0; i < mTracksList.size(); ++i) {
                        if (mTracksList.get(i).getId().equals(trackId)) {
                            mCurrentTrackIndex = i;
                            break;
                        }
                    }
                }

                destroyMediaPlayer();
                initMediaPlayer(mTracksList.get(mCurrentTrackIndex));

                // check if list has liked tracks
                checkLikedTracks();
            }

            @Override
            public void onFailure(Call<GetAlbum> call, Throwable t) {

            }
        });
    }

    /**
     * Shuffle what is currently being played while preserving the currently playing track
     * but shuffling the entire list around it
     */
    public void shuffleToggle() {

        if (mTracksList == null || mTracksList.size() < 1){
            return;
        }

        // toggle the shuffle state
        mShuffle = !mShuffle;

        if (mShuffle) {
            // shuffle is enabled
            mTracksListOriginal = new ArrayList<>();
            // create the temp list to store the original order of tracks
            mTracksListOriginal.addAll(mTracksList);
            // get the current track being played as to not disturb the playing right now
            Track currentTrack = mTracksList.get(mCurrentTrackIndex);
            // remove it from list
            mTracksList.remove(mCurrentTrackIndex);
            // then shuffle
            Collections.shuffle(mTracksList);
            // finally add the currently being played again in same place
            mTracksList.add(mCurrentTrackIndex, currentTrack);
        }
        else {
            // shuffle is off so get the being played right now
            String trackId = mTracksList.get(mCurrentTrackIndex).getId();
            mTracksList = new ArrayList<>();
            // add the original list order to being played list
            mTracksList.addAll(mTracksListOriginal);
            // find the current being played and make index point to it
            for (int i = 0; i < mTracksList.size(); ++i) {
                if (mTracksList.get(i).getId().equals(trackId)) {
                    mCurrentTrackIndex = i;
                    break;
                }
            }
        }

        // tell backend about shuffle state
        setShuffleState(mShuffle);
        // update current track shuffle state
        mTracksList.get(mCurrentTrackIndex).setShuffle(mShuffle);
        // update being played right now
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
    }

    /**
     * This toggles the repeat status
     * if repeat is off then it turns on and vice versa
     */
    public void repeatAllToggle() {

        if (mTracksList == null || mTracksList.size() < 1) {
            return;
        }

        if (mRepeat) {

            // toggle the repeat
            mRepeat = false;

            // update next and previous accordingly
            if (mCurrentTrackIndex < 1)
                mTracksList.get(mCurrentTrackIndex).setHasPrev(false);
            else
                mTracksList.get(mCurrentTrackIndex).setHasPrev(true);

            if (mCurrentTrackIndex >= mTracksList.size() - 1)
                mTracksList.get(mCurrentTrackIndex).setHasNext(false);
            else
                mTracksList.get(mCurrentTrackIndex).setHasNext(true);

        } else {
            // toggle the repeat
            mRepeat = true;

            // if activating repeat then always gonna be a previous and next
            mTracksList.get(mCurrentTrackIndex).setHasPrev(true);
            mTracksList.get(mCurrentTrackIndex).setHasNext(true);
        }

        mTracksList.get(mCurrentTrackIndex).setRepeat(mRepeat);
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
        setRepeatState(mRepeat);
    }

    /**
     * Called to hide track from playlist
     * @param trackId id of track to hide from playlist
     */
    public void hideTrack(String trackId) {
        if ((mTracksList == null) || (mTracksList.size() < 1) || (TextUtils.isEmpty(trackId)))
            return;

        // add track to hidden tracks list
        mTracksHidden.add(trackId);

        // if it is currently being played then skip it
        // if can't skip forward(End of list) then skip backward
        if (mTracksList.get(mCurrentTrackIndex).getId().equals(trackId))
            if (!skipToNext())
                skipToPrev();
    }

    /**
     * Called to make track visible again
     * @param trackId id of track to return to visibility
     */
    public void unHideTrack(String trackId) {
        if ((mTracksList == null) || (mTracksList.size() < 1) || (TextUtils.isEmpty(trackId)))
            return;

        // un hide track and remove it from hidden tracks list
        mTracksHidden.remove(trackId);
    }

    /**
     * Shares track with social media apps
     * @param track track object that is gonna get shared
     */
    public void shareTrack(Track track) {
        if ((track == null) || (TextUtils.isEmpty(track.getShareUrl()))) {
            Toast.makeText(MediaPlaybackService.this, getString(R.string.share_url_error), Toast.LENGTH_SHORT).show();
            return;
        }

        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody;
        if (!TextUtils.isEmpty(track.getArtistName()))
            shareBody = track.getArtistName() + "\n";
        else
            shareBody = "";

        shareBody += track.getName() + "\n" + track.getShareUrl();
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Spotify El8alaba");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // because from service so have to add this flag
        Intent chooser = Intent.createChooser(intent, "Share Via");
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /*Fire!*/
        startActivity(chooser);
    }

    //______________________________________________________________________________________________
    //------------------------------MediaPlayer Public Functions------------------------------------
    //______________________________________________________________________________________________

    /**
     * called to start playing song
     */
    public void start() {

        if ((mTracksList == null) || (mTracksList.size() < 1) || (mCurrentTrackIndex < 0))
            return;

        // try to get audio focus first, if failed then don't play
        if (!successfullyRetrievedAudioFocus())
            return;

        // if no instance then init media player and let on prepared listener play the song
        if (mMediaPlayer == null && mTracksList.get(mCurrentTrackIndex) != null && !TextUtils.isEmpty(mTracksList.get(mCurrentTrackIndex).getId())) {
            initMediaPlayer(mTracksList.get(mCurrentTrackIndex));
            return;
        }

        // if media player is already active and there is network connection then play song
        // and start handler to update user about progress
        if (!mPlaying && isNetworkAvailable() && mPlayerReady) {
            setIsPlaying(true);
            mMediaPlayer.start();
            startHandler();
        }

    }

    /**
     * called to pause song
     */
    public void pause() {
        if ((mTracksList == null) || (mTracksList.size() < 1) || (mCurrentTrackIndex < 0))
            return;

        // if song is already playing then pause it and stop the handler from updating
        if (mPlaying && mPlayerReady) {
            setIsPlaying(false);
            mMediaPlayer.pause();
            stopHandler();
        }
    }

    /**
     * called to skip to next song
     *
     * @return true if succeeds to skip : there is a next song
     * false if fails to skip : last song in list
     */
    public boolean skipToNext() {

        // if not tracks then return false
        if ((mTracksList == null) || (mTracksList.size() < 1) || (mCurrentTrackIndex < 0))
            return false;

        mFirstInit = false;

        // if last song in list return false
        ++mCurrentTrackIndex;
        if (mCurrentTrackIndex > mTracksList.size() - 1) {
            if (mRepeat) {
                mCurrentTrackIndex = 0;
            } else {
                mCurrentTrackIndex = mTracksList.size() - 1;
                if (mPlayerReady) {
                    mMediaPlayer.seekTo(mMediaPlayer.getDuration());
                    mMediaPlayer.pause();
                }
                setIsPlaying(false);
                return false;
            }
        }

        // there is next song so load it and update current track being played
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));

        // reload media player instance to set data source to new song
        destroyMediaPlayer();
        initMediaPlayer(mTracksList.get(mCurrentTrackIndex));

        return true;
    }

    /**
     * called to skip to previous song
     */
    public void skipToPrev() {
        // if no track in list then do nothing
        if ((mTracksList == null) || (mTracksList.size() < 1) || (mCurrentTrackIndex < 0))
            return;

        mFirstInit = false;

        // if first song in list then just seek to beginning of song
        --mCurrentTrackIndex;
        if (mCurrentTrackIndex < 0) {
            if (mRepeat) {
                mCurrentTrackIndex = mTracksList.size() - 1;
            } else {
                mCurrentTrackIndex = 0;
            }
        }

        // there is previous song so load it and change global song to it
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));

        // reload media player instance to set data source to new song
        destroyMediaPlayer();
        initMediaPlayer(mTracksList.get(mCurrentTrackIndex));
    }

    /**
     * seek to specified location
     *
     * @param loc location to seek to
     */
    public void seek(int loc) {

        mFirstInit = false;

        // if no player then don't seek
        if (mMediaPlayer == null || !mPlayerReady) {
            return;
        }

        // get actual seeking location cus we got only from 0-100 to scale it to actual song
        int seekTo = (loc * mMediaPlayer.getDuration()) / 100;

        mMediaPlayer.seekTo(seekTo);
        mLastProgress = -100000;
    }


    //______________________________________________________________________________________________
    //-----------------------------MediaPlayer private Functions------------------------------------
    //______________________________________________________________________________________________

    /**
     * init media player
     */
    private void initMediaPlayer(Track track) {
        // if there is instance of media player then it has already been initialized so return
        if (mMediaPlayer != null)
            return;

        // if no track to play return nothing to do
        if (track == null || TextUtils.isEmpty(track.getId())) {
            return;
        }

        // if track is hidden then skip to next one
        if (mTracksHidden.contains(track.getId())) {
            if (!skipToNext()) {
                skipToPrev();
            }
            return;
        }

        // if there is not artist name then fetch it from backend
        if (TextUtils.isEmpty(track.getArtistName())) {
            getArtistInfo(track);
        }

        // if there is not image then fetch it from backend
        if (TextUtils.isEmpty(track.getImage())) {
            getAlbumImage(track);
        }

        // if there is no share url ready then fetch it from backend
        if (TextUtils.isEmpty(track.getShareUrl())) {
            getShareUrl(track);
        }

        // update the widget UI with the new track
        updateWidget(track);

        // set shuffle and repeat states
        mTracksList.get(mCurrentTrackIndex).setRepeat(mRepeat);
        mTracksList.get(mCurrentTrackIndex).setShuffle(mShuffle);

        // know if song has next or previous and set data accordingly
        if (mCurrentTrackIndex < 1)
            mTracksList.get(mCurrentTrackIndex).setHasPrev(false);
        else
            mTracksList.get(mCurrentTrackIndex).setHasPrev(true);

        if (mCurrentTrackIndex >= mTracksList.size() - 1)
            mTracksList.get(mCurrentTrackIndex).setHasNext(false);
        else
            mTracksList.get(mCurrentTrackIndex).setHasNext(true);

        if (mRepeat) {
            mTracksList.get(mCurrentTrackIndex).setHasPrev(true);
            mTracksList.get(mCurrentTrackIndex).setHasNext(true);
        }

        // update the currently played track
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));

        // Tell backend that i'm playing this track
        postCurrentlyPlaying(track);
        // init progress of track
        TrackViewModel.getInstance().updateTrackProgress(0);

        // create new instance of media player and set it to playing music
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        // set volume, wake mode and audio stream type
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setVolume(1.0f, 1.0f);

        // set the data source and prepare the data and setting the listener to this class
        try {
            // prepare the url to play the song
            String songUrl = PLAYER_STREAMING_BASE_URL + track.getId() + PLAYER_STREAMING_URL_MIDDLE + mAccessToken;

            // prepare the data
            mMediaPlayer.setDataSource(songUrl);
            mMediaPlayer.prepareAsync();

            // set playing and set media player listeners
            setIsPlaying(false);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mPlayerReady = false;
            mLastProgress = -100000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Init the media session that handles audio focus and call backs
     */
    void initMediaSession() {
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
        mMediaSession = new MediaSessionCompat(getApplicationContext(), "tag", mediaButtonReceiver, null);

        mMediaSession.setCallback(mMediaSessionCallBacks);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
        mMediaSession.setMediaButtonReceiver(pendingIntent);

        setSessionToken(mMediaSession.getSessionToken());
    }

    /**
     * Handles headphones coming unplugged
     */
    void initNoisyReceiver() {
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(mNoisyReceiver, filter);
    }

    /**
     * If audio focus changed so getting notified and taking proper actions
     * @param focusChange state of what happened
     */
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            // if lost audio focus for a short time or long time just pause then playback
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pause();
                break;

            // if can duck the audio then reduce it
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mPlaying && (mMediaPlayer != null))
                    mMediaPlayer.setVolume(0.3f, 0.3f);
                break;

            // got the focus again, if not playing then play, if playing then update volume
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mMediaPlayer != null) {
                    if (!mPlaying)
                        start();
                    mMediaPlayer.setVolume(1.0f, 1.0f);
                }
                break;
        }
    }

    /**
     * setting if song is playing or not and updating current playing track accordingly
     *
     * @param playing caller determines the state of playing
     */
    private void setIsPlaying(boolean playing) {
        mPlaying = playing;
        mTracksList.get(mCurrentTrackIndex).setIsPlaying(mPlaying);
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));

        mMediaSession.setActive(playing);

        if (playing) {
            successfullyRetrievedAudioFocus();
            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
        }
        else {
            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(this);
        }
    }

    /**
     * utility function to destroy media player
     * and stop handler thread
     */
    private void destroyMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            stopHandler();
        }
    }


    //______________________________________________________________________________________________
    //-------------------------------Getting tracks-------------------------------------------------
    //______________________________________________________________________________________________


    /**
     * Tell the backend API that i'm currently playing this track
     * @param track object that contains info of currently playing track
     */
    private void postCurrentlyPlaying(Track track) {
        if (track == null || TextUtils.isEmpty(track.getId()) || TextUtils.isEmpty(track.getContextUri())) {
            return;
        }

        PostPlayTrack trackIdObj = new PostPlayTrack(track.getId(), track.getContextUri());

        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).postTrack(trackIdObj);

        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    /**
     * Get the currently playing track from backend, only called on start up
     */
    private void getCurrentlyPlaying() {
        // get retrofit client instance
        RetrofitClient retrofitClient = RetrofitClient.getInstance();

        // make request to get currently playing track
        Call<CurrentlyPlayingTrackResponse> request = retrofitClient.getAPI(TrackPlayerApi.class).getCurrentlyPlaying();

        request.enqueue(new Callback<CurrentlyPlayingTrackResponse>() {
            @Override
            public void onResponse(Call<CurrentlyPlayingTrackResponse> call, Response<CurrentlyPlayingTrackResponse> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    TrackViewModel.getInstance().updateCurrentTrack(null);
                    return;
                }
                // read the body and get the track and init it with all the data that
                // get from JSON
                CurrentlyPlayingTrack track = response.body()
                        .getCurrentTrackWrapper().getCurrentTrack();
                String type = response.body().getCurrentTrackWrapper().getTrackContext().getType();
                String artistName = " ";
                if (track.getArtists().size() > 0)
                    artistName = track.getArtists().get(0).getUserInfo().getName();
                String songImageUrl = null;
                if (track.getAlbum().getImages().size() > 0)
                    songImageUrl = track.getAlbum().getImages().get(0).getUrl();


                Track currentTrack = new Track(track.getId(), track.getName(), track.getDuration(),
                        artistName, Track.TYPE_ARTIST, artistName, songImageUrl, null, null,
                        response.body().getCurrentTrackWrapper().getTrackContext().getUri());

                // init some state variables that definitely happen here
                mFirstInit = true;
                mFirstProgress = response.body().getCurrentTrackWrapper().getTrackProgress();
                // set the repeat stats
                mRepeat = response.body().getCurrentTrackWrapper().getRepeat();
                currentTrack.setRepeat(mRepeat);
                mTracksList = new ArrayList<>();
                mTracksList.add(currentTrack);

                // decode the context uri that i got from backend
                String uri = currentTrack.getContextUri();
                String id = null;

                for (int i = uri.length() - 1; i >= 0; --i) {
                    if (uri.charAt(i) == ':') {
                        id = uri.substring(i + 1);
                        break;
                    }
                }

                // get the shuffle stat of currently playing
                boolean shuffleState = response.body().getCurrentTrackWrapper().getShuffle();
                currentTrack.setShuffle(shuffleState);

                if (type.equals(CONTEXT_RESPONSE_TYPE_ALBUM)) {
                    getAlbum(id, shuffleState, currentTrack.getId());
                }
                else if (type.equals(CONTEXT_RESPONSE_TYPE_PLAYLIST)) {
                    getPlaylist(id, shuffleState, currentTrack.getId());
                }
                else {
                    mCurrentTrackIndex = 0;
                    destroyMediaPlayer();
                    initMediaPlayer(currentTrack);
                }
            }

            @Override
            public void onFailure(Call<CurrentlyPlayingTrackResponse> call, Throwable t) {
                TrackViewModel.getInstance().updateCurrentTrack(null);
            }
        });
    }

    /**
     * Play the playlist that is passed
     * @param playlistId id of playlist
     * @param shuffle shuffle stat to use
     * @param trackId id of track to start playing at (starts at beginning of null)
     */
    private void getPlaylist(String playlistId, boolean shuffle, String trackId) {
        Call<GetPlaylist> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).getPlaylist(playlistId);

        request.enqueue(new Callback<GetPlaylist>() {
            @Override
            public void onResponse(Call<GetPlaylist> call, Response<GetPlaylist> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                List<GetPlaylistItem> items = response.body().getTracks().getItems();

                mTracksList = new ArrayList<>();
                String uri = CONTEXT_PLAYLIST_PREFIX + playlistId;

                // read all tracks in album
                for (int i = 0; i < items.size(); ++i) {
                    GetPlaylistTrack track = items.get(i).getTrack();

                    String artistId = null;
                    if (track.getArtists().size() > 0)
                        artistId = track.getArtists().get(0).getId();

                    Track addTrack = new Track(track.getId(), track.getName(), track.getDuration(),
                            null, Track.TYPE_PLAYLIST, response.body().getName(),
                            null, artistId, track.getAlbum().getId(), uri);
                    mTracksList.add(addTrack);
                }

                // shuffles the list of shuffle is on and sets the variable
                mShuffle = shuffle;
                if (shuffle) {
                    mTracksListOriginal = new ArrayList<>();
                    mTracksListOriginal.addAll(mTracksList);
                    Collections.shuffle(mTracksList);
                }

                setShuffleState(shuffle);

                mCurrentTrackIndex = 0;

                // if trackId is not null then finds the track and starts at it
                if (!TextUtils.isEmpty(trackId)) {
                    for (int i = 0; i < mTracksList.size(); ++i) {
                        if (mTracksList.get(i).getId().equals(trackId)) {
                            mCurrentTrackIndex = i;
                            break;
                        }
                    }
                }

                destroyMediaPlayer();
                initMediaPlayer(mTracksList.get(mCurrentTrackIndex));

                // check if list has liked tracks
                checkLikedTracks();
            }

            @Override
            public void onFailure(Call<GetPlaylist> call, Throwable t) {
            }
        });
    }

    /**
     * Function to loop on tracks list and mark all tracks with liked ones or not
     */
    private void checkLikedTracks() {

        // if there is any error return from function

        if ((mLikedTracks == null) || (mLikedTracks.isEmpty())) {
            return;
        }

        if ((mTracksList == null) || (mTracksList.isEmpty())) {
            return;
        }

        // loop on all tracks if they exist in liked tracks list then mark them
        for (int i = 0; i < mTracksList.size(); ++i) {
            if (mLikedTracks.contains(mTracksList.get(i).getId())) {
                mTracksList.get(i).setLoved(true);
            } else {
                // otherwise un mark them
                mTracksList.get(i).setLoved(false);
            }
        }
    }

    /**
     * Init all my variables here
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // init the session and the broadcast receiver
        initMediaSession();
        initNoisyReceiver();
    }

    /**
     * called when startService is called in main activity
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (TrackViewModel.getInstance().getInitRequired().getValue()) {
            // get access token to use to play tracks
            SharedPreferences prefs = getSharedPreferences(
                    getResources().getString(R.string.access_token_preference), MODE_PRIVATE);
            mAccessToken = prefs.getString("token", null);

            // get the list of tracks to play
            getCurrentlyPlaying();
            // get a list of liked tracks
            getLikedTracks();

            // reset init status
            TrackViewModel.getInstance().updateInitRequired(false);
        }

        MediaButtonReceiver.handleIntent(mMediaSession, intent);

        // check if widget is the one who launched me
        checkWidget(intent);

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Tell backend to love track
     */
    public void loveTrack(String trackId) {
        if (TextUtils.isEmpty(trackId)) {
            return;
        }

        // find track in list and when found then set it's love status to true
        if ((mTracksList != null) && (mTracksList.size() > 0)) {
            for (int i = 0; i < mTracksList.size(); ++i) {
                if (mTracksList.get(i).getId().equals(trackId)) {
                    mTracksList.get(i).setLoved(true);

                    // if track is one being played right now then update it
                    if (mCurrentTrackIndex == i) {
                        TrackViewModel.getInstance()
                                .updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
                    }

                    break;
                }
            }
        }

        // inform user
        Toast.makeText(MediaPlaybackService.this,
                getString(R.string.like_track_popup), Toast.LENGTH_SHORT).show();

        // add track to liked tracks list
        mLikedTracks.add(trackId);

        // tell backend about loving track
        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class)
                .loveTrack(trackId);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    /**
     * Get the artist object from backend to fill in the track passed
     * @param track to get the artist of and fill it's info in it
     */
    private void getArtistInfo(Track track) {

        if (track == null || TextUtils.isEmpty(track.getArtistId())) {
            return;
        }

        Call<List<GetArtist>> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).getArtist(track.getArtistId());

        request.enqueue(new Callback<List<GetArtist>>() {
            @Override
            public void onResponse(Call<List<GetArtist>> call, Response<List<GetArtist>> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                List<GetArtist> artists = response.body();

                if (artists.size() < 1) {
                    return;
                }

                String name = artists.get(0).getName();

                // if data is valid then set the data for the track and if it is being
                // currently played then update the live data model
                if (!TextUtils.isEmpty(name)) {
                    track.setArtistName(name);
                    if (track.getType().equals(Track.TYPE_ARTIST)) {
                        track.setTypeName(name);
                    }
                    if (track == mTracksList.get(mCurrentTrackIndex)) {
                        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetArtist>> call, Throwable t) {

            }
        });
    }

    /**
     * Get the info about album to fill data for track
     * @param track to fill album data for
     */
    private void getAlbumImage(Track track) {
        if (track == null || TextUtils.isEmpty(track.getAlbumId())) {
            return;
        }

        Call<GetAlbum> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).getAlbum(track.getAlbumId());

        request.enqueue(new Callback<GetAlbum>() {
            @Override
            public void onResponse(Call<GetAlbum> call, Response<GetAlbum> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                if (response.body().getImages().size() < 1) {
                    return;
                }

                String imageUrl = response.body().getImages().get(0).getUrl();

                // if data is ok then fill the data for track and if track is being played now
                // then update the live data model
                if (!TextUtils.isEmpty(imageUrl)) {
                    track.setImage(imageUrl);
                    if (track == mTracksList.get(mCurrentTrackIndex)) {
                        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAlbum> call, Throwable t) {

            }
        });
    }

    /**
     * Get the external share url of track
     * @param track to fill share url data for
     */
    private void getShareUrl(Track track) {
        if (track == null || TextUtils.isEmpty(track.getAlbumId())) {
            return;
        }

        Call<ShareTrackResponse> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).getShareUrl(track.getId());

        request.enqueue(new Callback<ShareTrackResponse>() {
            @Override
            public void onResponse(Call<ShareTrackResponse> call, Response<ShareTrackResponse> response) {
                if ((!response.isSuccessful()) || (response.code() != 200)) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                String shareUrl = response.body().getShareUrl();

                // if data is ok then fill the data for track and if track is being played now
                // then update the live data model
                if (!TextUtils.isEmpty(shareUrl)) {
                    track.setShareUrl(shareUrl);
                    if (track == mTracksList.get(mCurrentTrackIndex)) {
                        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
                    }
                }
            }

            @Override
            public void onFailure(Call<ShareTrackResponse> call, Throwable t) {

            }
        });
    }

    /**
     * tells backend about shuffle state
     * @param shuffleState to send to backend
     */
    private void setShuffleState(boolean shuffleState) {
        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).setShuffleState(shuffleState);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}
            @Override
            public void onFailure(Call<Void> call, Throwable t) {}});
    }

    /**
     * tells backend about repeat state
     * @param repeatState to send to backend
     */
    private void setRepeatState(boolean repeatState) {
        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class).setRepeatState(repeatState);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}
            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    // _____________________________________________________________________________________________
    // --------------------------------Media player callbacks---------------------------------------
    // _____________________________________________________________________________________________

    /**
     * called when media player finished getting song from url
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

        mPlayerReady = true;

        // update live data info
        mTracksList.get(mCurrentTrackIndex).setDuration(mMediaPlayer.getDuration());
        TrackViewModel.getInstance().updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));

        // if first init then don't start song
        if (mFirstInit) {
            mFirstInit = false;
            mMediaPlayer.seekTo(mFirstProgress);
            TrackViewModel.getInstance().updateTrackProgress(mFirstProgress);
            mLastProgress = mFirstProgress;
            mFirstProgress = 0;
            setIsPlaying(false);
            return;
        }

        // if not playing and there is network connection then play song and update global
        // live data variable
        // start the handler
        if (!mPlaying && isNetworkAvailable()) {
            setIsPlaying(true);
            mMediaPlayer.start();
            startHandler();
        }

    }

    /**
     * when song finishes playing this is called and skip to next song if available
     * if not then stop playing
     *
     * @param mp media player object playing
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!skipToNext()) {
            setIsPlaying(false);
            stopHandler();
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------Widget Functions------------------------------------------
    //______________________________________________________________________________________________

    /**
     * Check the widget intent and see if it matches any code used
     * @param intent with data from widget
     */
    private void checkWidget(Intent intent) {

        if (intent != null) {
            String code = intent.getAction();
            if (!TextUtils.isEmpty(code)) {

                if (code.equals(OPEN_APP)) {
                    openApp();
                } else if (code.equals(PLAY_ID)) {
                    playPressed();
                }
                else if (code.equals(PAUSE_ID)) {
                    pausePressed();
                }
                else if (code.equals(SKIP_FORWARD)) {
                    skipForward();
                }
                else if (code.equals(SKIP_BACKWARD)) {
                    skipBackward();
                }

                // store the widget ids in global variable
                mAllWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            }
        }
    }

    /**
     * Open application with intent
     */
    private void openApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * play the track
     */
    private void playPressed() {
        start();
    }

    /**
     * pause track
     */
    private void pausePressed() {
        pause();
    }

    /**
     * skip to next track
     */
    private void skipForward() {
        skipToNext();
    }

    /**
     * skip to previous track
     */
    private void skipBackward() {
        skipToPrev();
    }

    /**
     * Updates the Widget UI to reflect current playing track
     * @param track object to update widget UI with
     */
    private void updateWidget(Track track) {
        // if any error then return
        if ((mAllWidgetIds == null) || (mTracksList == null) || (mTracksList.size() < 1) || (mCurrentTrackIndex < 0))
            return;

        // loop on all widgets
        for (int id : mAllWidgetIds) {
            // fetch the remote view
            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.spotify_el8alaba_widget);

            // update track name
            if (track.getName() != null)
                views.setTextViewText(R.id.widget_song_name, track.getName());
            else
                views.setTextViewText(R.id.widget_song_name, getString(R.string.widget_warning));

            // update track artist name
            if (track.getArtistName() != null)
                views.setTextViewText(R.id.widget_artist_name, track.getArtistName());
            else
                views.setTextViewText(R.id.widget_artist_name, " ");

            // update the widget
            AppWidgetManager.getInstance(this).updateAppWidget(id, views);
        }
    }

    //______________________________________________________________________________________________
    //------------------------------------Utility Functions-----------------------------------------
    //______________________________________________________________________________________________

    /**
     * Tell backend to hate track
     */
    public void unLoveTrack(String trackId) {
        if (TextUtils.isEmpty(trackId)) {
            return;
        }

        // find track in list and when found then set it's love status to false
        if ((mTracksList != null) && (mTracksList.size() > 0)) {
            for (int i = 0; i < mTracksList.size(); ++i) {
                if (mTracksList.get(i).getId().equals(trackId)) {
                    mTracksList.get(i).setLoved(false);

                    // if track is one being played right now then update it
                    if (mCurrentTrackIndex == i) {
                        TrackViewModel.getInstance()
                                .updateCurrentTrack(mTracksList.get(mCurrentTrackIndex));
                    }

                    break;
                }
            }
        }

        // inform user
        Toast.makeText(MediaPlaybackService.this,
                getString(R.string.unlike_track_popup), Toast.LENGTH_SHORT).show();

        // add track to liked tracks list
        mLikedTracks.remove(trackId);

        // tell backend about unloving track
        Call<Void> request = RetrofitClient.getInstance().getAPI(TrackPlayerApi.class)
                .unLoveTrack(trackId);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    /**
     * start the handler to watch progress and report it
     */
    private void startHandler() {
        mHandler.postDelayed(mRunnable, HANDLER_DELAY);
    }

    /**
     * stop handler to not leak any resources in the end
     */
    private void stopHandler() {
        mHandler.removeCallbacks(mRunnable); //stop handler when activity not visible
    }

    /**
     * check if there is network connectivity or not
     *
     * @return true : network connection available
     * false : no network connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Tries to retrieve audio focus
     * @return true : got audio focus successfully, false : failed to get audio focus
     */
    private boolean successfullyRetrievedAudioFocus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    /**
     * set the media session state of playing or not
     * @param state of playing or not
     */
    private void setMediaPlaybackState(int state) {
        PlaybackStateCompat.Builder playbackStateBuilder = new PlaybackStateCompat.Builder();

        if (state == PlaybackStateCompat.STATE_PLAYING) {
            playbackStateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE);
        }
        else {
            playbackStateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY);
        }

        playbackStateBuilder.setState(state,  PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
        mMediaSession.setPlaybackState(playbackStateBuilder.build());
    }


    // Just put them for class, not important functions
    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        if(TextUtils.equals(clientPackageName, getPackageName())) {
            return new BrowserRoot(getString(R.string.app_name), null);
        }

        return null;
    }

    //Not important for general audio service, required for class
    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(null);
    }
}
