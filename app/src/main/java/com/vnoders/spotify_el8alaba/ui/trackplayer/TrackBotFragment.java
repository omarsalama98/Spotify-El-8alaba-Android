package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;


/**
 * @author Ali Adel
 * Bottom part of track player to play music from
 */
@SuppressWarnings("deprecation")
public class TrackBotFragment extends Fragment {

    // holds the name of song text view
    private TextView mSongNameTextView;
    // holds the author name text view
    private TextView mAuthorNameText;
    // holds play_pause button
    private Button mPlayPauseButton;
    // holds seek bar
    private SeekBar mSeekbar;
    // tracks progress text
    private TextView mTrackProgress;
    // track progress duration
    private TextView mTrackDuration;
    // holds current track being played
    private Track mCurrentTrack;
    // holds skip next button
    private Button mNextButton;
    // holds skip previous button
    private Button mPrevButton;

    // if player is currently playing anything
    private boolean mIsPlaying = false;
    // to know if user is currently seeking
    private boolean mIsSeeking = false;

    /**
     * inflating layout and return it to system
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player_bot, container, false);


        // setting the texts displayed
        mSongNameTextView = rootView.findViewById(R.id.song_name_text);

        mAuthorNameText = rootView.findViewById(R.id.author_name_text_bot);

        // get seek bar and textViews of progress and duration and setting progress to 00:00
        mSeekbar = rootView.findViewById(R.id.seek_bar);
        setSeekbarListener();
        mTrackProgress = rootView.findViewById(R.id.trackProgress);
        mTrackProgress.setText("00:00");
        mTrackDuration = rootView.findViewById(R.id.trackDuration);

        // set play_pause button
        mPlayPauseButton = rootView.findViewById(R.id.play_pause_button);
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausePlayPressed();
            }
        });

        // setting skip to next press
        mNextButton = rootView.findViewById(R.id.skip_next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToNext();
            }
        });

        // setting skip to prev press
        mPrevButton = rootView.findViewById(R.id.skip_previous_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToPrev();
            }
        });

        // setting to observe change in global song being played
        TrackViewModel.getInstance().getCurrentTrack().observe(getActivity(), new Observer<Track>() {
            @Override
            public void onChanged(Track realTrack) {
                updateUI(realTrack);
            }
        });

        // setting to observe change in global song progress
        TrackViewModel.getInstance().getTrackProgress().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                updateSeekbar(integer);
            }
        });

        return rootView;
    }

    /**
     * updates UI when called with track
     *
     * @param track current track being played holding info
     */
    private void updateUI(Track track) {

        if (track == null)
            return;

        // get track
        mCurrentTrack = track;

        // set next and previous buttons
        if (mCurrentTrack.getHasNext()) {
            mNextButton.setBackground(getResources().getDrawable(R.drawable.ic_skip_next_white_56dp));
            mNextButton.setClickable(true);
        }
        else {
            mNextButton.setBackground(getResources().getDrawable(R.drawable.ic_skip_next_grey_56dp));
            mNextButton.setClickable(false);
        }
        if (mCurrentTrack.getHasPrev()) {
            mPrevButton.setBackground(getResources().getDrawable(R.drawable.ic_skip_previous_white_56dp));
            mPrevButton.setClickable(true);
        } else {
            mPrevButton.setBackground(getResources().getDrawable(R.drawable.ic_skip_previous_grey_56dp));
            mPrevButton.setClickable(false);
        }

        // set the name of song and name of author and setting the button to display correctly
        mSongNameTextView.setText(track.getName());
        if (TextUtils.isEmpty(track.getArtistName()))
            mAuthorNameText.setText(" ");
        else
            mAuthorNameText.setText(track.getArtistName());
        mIsPlaying = track.getIsPlaying();
        if (mIsPlaying) {
            mPlayPauseButton.setBackground(getResources().getDrawable(R.drawable.ic_pause_circle_filled_white_82dp));
        } else {
            mPlayPauseButton.setBackground(getResources().getDrawable(R.drawable.ic_play_circle_filled_white_82dp));
        }

        // get the duration and setting the text view corresponding to it
        int songTime = track.getDuration();

        String durationText = "";
        int minutes = songTime / 1000 / 60;
        int seconds = (songTime / 1000) % 60;

        String minutesString = "";
        if (minutes < 10) {
            minutesString += "0";
        }
        minutesString += minutes;

        String secondsString = "";
        if (seconds < 10) {
            secondsString += "0";
        }
        secondsString += seconds;

        durationText = minutesString + ":" + secondsString;

        mTrackDuration.setText(durationText);
    }

    /**
     * updates UI when called with progress
     *
     * @param progress of current track being played holding info
     */
    private void updateSeekbar(Integer progress) {

        // if user is currently seeking then don't update
        if (mIsSeeking || mCurrentTrack == null)
            return;

        // get the duration and scale it to 0-100
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

        // set the progress text
        setProgressText(progress);
    }

    /**
     * tell service to pause/skip according to state
     */
    private void pausePlayPressed() {
        if (mIsPlaying) {
            ((TrackPlayerActivity) getActivity()).getService().pause();
        } else {
            ((TrackPlayerActivity) getActivity()).getService().start();
        }
    }

    /**
     * tell service to skip to next song
     */
    private void skipToNext() {
        ((TrackPlayerActivity) getActivity()).getService().skipToNext();
    }

    /**
     * tell service to skip to previous song
     */
    private void skipToPrev() {
        ((TrackPlayerActivity) getActivity()).getService().skipToPrev();
    }

    /**
     * setting the listener of seek bar to seek in song depending on where clicked
     */
    private void setSeekbarListener() {
        // set the color of progress bar
        mSeekbar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mCurrentTrack == null) {
                    return true;
                }
                return false;
            }
        });

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // if seek from user then change text to update with it
                if (fromUser)
                    setProgressText((progress * (int)mCurrentTrack.getDuration()) / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // user started seeking to don't update with player
                mIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // user finished seeking so return it to update
                mIsSeeking = false;

                // tell service where user put seekBar and finished seeking
                ((TrackPlayerActivity) getActivity()).getService().seek(seekBar.getProgress());
            }
        });
    }

    /**
     * utility function to set text of progress
     *
     * @param progress progress so far in track
     */
    private void setProgressText(int progress) {
        // setting the text for the progress text view
        String progressText = "";
        int minutes = (progress / 1000) / 60;
        int seconds = (progress / 1000) % 60;

        String minutesString = "";
        if (minutes < 10) {
            minutesString += "0";
        }
        minutesString += minutes;

        String secondsString = "";
        if (seconds < 10) {
            secondsString += "0";
        }
        secondsString += seconds;

        progressText = minutesString + ":" + secondsString;

        mTrackProgress.setText(progressText);
    }
}