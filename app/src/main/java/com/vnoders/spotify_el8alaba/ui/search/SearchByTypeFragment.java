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
import com.vnoders.spotify_el8alaba.models.Search.Albums;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.Playlists;
import com.vnoders.spotify_el8alaba.models.Search.Tracks;
import com.vnoders.spotify_el8alaba.models.Search.Users;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A {@link Fragment} for showing search results for a certain type.
 */
public class SearchByTypeFragment extends Fragment {

    private Toolbar toolbar;
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
        RecyclerView searchByTypeResultsRecyclerView = root
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

        switch (searchType) {
            case SearchByTypeConstantsHelper.ALBUMS:
                Call<Albums> albumsCall;
                albumsCall = apiService.getAlbumsOfSearch(searchQuery);
                albumsCall.enqueue(new Callback<Albums>() {
                    @Override
                    public void onResponse(Call<Albums> call,
                            Response<Albums> response) {
                        if (response.body() != null) {
                            searchResults.addAll(response.body().getAlbums());
                            mSearchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Albums> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve Albums " + t.getMessage());
                    }
                });
                break;
            case SearchByTypeConstantsHelper.SONGS:
                Call<Tracks> tracksCall;
                tracksCall = apiService.getTracksOfSearch(searchQuery);
                tracksCall.enqueue(new Callback<Tracks>() {
                    @Override
                    public void onResponse(Call<Tracks> call,
                            Response<Tracks> response) {
                        if (response.body() != null) {
                            searchResults.addAll(response.body().getTracks());
                            mSearchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Tracks> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists " + t.getMessage());
                    }
                });
                break;
            case SearchByTypeConstantsHelper.ARTISTS:
                Call<Artists> artistsCall;
                artistsCall = apiService.getArtistsOfSearch(searchQuery);
                artistsCall.enqueue(new Callback<Artists>() {
                    @Override
                    public void onResponse(Call<Artists> call,
                            Response<Artists> response) {
                        if (response.body() != null) {
                            searchResults.addAll(response.body().getArtists());
                            mSearchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Artists> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists " + t.getMessage());
                    }
                });
                break;
            case SearchByTypeConstantsHelper.PLAYLISTS:
                Call<Playlists> playlistsCall;
                playlistsCall = apiService.getPlaylistsOfSearch(searchQuery);
                playlistsCall.enqueue(new Callback<Playlists>() {
                    @Override
                    public void onResponse(Call<Playlists> call,
                            Response<Playlists> response) {
                        if (response.body() != null) {
                            searchResults.addAll(response.body().getPlaylists());
                            mSearchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Playlists> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
            case SearchByTypeConstantsHelper.PROFILES:
                Call<Users> usersCall;
                usersCall = apiService.getUsersOfSearch(searchQuery);
                usersCall.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call,
                            Response<Users> response) {
                        if (response.body() != null) {
                            searchResults.addAll(response.body().getUsers());
                            mSearchListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Log.d(TAG, "failed to retrieve playlists");
                    }
                });
                break;
        }


    }
}
