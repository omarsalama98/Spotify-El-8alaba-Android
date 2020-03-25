package com.vnoders.spotify_el8alaba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.models.Genre;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;


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
        TextView genresSearchTextLayout = v.findViewById(R.id.genres_search_bar_text_view);

        ExpandableHeightGridView topGenresGridView = v
                .findViewById(R.id.search_top_genres_gridview);
        ExpandableHeightGridView browseAllGenresGridView = v
                .findViewById(R.id.search_browse_all_genres_gridview);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bika);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.beach);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.siu);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.sii);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.sestoelemento);
        Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.bugatti);
        Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(), R.drawable.tesla);

        ArrayList<Genre> topGenresList = new ArrayList<>();
        topGenresList.add(new Genre(bitmap, "Sha3by"));
        topGenresList.add(new Genre(bitmap7, "Sha3by"));
        topGenresList.add(new Genre(bitmap7, "Sha3by"));
        topGenresList.add(new Genre(bitmap6, "Sha3by"));

        topGenresGridView
                .setAdapter(new SearchGenresGridAdapter(getContext(), topGenresList, this));
        topGenresGridView.setExpanded(true);
        //setGridViewHeightBasedOnChildren(topGenresGridView, 2);

        ArrayList<Genre> browseAllGenresList = new ArrayList<>();

        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap2, "Chill"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap3, "Football"));
        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap4, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap5, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresList.add(new Genre(bitmap, "Sha3by"));
        browseAllGenresGridView
                .setAdapter(new SearchGenresGridAdapter(getContext(), browseAllGenresList, this));
        browseAllGenresGridView.setExpanded(true);
        //setGridViewHeightBasedOnChildren(browseAllGenresGridView, 2);

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

        return v;
    }


}
