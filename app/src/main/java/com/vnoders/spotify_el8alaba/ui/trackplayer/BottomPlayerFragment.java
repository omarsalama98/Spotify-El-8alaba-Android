package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.MediaPlaybackService;
import com.vnoders.spotify_el8alaba.OnSwipeTouchListener;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.TrackViewModel;
import com.vnoders.spotify_el8alaba.models.PlayableTrack;
import com.vnoders.spotify_el8alaba.models.RealTrack;

/**
 * @author Ali Adel
 * Player that is displayed with main activity at bottom which shows all the time
 */
public class BottomPlayerFragment extends Fragment {

    // connection to service
    private MediaPlaybackService mService;
    // boolean to know if currently bound to service or not
    private boolean mBound = false;

    // text view that scrolls at bottom with name of song and author
    private TextView songInfoView;
    // image that appears at left of player
    private ImageView songImage;

    // holds button
    private ImageView playPauseButton;

    // current state of playing
    private boolean isPlaying = false;
    // current track being played
    private RealTrack mCurrentTrack;

    // seek bar reference
    private SeekBar mSeekbar;

    /**
     * Inflating the layout and returning it to the system to display
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_player, container, false);

        // setting the texts displayed
        songInfoView = rootView.findViewById(R.id.song_information);
        // important line for scrolling
        songInfoView.setSelected(true);
        //-----------------------------

        // finding refernce for image view
        songImage = rootView.findViewById(R.id.bottom_player_image);

        // getting reference to seek bar and stop it from moving when user clicks on it
        mSeekbar = getActivity().findViewById(R.id.seek_bar_bottom_player);
        mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // getting button and calling action function
        playPauseButton = rootView.findViewById(R.id.bottom_player_play_pause_button);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPausePressed();
            }
        });


        // setting the button player for when swiped skips and if clicked goes to player
        rootView.findViewById(R.id.bottom_player_container).setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                mService.skipToNext();
            }

            @Override
            public void onSwipeRight() {
                mService.skipToPrev();
            }

            @Override
            public void onClick() {
                goToSongPlayer();
            }
        });

        // get instance of current track and set the observer on it to update UI on data change
        TrackViewModel.getInstance().getCurrentTrack().observe(getActivity(), new Observer<RealTrack>() {
            @Override
            public void onChanged(RealTrack realTrack) {
                updateUI(realTrack);
            }
        });

        // watches the progress of song to update seek bar
        TrackViewModel.getInstance().getTrackProgress().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                updateSeekbar(integer);
            }
        });

        return rootView;
    }

    /**
     * When triggered go to track player activity
     */
    private void goToSongPlayer() {
        startActivity(new Intent(getContext(), TrackPlayerActivity.class));

        getActivity().overridePendingTransition(R.anim.enter_from_bot, R.anim.exit_to_bot);
    }

    /**
     * Updates the UI whenever there is a change in data (Livedata)
     *
     * @param track current track being played object
     */
    private void updateUI(RealTrack track) {

        if (track == null) {
            getActivity().findViewById(R.id.music_player_fragment).setVisibility(View.GONE);
            return;
        }

        getActivity().findViewById(R.id.music_player_fragment).setVisibility(View.VISIBLE);

        // reads the track
        mCurrentTrack = track;

        // loads the image and puts it
        songImage.setImageResource(R.drawable.track_image_default);

        // concatenating the song info in 1 string
        String songInfo = track.getName() + " • " + track.getArtists().get(0).getUserInfo().getName();
        // if it equals the text already displayed then don't display it
        if (!TextUtils.equals(songInfo, songInfoView.getText()))
            songInfoView.setText(songInfo);

        // puts correct icon in case of playing or paused
        isPlaying = track.getIsPlaying();
        if (isPlaying) {
            playPauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
        } else {
            playPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
    }

    /**
     * handles click presses on play_pause button
     * whether starts playing or pauses
     */
    private void playPausePressed() {
        if (isPlaying) {
            mService.pause();
        } else {
            mService.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // bind the activity to service
        Intent intent = new Intent(getActivity(), MediaPlaybackService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        // if bound then unbind to release service
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * updates UI when called with progress
     *
     * @param progress of current track being played holding info
     */
    private void updateSeekbar(Integer progress) {

        // get duration and progress of 0-100
        int songTime = mCurrentTrack.getDuration();

        int progressScaled = progress * 100 / songTime;

        // if SDK is above Nougat can call the set progress with animation
        // if below then just set it without animation
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Do something for Nougat and above versions
            mSeekbar.setProgress(progressScaled, true);
        } else {
            // do something for phones running an SDK before lollipop
            mSeekbar.setProgress(progressScaled);
        }
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
}