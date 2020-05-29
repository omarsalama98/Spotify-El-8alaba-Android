package com.vnoders.spotify_el8alaba.Artist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SettingsList;

public class ArtistHomeFragment extends Fragment {

    static TextView artistFollowersTextView, artistStreamsTextView, artistTopSongStreamsTextView, artistTopSongTextView;
    ImageView artistSettingsImageView, artistTopSongImageView, spotifyUserImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_artist_home, container, false);

        artistSettingsImageView = root.findViewById(R.id.artist_settings_image_view);
        artistFollowersTextView = root.findViewById(R.id.artist_followers_text_view);
        artistStreamsTextView = root.findViewById(R.id.artist_streams_text_view);
        artistTopSongStreamsTextView = root.findViewById(R.id.artist_top_song_streams_text_view);
        artistTopSongTextView = root.findViewById(R.id.artist_top_song_text_view);
        spotifyUserImageView = root.findViewById(R.id.spotify_user_image_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.artist_nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_artist_home) {
            navView.setSelectedItemId(R.id.navigation_artist_home);
        }

        spotifyUserImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        artistSettingsImageView.setOnClickListener(v -> {
            SettingsList settingsList = new SettingsList();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.artist_nav_host_fragment, settingsList, "SETTINGS_LIST")
                    .addToBackStack(null).commit();
        });

    }

}
