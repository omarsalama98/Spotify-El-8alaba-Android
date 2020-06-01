package com.vnoders.spotify_el8alaba.Artist;

import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.allSongsStreams;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.followers;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.songTopStreams;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.topSongName;

import android.annotation.SuppressLint;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;

public class ArtistHomeFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static TextView artistFollowersTextView, artistStreamsTextView, artistTopSongStreamsTextView, artistTopSongTextView;
    private ImageView artistTopSongImageView, spotifyUserImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_artist_home, container, false);

        artistFollowersTextView = root.findViewById(R.id.artist_followers_text_view);
        artistStreamsTextView = root.findViewById(R.id.artist_streams_text_view);
        artistTopSongStreamsTextView = root.findViewById(R.id.artist_top_song_streams_text_view);
        artistTopSongTextView = root.findViewById(R.id.artist_top_song_text_view);
        spotifyUserImageView = root.findViewById(R.id.spotify_user_image_view);

        return root;
    }

    static void updateUI() {
        artistStreamsTextView.setText(allSongsStreams);
        artistTopSongStreamsTextView.setText(songTopStreams);
        artistTopSongTextView.setText(topSongName);
        artistFollowersTextView.setText(followers);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.artist_nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_artist_home) {
            navView.setSelectedItemId(R.id.navigation_artist_home);
        }

        artistStreamsTextView.setText(allSongsStreams);
        artistTopSongStreamsTextView.setText(songTopStreams);
        artistTopSongTextView.setText(topSongName);
        artistFollowersTextView.setText(followers);

        spotifyUserImageView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });

    }

}
