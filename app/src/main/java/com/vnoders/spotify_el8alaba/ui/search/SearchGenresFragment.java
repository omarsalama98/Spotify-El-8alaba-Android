package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.GridSpacingItemDecoration;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchGenresGridAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Search.Albums;
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
    private int REQUESTS_TBD = 3;
    private int requestsDone = 0;
    private View loadingView;
    private static ArrayList<Category> topCategories = new ArrayList<>();
    private static ArrayList<Category> otherCategories = new ArrayList<>();
    private static boolean fetchedRequest = false;

    public SearchGenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_genres, container, false);
        genresSearchTextLayout = v.findViewById(R.id.genres_search_bar_text_view);
        loadingView = v.findViewById(R.id.loading_view);

        topGenresGridView = v
                .findViewById(R.id.search_top_genres_gridview);
        browseAllGenresGridView = v
                .findViewById(R.id.search_browse_all_genres_gridview);

        return v;
    }

    /**
     * @return Spacing between grid items based on current mobile screen
     */
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

        if (fetchedRequest) {
            loadingView.setVisibility(View.GONE);
        }
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        if (navView.getSelectedItemId() != R.id.navigation_search) {
            navView.setSelectedItemId(R.id.navigation_search);
        }

        APIInterface apiService =
                RetrofitClient.getInstance().getAPI(APIInterface.class);

        int spacingInPixels = getGridSpacing();

        topGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        topGenresGridView
                .addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));
        SearchGenresGridAdapter topCategoriesAdapter = new SearchGenresGridAdapter(
                topCategories,
                SearchGenresFragment.this);
        topGenresGridView
                .setAdapter(topCategoriesAdapter);
        if (!fetchedRequest) {
            Call<List<Category>> call = apiService.getTopCategories();
            call.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call,
                        Response<List<Category>> response) {
                    requestsDone += 1;
                    if (response.body() != null) {
                        topCategories.addAll(response.body());
                        topCategoriesAdapter.notifyDataSetChanged();
                        // topCategories[0] will be put in the adapter
                    }
                    if (requestsDone == REQUESTS_TBD) {
                        loadingView.setVisibility(View.GONE);
                        fetchedRequest = true;
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Log.d(TAG, "failed to retrieve Categories");
                }
            });
        }

        browseAllGenresGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        browseAllGenresGridView
                .addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));
        SearchGenresGridAdapter otherCategoriesAdapter = new SearchGenresGridAdapter(
                otherCategories,
                SearchGenresFragment.this);
        browseAllGenresGridView
                .setAdapter(otherCategoriesAdapter);
        if (!fetchedRequest) {
            Call<Albums> call = apiService.getNewReleases();
            call.enqueue(new Callback<Albums>() {
                @Override
                public void onResponse(Call<Albums> call,
                        Response<Albums> response) {
                    requestsDone += 1;
                    if (response.body() != null) {
                        Category newReleases = new Category(
                                SearchByTypeConstantsHelper.NEW_RELEASES,
                                response.body().getAlbums());
                        if (otherCategories.size() > 2) {
                            otherCategories.add(2, newReleases);
                        } else {
                            otherCategories.add(newReleases);
                        }
                        otherCategoriesAdapter.notifyDataSetChanged();
                    }
                    if (requestsDone == REQUESTS_TBD) {
                        loadingView.setVisibility(View.GONE);
                        fetchedRequest = true;
                    }
                }

                @Override
                public void onFailure(Call<Albums> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            Call<List<Category>> call2 = apiService.getAllCategories();
            call2.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call,
                        Response<List<Category>> response) {
                    requestsDone += 1;
                    if (response.body() != null) {
                        otherCategories.addAll(response.body());
                        otherCategoriesAdapter.notifyDataSetChanged();
                        // allCategories[0] will be put in the adapter
                    }
                    if (requestsDone == REQUESTS_TBD) {
                        loadingView.setVisibility(View.GONE);
                        fetchedRequest = true;
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Log.d(TAG, "failed to retrieve Categories");
                }
            });
        }
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
