package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.OnSwipeTouchListener;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;

/**
 * @author Ali Adel
 */

/**
 * Player that is displayed with main activity at bottom which shows all the time
 */
public class BottomPlayerFragment extends Fragment {

    // text view that scrolls at bottom with name of song and author
    private TextView mSongInfoView;
    // image that appears at left of player
    private ImageView mSongImage;

    // holds button
    private ImageView mPlayPauseButton;
    // holds love button
    private Button mLoveButton;

    // current state of playing
    private boolean mIsPlaying = false;
    // current track being played
    private Track mCurrentTrack;

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
        mSongInfoView = rootView.findViewById(R.id.song_information);
        // important line for scrolling
        mSongInfoView.setSelected(true);
        //-----------------------------

        // finding refernce for image view
        mSongImage = rootView.findViewById(R.id.bottom_player_image);

        // getting reference to seek bar and stop it from moving when user clicks on it
        mSeekbar = getActivity().findViewById(R.id.seek_bar_bottom_player);
        mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // getting button and calling action function
        mPlayPauseButton = rootView.findViewById(R.id.bottom_player_play_pause_button);
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPausePressed();
            }
        });

        // getting button and setting it's love action
        mLoveButton = rootView.findViewById(R.id.love_button_main_fragment_bot);
        mLoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loveTrack();
            }
        });


        // setting the button player for when swiped skips and if clicked goes to player
        rootView.findViewById(R.id.bottom_player_container).setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                ((MainActivity)getActivity()).getService().skipToNext();
            }

            @Override
            public void onSwipeRight() {
                ((MainActivity)getActivity()).getService().skipToPrev();
            }

            @Override
            public void onClick() {
                goToSongPlayer();
            }
        });

        // get instance of current track and set the observer on it to update UI on data change
        TrackViewModel.getInstance().getCurrentTrack().observe(getActivity(), new Observer<Track>() {
            @Override
            public void onChanged(Track realTrack) {
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
    private void updateUI(Track track) {

        if (track == null) {
            getActivity().findViewById(R.id.music_player_fragment).setVisibility(View.GONE);
            getActivity().findViewById(R.id.seek_bar_bottom_player).setVisibility(View.GONE);
            return;
        }

        getActivity().findViewById(R.id.music_player_fragment).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.seek_bar_bottom_player).setVisibility(View.VISIBLE);

        // reads the track
        mCurrentTrack = track;

        // loads the image and puts it
        if (TextUtils.isEmpty(track.getImage())) {
            mSongImage.setImageResource(R.drawable.track_image_default);
        } else {
            Picasso.get().load(track.getImage()).into(mSongImage);
        }

        // concatenating the song info in 1 string
        if (track.getArtistName() == null || track.getArtistName().equals("") || track.getArtistName().equals(" ")) {
            String songInfo = track.getName();
            // if it equals the text already displayed then don't display it
            if (!TextUtils.equals(songInfo, mSongInfoView.getText()))
                mSongInfoView.setText(songInfo);
        } else {
            String songInfo = track.getName() + " • " + track.getArtistName();
            // if it equals the text already displayed then don't display it
            if (!TextUtils.equals(songInfo, mSongInfoView.getText()))
                mSongInfoView.setText(songInfo);
        }

        // puts correct icon in case of playing or paused
        mIsPlaying = track.getIsPlaying();
        if (mIsPlaying) {
            mPlayPauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
        } else {
            mPlayPauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }

        // set the background color of love button
        if (track.getLoved()) {
            mLoveButton.setBackground(getResources().getDrawable(R.drawable.like_track_liked));
            mLoveButton.getBackground().setTint(getResources().getColor(R.color.green));
        }
        else {
            mLoveButton.setBackground(getResources().getDrawable(R.drawable.like_track_unliked));
            mLoveButton.getBackground().setTint(getResources().getColor(R.color.white));
        }
    }

    /**
     * handles click presses on play_pause button
     * whether starts playing or pauses
     */
    private void playPausePressed() {
        if (mIsPlaying) {
            ((MainActivity)getActivity()).getService().pause();
        } else {
            ((MainActivity)getActivity()).getService().start();
        }
    }

    /**
     * Tell service to love track
     */
    private void loveTrack() {

        if (mCurrentTrack == null)
            return;

        if (mCurrentTrack.getLoved())
            ((MainActivity)getActivity()).getService().unLoveTrack(mCurrentTrack.getId());
        else
            ((MainActivity)getActivity()).getService().loveTrack(mCurrentTrack.getId());
    }


    /**
     * updates UI when called with progress
     *
     * @param progress of current track being played holding info
     */
    private void updateSeekbar(Integer progress) {

        if (mCurrentTrack == null)
            return;

        // get duration and progress of 0-100
        int songTime = mCurrentTrack.getDuration();

        int progressScaled = 0;

        if (songTime > 0) {
            progressScaled = progress * 100 / songTime;
        }

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
}