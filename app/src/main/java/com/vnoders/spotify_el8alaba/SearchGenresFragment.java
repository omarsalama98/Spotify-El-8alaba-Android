package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGenresFragment extends Fragment {

    public SearchGenresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_genres, container, false);
        GridView gridview = v.findViewById(R.id.search_genres_gridview);
        gridview.setAdapter(new SearchGenresGridAdapter(getContext()));
        return v;
    }
}
