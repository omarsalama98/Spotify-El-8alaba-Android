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
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.overflowmenu.OverflowMenu;
import com.vnoders.spotify_el8alaba.models.overflowmenu.OverflowMenuItem;
import java.util.ArrayList;
import java.util.List;

import com.vnoders.spotify_el8alaba.models.TrackPlayer.Track;

/**
 * @author Ali Adel Top part of track player fragment
 */
public class TrackTopFragment extends Fragment {

    // holds author name text view
    private TextView mTypeNameText;
    private TextView mPlayingFromTextView;

    /**
     * inflate layout and return it to system to display
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player_top, container, false);

        // set the overflow menu click listener to start overflow menu
        rootView.findViewById(R.id.top_overflow_menu).setOnClickListener(v -> startOverflowMenu());

        // setting the text
        mPlayingFromTextView = rootView.findViewById(R.id.playing_from_text);

        mTypeNameText = rootView.findViewById(R.id.author_name_text_top);

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

        String name = track.getTypeName();

        if (name == null || name.equals("") || name.equals(" ")) {
            mTypeNameText.setVisibility(View.GONE);
            mPlayingFromTextView.setVisibility(View.GONE);
        } else {
            mTypeNameText.setVisibility(View.VISIBLE);
            mPlayingFromTextView.setVisibility(View.VISIBLE);
            mTypeNameText.setText(name);

            String type = track.getType();

            if (type.equals(Track.TYPE_ALBUM)) {
                mPlayingFromTextView.setText(getString(R.string.playing_from_album));
            } else if (type.equals(Track.TYPE_PLAYLIST)) {
                mPlayingFromTextView.setText(getString(R.string.playing_from_playlist));
            } else {
                mPlayingFromTextView.setText(getString(R.string.playing_from_artist));
            }
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

            Track track = TrackViewModel.getInstance().getCurrentTrack().getValue();

            String songName = " Temp Track Name";
            if (track != null) {
                songName = track.getName();
            }
            
            String authorName = "Temp Author Name";
            if (track != null) {
                authorName = track.getArtistName();
            }

            List<OverflowMenuItem> actionItems = new ArrayList<>();

            actionItems.add(new OverflowMenuItem(R.drawable.like_track_unliked,
                    getString(R.string.overflow_like), null));

            actionItems.add(new OverflowMenuItem(R.drawable.hide_track_visible,
                    getString(R.string.overflow_hide), null));

            actionItems.add(new OverflowMenuItem(R.drawable.add_song,
                    getString(R.string.overflow_add), null));

            actionItems.add(new OverflowMenuItem(R.drawable.artist,
                    getString(R.string.overflow_view), null));

            actionItems.add(new OverflowMenuItem(R.drawable.share,
                    getString(R.string.overflow_share), null));

            actionItems.add(new OverflowMenuItem(R.drawable.sleep,
                    getString(R.string.overflow_sleep), null));

            actionItems.add(new OverflowMenuItem(R.drawable.report,
                    getString(R.string.overflow_report), null));

            actionItems.add(new OverflowMenuItem(R.drawable.credits,
                    getString(R.string.overflow_credits), null));

            OverflowMenu overflowMenu = new OverflowMenu(songName, authorName, null, actionItems);
            overflowMenu.show(parentActivity.getSupportFragmentManager(), overflowMenu.getTag());
        }
    }
}
