package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.MediaPlaybackService;
import com.vnoders.spotify_el8alaba.OnSwipeTouchListener;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.TrackViewModel;
import com.vnoders.spotify_el8alaba.models.PlayableTrack;


/**
 * @author Ali Adel
 * Class that shows the media player and binds with media playback service
 */
public class TrackPlayerActivity extends AppCompatActivity {

    // connection to service
    private MediaPlaybackService mService;
    // boolean to know if currently bound to service or not
    private boolean mBound = false;

    // holds track image view
    private ImageView mTrackImageView;

    /**
     * Just set the layout and get track currently being played
     *
     * @param savedInstanceState bundle of saved state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_track_player);

        // get reference to image
        mTrackImageView = findViewById(R.id.track_image_view);

        // to remove the color of bottom navigation
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // listener for touches on image and surrounding space for swipe gestures
        // tell the service if swipe is detected to skip
        findViewById(R.id.track_player_space).setOnTouchListener(new OnSwipeTouchListener(TrackPlayerActivity.this) {
            @Override
            public void onSwipeLeft() {
                mService.skipToNext();
            }

            @Override
            public void onSwipeRight() {
                mService.skipToPrev();
            }
        });

        // setting observer to know when data changes
        TrackViewModel.getInstance().getCurrentTrack().observe(this, new Observer<PlayableTrack>() {
            @Override
            public void onChanged(PlayableTrack track) {
                updateUI(track);
            }
        });
    }

    /**
     * updates UI when called with track
     *
     * @param track current track being played holding info
     */
    private void updateUI(PlayableTrack track) {
        Picasso.get().load(track.getAlbum().getImages().get(0).getUrl()).into(mTrackImageView);
    }

    /**
     * Override it to bind to media playback service
     */
    @Override
    protected void onStart() {
        super.onStart();

        // bind the activity to service
        Intent intent = new Intent(this, MediaPlaybackService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Override it to sever connection to bound service
     */
    @Override
    protected void onStop() {
        super.onStop();

        // if bound then unbind to release service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * override back pressed to show sliding down animation when quitting
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_top, R.anim.exit_to_top);
    }

    /**
     * Connection to bind with media playback service
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlaybackService.MediaPlaybackBinder binder =
                    (MediaPlaybackService.MediaPlaybackBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    /**
     * @return an instance of media playback service for use by fragments
     */
    public MediaPlaybackService getService() {
        return mService;
    }
}