package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.Lists_Adapters.GenrePlaylistsGridAdapter;
import com.vnoders.spotify_el8alaba.R;
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
public class GenreFragment extends Fragment {

    private RecyclerView genrePlaylistsGridView;
    private ImageView backArrow;
    private TextView genreTopTitle;
    private TextView genreMainTitle;
    private AppBarLayout appBar;

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_genre, container, false);
        genrePlaylistsGridView = root.findViewById(R.id.genre_playlists_grid_view);
        backArrow = root.findViewById(R.id.genre_fragment_back_arrow);
        genreTopTitle = root.findViewById(R.id.genre_fragment_top_title);
        genreMainTitle = root.findViewById(R.id.genre_fragment_main_title);
        appBar = root.findViewById(R.id.genre_fragment_appbar);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String title = arguments.getString(SearchByTypeConstantsHelper.GENRE_NAME_KEY);
        genreMainTitle.setText(title);
        genreTopTitle.setText(title);

        String id = arguments.getString(SearchByTypeConstantsHelper.GENRE_ID_KEY);

        backArrow.setOnClickListener(v -> getActivity().onBackPressed());

        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            genreMainTitle
                    .setAlpha(1.0f + 2.0f * verticalOffset / appBarLayout.getTotalScrollRange());
            genreTopTitle.setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());
        });

        APIInterface apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        Call<List<HomePlaylist>> call = apiService
                .getCategoryPlaylists(id);

        ArrayList<HomePlaylist> myDataList = new ArrayList<>();

        GenrePlaylistsGridAdapter adapter = new GenrePlaylistsGridAdapter(myDataList, this);
        genrePlaylistsGridView
                .setAdapter(adapter);
        genrePlaylistsGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        call.enqueue(new Callback<List<HomePlaylist>>() {
            @Override
            public void onResponse(Call<List<HomePlaylist>> call,
                    Response<List<HomePlaylist>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    myDataList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<HomePlaylist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });


    }
}
