package com.vnoders.spotify_el8alaba.ui.search;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnScrollChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchHistoryListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;


public class SearchFragment extends Fragment {


    RecyclerView search_history_recycler_view;
    ArrayList<SearchListItem> mySearchHistory;
    TextView clearRecentSearches;
    private SearchViewModel searchViewModel;
    private EditText searchQuery;
    private RecyclerView searchListRecyclerView;
    private LayoutManager layoutManager;
    private SearchListAdapter searchListAdapter;
    private BottomNavigationView botNavView;
    private RelativeLayout searchTextViewLayout;
    private LinearLayout searchEditTextLayout;
    private ImageView backArrow;
    private ImageView cameraInEditText;
    private ImageView cameraInTextView;
    private ImageView resetSearch;
    private TextView searchTextView;
    private LinearLayout searchHistoryListLayout;
    private RelativeLayout searchMainBackground;
    private ScrollView searchResultListLayout;

    private ArrayList<SearchListItem> getMockSearchData() {

        ArrayList<SearchListItem> myList = new ArrayList<>();
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("DD xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));

        return myList;
    }

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.onPause();
    }

    @RequiresApi(api = VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        cameraInTextView = root.findViewById(R.id.search_camera_image_in_text_view);
        cameraInEditText = root.findViewById(R.id.search_camera_image_in_edit_text);
        //TODO: Add Camera functionality

        searchTextView = root.findViewById(R.id.search_bar_text_view);
        resetSearch = root.findViewById(R.id.reset_search_image);
        backArrow = root.findViewById(R.id.search_bar_back_arrow);
        searchTextViewLayout = root.findViewById(R.id.search_text_layout);
        searchEditTextLayout = root.findViewById(R.id.search_edit_text_layout);
        searchQuery = root.findViewById(R.id.search_bar_edit_text);
        botNavView = getActivity().findViewById(R.id.nav_view);
        searchListRecyclerView = root.findViewById(R.id.search_list_recycler_view);
        searchMainBackground = root.findViewById(R.id.search_main_background_layout);
        searchResultListLayout = root.findViewById(R.id.search_result_list_layout);

        layoutManager = new LinearLayoutManager(getContext());
        searchListAdapter = new SearchListAdapter(getMockSearchData());

        searchListRecyclerView.setLayoutManager(layoutManager);
        searchListRecyclerView.setAdapter(searchListAdapter);

        searchHistoryListLayout = root.findViewById(R.id.search_history_list_container);
        search_history_recycler_view = root.findViewById(R.id.search_history_list_recycler_view);
        clearRecentSearches = root.findViewById(R.id.clear_recent_searches_text_view);

        mySearchHistory = new ArrayList<SearchListItem>();
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        search_history_recycler_view.setLayoutManager(layoutManager);

        clearRecentSearches.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mySearchHistory.clear();
                searchMainBackground.setVisibility(View.VISIBLE);
                searchHistoryListLayout.setVisibility(View.GONE);
            }
        });

        SearchHistoryListAdapter searchHistoryListAdapter = new SearchHistoryListAdapter(
                mySearchHistory);
        search_history_recycler_view.setAdapter(searchHistoryListAdapter);

        if (!mySearchHistory.isEmpty()) {
            searchHistoryListLayout.setVisibility(View.VISIBLE);
            searchMainBackground.setVisibility(View.GONE);
        } else {
            searchHistoryListLayout.setVisibility(View.GONE);
            searchMainBackground.setVisibility(View.VISIBLE);
        }

        backArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                getActivity().onBackPressed();
            }
        });

        searchTextView.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTextViewLayout.setVisibility(View.GONE);
                searchQuery.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }));

        searchResultListLayout.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                    int oldScrollY) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });

        resetSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            botNavView.setVisibility(View.GONE);
                            searchTextViewLayout.setVisibility(View.GONE);
                            searchEditTextLayout.setVisibility(View.VISIBLE);
                        } else {
                            botNavView.setVisibility(View.VISIBLE);
                            if (searchResultListLayout.getVisibility() != View.VISIBLE) {
                                searchTextViewLayout.setVisibility(View.VISIBLE);
                                searchEditTextLayout.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchResultListLayout.setVisibility(View.VISIBLE);
                    searchMainBackground.setVisibility(View.GONE);
                    searchHistoryListLayout.setVisibility(View.GONE);
                    resetSearch.setVisibility(View.VISIBLE);
                } else {
                    searchResultListLayout.setVisibility(View.GONE);
                    if (!mySearchHistory.isEmpty()) {
                        searchMainBackground.setVisibility(View.GONE);
                        searchHistoryListLayout.setVisibility(View.VISIBLE);
                    } else {
                        searchMainBackground.setVisibility(View.VISIBLE);
                        searchHistoryListLayout.setVisibility(View.GONE);
                    }
                    resetSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
    }

}