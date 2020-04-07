package com.vnoders.spotify_el8alaba.ui.search;

import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.GenrePlaylistsGridAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment {

    private RecyclerView genrePlaylistsGridView;
    private Toolbar toolbar;
    private TextView genreTopTitle;
    private TextView genreMainTitle;
    private NestedScrollView scrollView;

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_genre, container, false);
        genrePlaylistsGridView = root.findViewById(R.id.genre_playlists_grid_view);
        toolbar = root.findViewById(R.id.genre_fragment_toolbar);
        genreTopTitle = root.findViewById(R.id.genre_fragment_top_title);
        genreMainTitle = root.findViewById(R.id.genre_fragment_main_title);
        scrollView = root.findViewById(R.id.genre_fragment_scroll_view);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        genrePlaylistsGridView
                .setAdapter(new GenrePlaylistsGridAdapter(innerListItems, this));
        genrePlaylistsGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        final float[] alpha = {0.0f};
        final float[] newAlpha = {0.0f};
        final int[] overallXScroll = {0};

        scrollView.setOnScrollChangeListener(
                (OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    overallXScroll[0] = -scrollY + oldScrollY;

                    if (overallXScroll[0] > 0) {
                        newAlpha[0] = alpha[0] - 0.2f;

                        if (newAlpha[0] >= 0) {
                            genreTopTitle.setAlpha(newAlpha[0]);
                            genreMainTitle.setAlpha(1 - newAlpha[0]);
                            alpha[0] = newAlpha[0];
                        }
                    } else {
                        newAlpha[0] = alpha[0] + 0.2f;

                        if (newAlpha[0] <= 1) {
                            genreTopTitle.setAlpha(newAlpha[0]);
                            genreMainTitle.setAlpha(1 - newAlpha[0]);
                            alpha[0] = newAlpha[0];
                        }
                    }
                });
    }
}
