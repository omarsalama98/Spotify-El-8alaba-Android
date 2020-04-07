package com.vnoders.spotify_el8alaba.ui.search;

import android.os.Bundle;
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
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;

public class SearchByTypeFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView searchByTypeResultsRecyclerView;
    private String searchQuery;
    private String searchType;
    private TextView searchByTypeTitleTextView;
    private APIInterface apiService;

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

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        String titleText = "\"" + searchQuery + "\"" + " in " + searchType;
        searchByTypeTitleTextView.setText(titleText);

        /*TODO:Remove Comments when backend is functional and Decide Which Service to call from SearchType
        final ArrayList[] playlists = new ArrayList[]{new ArrayList<Playlist>()};

        Call<List<Playlist>> call = apiService.getPLaylistsofSearch(searchQuery);
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call,
                    Response<List<Playlist>> response) {
                Log.d(TAG, response.body().get(0).getName());
                playlists[0] = (ArrayList<Playlist>) response.body();
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "failed to retrieve playlists");
            }
        });
        */

        searchByTypeResultsRecyclerView
                .setAdapter(new SearchListAdapter(Mock.getMockSearchData(), this));
        searchByTypeResultsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }
}
