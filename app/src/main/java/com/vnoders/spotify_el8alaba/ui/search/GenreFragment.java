package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
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

        Bundle arguments = getArguments();
        String title = arguments.getString(SearchByTypeConstantsHelper.GENRE_NAME_KEY);
        genreMainTitle.setText(title);
        genreTopTitle.setText(title);

        String id = arguments.getString(SearchByTypeConstantsHelper.GENRE_ID_KEY);

        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

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
                myDataList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<HomePlaylist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Playlists" + t.getLocalizedMessage());
            }
        });

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
