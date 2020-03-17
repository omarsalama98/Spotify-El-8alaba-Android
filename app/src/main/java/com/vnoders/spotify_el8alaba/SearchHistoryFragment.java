package com.vnoders.spotify_el8alaba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchHistoryListAdapter;
import java.util.ArrayList;

public class SearchHistoryFragment extends Fragment {

    RecyclerView search_history_recycler_view;
    ArrayList<SearchListItem> mySearchHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_history, container, false);

        mySearchHistory = new ArrayList<SearchListItem>();
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));

        search_history_recycler_view = root.findViewById(R.id.search_history_list_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        search_history_recycler_view.setLayoutManager(layoutManager);

        SearchHistoryListAdapter searchHistoryListAdapter = new SearchHistoryListAdapter(
                mySearchHistory);
        search_history_recycler_view.setAdapter(searchHistoryListAdapter);

        return root;
    }
}
