package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;

public class TrackTopFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player_top, container, false);

        Mock.mock_track currentTrack = ((TrackPlayerActivity)getActivity()).getCurrentTrack();

        String playingFrom;
        switch (currentTrack.getType()) {
            case 0: // playlist
                playingFrom = getString(R.string.playing_from_playlist);
                break;
            default:    // artist
                playingFrom = getString(R.string.playing_from_artist);
                break;
        }

        TextView playingFromTextView = rootView.findViewById(R.id.playing_from_text);
        playingFromTextView.setText(playingFrom);

        TextView authorNameText = rootView.findViewById(R.id.author_name_text_top);
        authorNameText.setText(currentTrack.getAuthor());

        Button button = rootView.findViewById(R.id.top_back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }
}
