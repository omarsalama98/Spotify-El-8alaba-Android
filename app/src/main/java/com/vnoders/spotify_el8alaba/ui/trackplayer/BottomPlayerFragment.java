package com.vnoders.spotify_el8alaba.ui.trackplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;


public class BottomPlayerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_player, container, false);

        Mock.mock_track currentTrack = Mock.getMockTrack();
        TextView textView = rootView.findViewById(R.id.song_information);
        // important line for scrolling
        textView.setSelected(true);
        //-----------------------------
        String songInformation = currentTrack.getName() + " · " + currentTrack.getAuthor();
        textView.setText(songInformation);

        ImageView songImage = rootView.findViewById(R.id.bottom_player_image);
        songImage.setImageResource(currentTrack.getImageId());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSongPlayer();
            }
        });

        songImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSongPlayer();
            }
        });

        return rootView;
    }

    private void goToSongPlayer() {
        startActivity(new Intent(getContext(), TrackPlayerActivity.class));

        getActivity().overridePendingTransition(R.anim.enter_from_bot, R.anim.exit_to_bot);
    }
}
