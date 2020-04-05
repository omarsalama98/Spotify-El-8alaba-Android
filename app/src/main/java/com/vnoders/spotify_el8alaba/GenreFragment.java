package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.GenrePlaylistsGridAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment {

    private RecyclerView genrePlaylistsGridView;
    private ArrayList<HomeInnerListItem> innerListItems;

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_genre, container, false);

        innerListItems = new ArrayList<HomeInnerListItem>();
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        genrePlaylistsGridView = root.findViewById(R.id.genre_playlists_grid_view);
        genrePlaylistsGridView
                .setAdapter(new GenrePlaylistsGridAdapter(innerListItems, this));
        genrePlaylistsGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return root;
    }
}
