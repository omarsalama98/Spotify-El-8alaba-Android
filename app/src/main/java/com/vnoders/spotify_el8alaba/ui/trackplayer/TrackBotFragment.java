package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;


public class TrackBotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_player_bot, container, false);

        Mock.mock_track currentTrack = ((TrackPlayerActivity)getActivity()).getCurrentTrack();

        TextView songNameTextView = rootView.findViewById(R.id.song_name_text);
        songNameTextView.setText(currentTrack.getName());

        TextView authorNameText = rootView.findViewById(R.id.author_name_text_bot);
        authorNameText.setText(currentTrack.getAuthor());

        return rootView;
    }

}
