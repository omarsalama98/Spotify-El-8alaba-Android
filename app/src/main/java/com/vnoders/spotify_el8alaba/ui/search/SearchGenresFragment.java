package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
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
public class SearchGenresFragment extends Fragment {

    private RecyclerView browseAllGenresGridView;
    private RecyclerView topGenresGridView;
    private TextView genresSearchTextLayout;

    public SearchGenresFragment() {
        // Required empty public constructor
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_search) {
            navView.setSelectedItemId(R.id.navigation_search);
        }

        //TODO: UnComment and use it instead of mock data when backend is populated.
        APIInterface apiService =
                RetrofitClient.getInstance().getAPI(APIInterface.class);

        final ArrayList[] topCategories = new ArrayList[]{new ArrayList<>()};

        Call<List<Category>> call = apiService.getTopCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, response.body().get(0).getName());
                topCategories[0] = (ArrayList<Category>) response.body();
                    // topCategories[0] will be put in the adapter
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories");
            }
        });


        topGenresGridView
                .setAdapter(new SearchGenresGridAdapter(this, topCategories[0]));
        topGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        /*TODO: UnComment and use it instead of mock data when backend is populated.
        APIInterface apiService =
                RetrofitClient.getInstance().getAPI(APIInterface.class);

        final ArrayList[] allCategories = new ArrayList[]{new ArrayList<>()};

        Call<List<Category>> call = apiService.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, response.body().get(0).getName());
                allCategories[0] = (ArrayList<Category>) response.body();
                    // allCategories[0] will be put in the adapter
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve Categories");
            }
        });*/

        browseAllGenresGridView
                .setAdapter(new SearchGenresGridAdapter(this, Mock.getAllGenres(this)));
        browseAllGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        genresSearchTextLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_right)
                        .replace(R.id.nav_host_fragment, new SearchFragment())
                        .addToBackStack("search")
                        .commit();
            }
        });
    }

}
