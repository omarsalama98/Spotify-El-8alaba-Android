package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.vnoders.spotify_el8alaba.OverflowFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.CurrentlyPlayingTrack;
import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;

/**
 * @author Ali Adel Top part of track player fragment
 */
public class TrackTopFragment extends Fragment {

    // holds author name text view
    private TextView authorNameText;
    private TextView playingFromTextView;

    /**
     * inflate layout and return it to system to display
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player_top, container, false);

        // set the overflow menu click listener to start overflow menu
        rootView.findViewById(R.id.top_overflow_menu).setOnClickListener(v -> startOverflowMenu());

        String playingFrom = getString(R.string.playing_from_artist);
        // setting the text
        playingFromTextView = rootView.findViewById(R.id.playing_from_text);
        playingFromTextView.setText(playingFrom);

        authorNameText = rootView.findViewById(R.id.author_name_text_top);

        // setting the top button to behave like the back button
        Button button = rootView.findViewById(R.id.top_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // setting the observer on the data change then calling updateUI on data change
        TrackViewModel.getInstance().getCurrentTrack().observe(getActivity(), new Observer<Track>() {
            @Override
            public void onChanged(Track realTrack) {
                updateUI(realTrack);
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

        String name = track.getArtistName();

        if (name == null || name.equals("") || name.equals(" ")) {
            authorNameText.setVisibility(View.GONE);
            playingFromTextView.setVisibility(View.GONE);
        } else {
            authorNameText.setVisibility(View.VISIBLE);
            playingFromTextView.setVisibility(View.VISIBLE);
            authorNameText.setText(name);
        }
    }

    /**
     * Starts the overflow menu activity when button is pressed
     * gives it the track object it needs to use as intent extra as json string
     */
    private void startOverflowMenu() {
        // start fragment and make it a sliding from bottom animation
        AppCompatActivity parentActivity = (AppCompatActivity)getActivity();
        if (parentActivity != null) {
            OverflowFragment bottomSheetFragment = new OverflowFragment(TrackViewModel.getInstance().getCurrentTrack().getValue());
            bottomSheetFragment.show(parentActivity.getSupportFragmentManager(), bottomSheetFragment.getTag());
        }
    }
}
