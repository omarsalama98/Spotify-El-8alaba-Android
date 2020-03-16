package com.vnoders.spotify_el8alaba;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vnoders.spotify_el8alaba.models.PlayableTrack;
import com.vnoders.spotify_el8alaba.models.TrackList;
import com.vnoders.spotify_el8alaba.repositories.TrackApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ali Adel
 * Media playback service to play music from background even when activity is not visible
 */
public class MediaPlaybackService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    //______________________________________________________________________________________________
    //--------------------------------------Variables-----------------------------------------------
    //______________________________________________________________________________________________

    // bind to give to activities to interact with this service
    private final IBinder mMediaPlaybackBinder = new MediaPlaybackBinder();
    // list of tracks in queue
    private List<PlayableTrack> tracks;
    // list of track ids received to play
    private List<String> tracksID;

    // index of current track active
    private int mCurrentTrackIndex;
    // current track playing
    private PlayableTrack mCurrentTrack;

    // instance of media player for audio playback
    private MediaPlayer mMediaPlayer;

    // know if playing right now
    private boolean mPlaying = false;

    // this is for event every .. ms
    private Handler mHandler;
    // runnable for handler
    private Runnable mRunnable = mRunnable = new Runnable() {
        public void run() {
            //do something
            if (mMediaPlayer != null) {
                if (!mSeeking)
                    // update the progress of track every Handler delay
                    TrackViewModel.getInstance().updateTrackProgress(mMediaPlayer.getCurrentPosition());
            }

            mHandler.postDelayed(this, HANDLER_DELAY);
        }
    };
    ;
    // constant which dictates time of handler thread
    private static final int HANDLER_DELAY = 50;
    // if currently seeking don't update progress
    private boolean mSeeking = false;


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
     * called when startService is called in main activity
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // get the list of tracks to play
        getTracks();

        return super.onStartCommand(intent, flags, startId);
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
    }


    //______________________________________________________________________________________________
    //------------------------------MediaPlayer Public Functions------------------------------------
    //______________________________________________________________________________________________

    /**
     * called to start playing song
     */
    public void start() {
        // if no instance then init media player and let on prepared listener play the song
        if (mMediaPlayer == null) {
            initMediaPlayer();
            return;
        }

        //Toast.makeText(getApplicationContext(), "Start enter", Toast.LENGTH_SHORT).show();

        // if media player is already active and there is network connection then play song
        // and start handler to update user about progress
        if (!mPlaying && isNetworkAvailable()) {
            setIsPlaying(true);
            mMediaPlayer.start();
            startHandler();
        }

    }

    /**
     * called to pause song
     */
    public void pause() {
        //Toast.makeText(getApplicationContext(), "Pause enter", Toast.LENGTH_SHORT).show();

        // if song is already playing then pause it and stop the handler from updating
        if (mPlaying) {
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

        // if there are no tracks then return false
        if (tracks == null || tracks.size() < 1)
            return false;

        // if last song in list return false
        ++mCurrentTrackIndex;
        if (mCurrentTrackIndex > tracks.size() - 1) {
            mCurrentTrackIndex = tracks.size() - 1;
            if (mMediaPlayer.getCurrentPosition() < mMediaPlayer.getDuration()) {
                mMediaPlayer.seekTo(mMediaPlayer.getDuration());
                TrackViewModel.getInstance().updateTrackProgress(mMediaPlayer.getDuration());
            }
            return false;
        }

        // there is next song so load it and update current track being played
        mCurrentTrack = tracks.get(mCurrentTrackIndex);
        TrackViewModel.getInstance().updateCurrentTrack(mCurrentTrack);

        // reload media player instance to set data source to new song
        destroyMediaPlayer();
        initMediaPlayer();

        return true;
    }

    /**
     * called to skip to previous song
     */
    public void skipToPrev() {

        // if no track in list then do nothing
        if (tracks == null || tracks.size() < 1)
            return;

        // if first song in list then just seek to beginning of song
        --mCurrentTrackIndex;
        if (mCurrentTrackIndex < 0) {
            mCurrentTrackIndex = 0;
            mMediaPlayer.seekTo(0);
            return;
        }

        // there is previous song so load it and change global song to it
        mCurrentTrack = tracks.get(mCurrentTrackIndex);
        TrackViewModel.getInstance().updateCurrentTrack(mCurrentTrack);

        // reload media player instance to set data source to new song
        destroyMediaPlayer();
        initMediaPlayer();
    }

    /**
     * used to indicate that user is moving the seek bar so stop handler from updating progress value
     */
    public void startSeeking() {
        mSeeking = true;
    }

    /**
     * seek to specified location
     *
     * @param loc location to seek to
     */
    public void seek(int loc) {
        // if no player then don't seek
        if (mMediaPlayer == null) {
            return;
        }

        // get actual seeking location cus we got only from 0-100 to scale it to actual song
        int seekTo = (loc * mMediaPlayer.getDuration()) / 100;

        mMediaPlayer.seekTo(seekTo);

        // set false so handler thread can continue updating value
        mSeeking = false;
    }


    //______________________________________________________________________________________________
    //-----------------------------MediaPlayer private Functions------------------------------------
    //______________________________________________________________________________________________

    /**
     * init media player
     */
    private void initMediaPlayer() {
        // if there is instance of media player then it has already been initialized so return
        if (mMediaPlayer != null)
            return;

        // if no current track to play return nothing to do
        if (mCurrentTrack == null) {
            return;
        }

        // if song doesn't have preview url to play return (for now cus we don't have real track url)
        if (mCurrentTrack.getPreview_url() == null)
            return;

        // create new instance of media player and set it to playing music
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        // set the data source and prepare the data and setting the listener to this class
        try {
            mMediaPlayer.setDataSource(mCurrentTrack.getPreview_url());
            mMediaPlayer.prepareAsync();
            setIsPlaying(false);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setting if song is playing or not and updating current playing track accordingly
     *
     * @param playing caller determines the state of playing
     */
    private void setIsPlaying(boolean playing) {
        mPlaying = playing;
        mCurrentTrack.setIsPlaying(mPlaying);
        TrackViewModel.getInstance().updateCurrentTrack(mCurrentTrack);
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
     * get the tracks from network API to play with IDs that we got
     */
    private void getTracks() {
        // get a retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // get the API
        TrackApi api = retrofit.create(TrackApi.class);


        // get tracks ID from mock class for now
        tracksID = Mock.getTracksID();

        if (tracksID == null)
            return;

        // combine tracks for 1 query as required by Spotify API
        String tracksIdCombined = "";
        if (tracksID.size() > 0)
            tracksIdCombined = tracksID.get(0);

        for (int i = 1; i < tracksID.size(); ++i) {
            tracksIdCombined += "," + tracksID.get(i);
        }

        // make the network request
        final Call<TrackList> request = api.getTracks(tracksIdCombined);
        request.enqueue(new Callback<TrackList>() {
            @Override
            public void onResponse(Call<TrackList> call, Response<TrackList> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "failed, token expired", Toast.LENGTH_LONG).show();
                    return;
                }

                // get the list of tracks and update current song being played
                TrackList track_list = response.body();
                tracks = track_list.getTracks();
                mCurrentTrackIndex = 0;

                // if no tracks then return
                if (tracks == null)
                    return;

                // if empty list return
                if (tracks.size() < 1)
                    return;

                // get the track
                mCurrentTrack = tracks.get(mCurrentTrackIndex);

                // update global live data variable
                TrackViewModel.getInstance().updateCurrentTrack(mCurrentTrack);


                //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            }

            // probably cus token expired
            @Override
            public void onFailure(Call<TrackList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
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
        // if not playing and there is network connection then play song and update global
        // live data variable
        // start the handler
        if (!mPlaying && isNetworkAvailable()) {
            setIsPlaying(true);
            mMediaPlayer.start();
            mCurrentTrack.setDuration(mMediaPlayer.getDuration());
            TrackViewModel.getInstance().updateCurrentTrack(mCurrentTrack);
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

        //Toast.makeText(getApplicationContext(), "Completion enter", Toast.LENGTH_SHORT).show();
    }


    //______________________________________________________________________________________________
    //------------------------------------Utility Functions-----------------------------------------
    //______________________________________________________________________________________________

    /**
     * start the handler to watch progress and report it
     */
    private void startHandler() {
        mHandler = new Handler();

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

}

