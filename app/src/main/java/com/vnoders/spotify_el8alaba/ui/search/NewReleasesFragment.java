package com.vnoders.spotify_el8alaba.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.R;


/**
 * A simple {@link Fragment} subclass for showing new releases. (Isn't currently used but will be
 * updated later)
 */
public class NewReleasesFragment extends Fragment {

    public NewReleasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_releases, container, false);
    }
}
