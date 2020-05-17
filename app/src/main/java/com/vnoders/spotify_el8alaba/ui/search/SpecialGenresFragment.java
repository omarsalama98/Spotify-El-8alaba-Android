package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.GridSpacingItemDecoration;
import com.vnoders.spotify_el8alaba.Lists_Adapters.RecentlyPlayedListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.HomePlaylist;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialGenresFragment extends Fragment {

    private RecyclerView topPlaylistsRecyclerView;
    private RecyclerView categoriesGridRecyclerView;
    private ImageView backButton;
    private TextView specialGenreTopTitle;
    private TextView specialGenreMainTitle;
    private AppBarLayout appBar;
    public SpecialGenresFragment() {
        // Required empty public constructor
    }

    private int getGridSpacing() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width / 25;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_special_genres, container, false);

        topPlaylistsRecyclerView = root
                .findViewById(R.id.special_genre_top_playlists_recycler_view);
        categoriesGridRecyclerView = root.findViewById(R.id.special_genre_categories_grid_view);
        specialGenreTopTitle = root.findViewById(R.id.special_genre_fragment_top_title);
        specialGenreMainTitle = root.findViewById(R.id.special_genre_fragment_main_title);
        appBar = root.findViewById(R.id.special_genre_fragment_appbar);
        backButton = root.findViewById(R.id.special_genre_fragment_back_arrow);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String title = arguments.getString(SearchByTypeConstantsHelper.GENRE_NAME_KEY);
        specialGenreMainTitle.setText(title);
        specialGenreTopTitle.setText(title);

        String id = arguments.getString(SearchByTypeConstantsHelper.GENRE_ID_KEY);

        topPlaylistsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        APIInterface apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            specialGenreMainTitle
                    .setAlpha(1.0f + 2.0f * verticalOffset / appBarLayout.getTotalScrollRange());
            specialGenreTopTitle
                    .setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());
        });
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        Call<List<HomePlaylist>> call2 = apiService
                .getCategoryPlaylists(id);

        ArrayList<HomePlaylist> recentlyPlayedList = new ArrayList<>();

        RecentlyPlayedListAdapter recentlyPlayedListAdapter = new RecentlyPlayedListAdapter(
                SpecialGenresFragment.this, recentlyPlayedList);

        topPlaylistsRecyclerView.setAdapter(recentlyPlayedListAdapter);

        call2.enqueue(new Callback<List<HomePlaylist>>() {
            @Override
            public void onResponse(Call<List<HomePlaylist>> call,
                    Response<List<HomePlaylist>> response) {
                recentlyPlayedList.addAll(response.body());
                recentlyPlayedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<HomePlaylist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });

        int spacingInPixels = getGridSpacing();

        categoriesGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoriesGridRecyclerView
                .addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));

        Call<List<Category>> call = apiService.getTopCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, response.body().get(0).getName());
                categoriesGridRecyclerView
                        .setAdapter(
                                new SearchGenresGridAdapter((ArrayList<Category>) response.body(),
                                        SpecialGenresFragment.this));
                // topCategories[0] will be put in the adapter
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories");
            }
        });
    }
}
