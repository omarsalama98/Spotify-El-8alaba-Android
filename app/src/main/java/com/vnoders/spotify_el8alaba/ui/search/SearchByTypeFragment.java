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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByTypeFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView searchByTypeResultsRecyclerView;
    private String searchQuery;
    private String searchType;
    private TextView searchByTypeTitleTextView;
    private APIInterface apiService;
    private ArrayList<Object> searchResults;
    private SearchListAdapter mSearchListAdapter;

    public SearchByTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_by_type, container, false);

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        Bundle arguments = getArguments();
        searchQuery = arguments.getString(SearchByTypeConstantsHelper.SEARCH_QUERY_KEY);
        searchType = arguments.getString(SearchByTypeConstantsHelper.SEARCH_TYPE_KEY);

        searchByTypeTitleTextView = root.findViewById(R.id.search_by_type_fragment_title);
        toolbar = root.findViewById(R.id.search_by_type_fragment_toolbar);
        searchByTypeResultsRecyclerView = root
                .findViewById(R.id.search_by_type_results_recycler_view);
        searchResults = new ArrayList<>();
        mSearchListAdapter = new SearchListAdapter(searchResults, this);

        searchByTypeResultsRecyclerView
                .setAdapter(mSearchListAdapter);
        searchByTypeResultsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        String titleText = "\"" + searchQuery + "\"" + " in " + searchType;
        searchByTypeTitleTextView.setText(titleText);

        Call<List<Object>> call;

        switch (searchType) {
            case SearchByTypeConstantsHelper.ALBUMS:
                call = apiService.getAlbumsOfSearch(searchQuery);
                call.enqueue(new Callback<List<Object>>() {
                    @Override
                    public void onResponse(Call<List<Object>> call,
                            Response<List<Object>> response) {
                        searchResults.addAll(response.body());
                        mSearchListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Object>> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
            case SearchByTypeConstantsHelper.SONGS:
                call = apiService.getTracksOfSearch(searchQuery);
                call.enqueue(new Callback<List<Object>>() {
                    @Override
                    public void onResponse(Call<List<Object>> call,
                            Response<List<Object>> response) {
                        searchResults.addAll(response.body());
                        mSearchListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Object>> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
            case SearchByTypeConstantsHelper.ARTISTS:
                call = apiService.getArtistsOfSearch(searchQuery);
                call.enqueue(new Callback<List<Object>>() {
                    @Override
                    public void onResponse(Call<List<Object>> call,
                            Response<List<Object>> response) {
                        searchResults.addAll(response.body());
                        mSearchListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Object>> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
            case SearchByTypeConstantsHelper.PLAYLISTS:
                call = apiService.getPlaylistsOfSearch(searchQuery);
                call.enqueue(new Callback<List<Object>>() {
                    @Override
                    public void onResponse(Call<List<Object>> call,
                            Response<List<Object>> response) {
                        searchResults.addAll(response.body());
                        mSearchListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Object>> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
        }


    }
}
