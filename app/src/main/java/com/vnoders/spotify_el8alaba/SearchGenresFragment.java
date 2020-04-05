package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGenresFragment extends Fragment {

    private RecyclerView browseAllGenresGridView;
    private RecyclerView topGenresGridView;
    private TextView genresSearchTextLayout;

    public SearchGenresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topGenresGridView
                .setAdapter(new SearchGenresGridAdapter(Mock.getTopGenres(this), this));
        topGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        browseAllGenresGridView
                .setAdapter(new SearchGenresGridAdapter(Mock.getAllGenres(this), this));
        browseAllGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        genresSearchTextLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.search_fragment_container, new SearchFragment())
                        .addToBackStack("search")
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_genres, container, false);
        genresSearchTextLayout = v.findViewById(R.id.genres_search_bar_text_view);

        topGenresGridView = v
                .findViewById(R.id.search_top_genres_gridview);
        browseAllGenresGridView = v
                .findViewById(R.id.search_browse_all_genres_gridview);

        return v;
    }


}
