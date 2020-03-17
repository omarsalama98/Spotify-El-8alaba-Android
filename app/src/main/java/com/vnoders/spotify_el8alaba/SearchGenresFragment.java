package com.vnoders.spotify_el8alaba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGenresFragment extends Fragment {

    TextView genres_search_text_layout;

    public SearchGenresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_genres, container, false);
        genres_search_text_layout = v.findViewById(R.id.genres_search_bar_text_view);
        GridView topGenresGridView = v.findViewById(R.id.search_top_genres_gridview);
        GridView browseAllGenresGridView = v.findViewById(R.id.search_browse_all_genres_gridview);

        ArrayList<Genre> topGenresList = new ArrayList<>();
        Bitmap mbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bika);
        topGenresList.add(new Genre(mbitmap, "Sha3by"));
        topGenresList.add(new Genre(mbitmap, "Sha3by"));
        topGenresList.add(new Genre(mbitmap, "Sha3by"));
        topGenresList.add(new Genre(mbitmap, "Sha3by"));
        topGenresGridView.setAdapter(new SearchGenresGridAdapter(getContext(), topGenresList));
        //setGridViewHeightBasedOnChildren(topGenresGridView, 2);

        ArrayList<Genre> browseAllGenresList = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bika);
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresGridView
                .setAdapter(new SearchGenresGridAdapter(getContext(), browseAllGenresList));
        //setGridViewHeightBasedOnChildren(browseAllGenresGridView, 2);

        genres_search_text_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment search_fragment = new SearchFragment();
                FragmentTransaction fT = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                fT.add(R.id.search_fragment_container, search_fragment);
                fT.addToBackStack("search");
                fT.commit();
            }
        });

        return v;
    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

}
