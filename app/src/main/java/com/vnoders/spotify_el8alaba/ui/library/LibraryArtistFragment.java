package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.R;

public class LibraryArtistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library_artist, container, false);
        View addArtists = root.findViewById(R.id.add_artists);

        addArtists.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddArtistActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
