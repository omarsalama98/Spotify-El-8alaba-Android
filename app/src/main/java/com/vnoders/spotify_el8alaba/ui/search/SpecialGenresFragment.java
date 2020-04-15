package com.vnoders.spotify_el8alaba.ui.search;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.core.widget.NestedScrollView.OnScrollChangeListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.GridSpacingItemDecoration;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialGenresFragment extends Fragment {

    private RecyclerView topPlaylistsRecyclerView;
    private RecyclerView categoriesGridRecyclerView;
    private Toolbar toolbar;
    private TextView specialGenreTopTitle;
    private TextView specialGenreMainTitle;
    private NestedScrollView scrollView;

    public SpecialGenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_special_genres, container, false);

        topPlaylistsRecyclerView = root
                .findViewById(R.id.special_genre_top_playlists_recycler_view);
        categoriesGridRecyclerView = root.findViewById(R.id.special_genre_categories_grid_view);
        toolbar = root.findViewById(R.id.special_genre_fragment_toolbar);
        specialGenreTopTitle = root.findViewById(R.id.special_genre_fragment_top_title);
        specialGenreMainTitle = root.findViewById(R.id.special_genre_fragment_main_title);
        scrollView = root.findViewById(R.id.special_genre_fragment_scroll_view);

        return root;
    }

    private int getGridSpacing() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width / 25;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String title = arguments.getString(SearchByTypeConstantsHelper.GENRE_NAME_KEY);
        specialGenreMainTitle.setText(title);
        specialGenreTopTitle.setText(title);

        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        ArrayList<HomeInnerListItem> innerListItems = new ArrayList<>();
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f0000000265af49474d91827160b56b27"));
        innerListItems.add(new HomeInnerListItem("Akpa", "Akpro",
                "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba"));

        topPlaylistsRecyclerView
                .setAdapter(new RecentlyPlayedListAdapter(innerListItems, this));
        topPlaylistsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        int spacingInPixels = getGridSpacing();

        categoriesGridRecyclerView
                .setAdapter(new SearchGenresGridAdapter(this, Mock.getTopGenres(this)));
        categoriesGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoriesGridRecyclerView
                .addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));

        final float[] alpha = {0.0f};
        final float[] newAlpha = {0.0f};
        final int[] overallXScroll = {0};

        scrollView.setOnScrollChangeListener(
                (OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
                {
                    overallXScroll[0] = -scrollY + oldScrollY;

                    if (overallXScroll[0] > 0) {
                        newAlpha[0] = alpha[0] - 0.2f;

                        if (newAlpha[0] >= 0) {
                            specialGenreTopTitle.setAlpha(newAlpha[0]);
                            specialGenreMainTitle.setAlpha(1 - newAlpha[0]);
                            alpha[0] = newAlpha[0];
                        }
                    } else {
                        newAlpha[0] = alpha[0] + 0.2f;

                        if (newAlpha[0] <= 1) {
                            specialGenreTopTitle.setAlpha(newAlpha[0]);
                            specialGenreMainTitle.setAlpha(1 - newAlpha[0]);
                            alpha[0] = newAlpha[0];
                        }
                    }
                });
    }
}
