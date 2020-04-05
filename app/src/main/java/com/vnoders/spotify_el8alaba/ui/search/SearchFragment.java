package com.vnoders.spotify_el8alaba.ui.search;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchHistoryListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;


public class SearchFragment extends Fragment {


    private ArrayList<SearchListItem> mySearchHistory;
    private EditText searchQuery;
    private BottomNavigationView botNavView;
    private RelativeLayout searchTextViewLayout;
    private LinearLayout searchEditTextLayout;
    private ImageView resetSearch;
    private LinearLayout searchHistoryListLayout;
    private RelativeLayout searchMainBackground;
    private ScrollView searchResultListLayout;
    private TextView searchTextView;
    private RecyclerView searchListRecyclerView;
    private ImageView backArrow;
    private ImageView cameraInTextView;
    private ImageView cameraInEditText;

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.onPause();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        cameraInTextView = root.findViewById(R.id.search_camera_image_in_text_view);
        cameraInEditText = root.findViewById(R.id.search_camera_image_in_edit_text);
        //TODO: Add Camera functionality

        searchTextView = root.findViewById(R.id.search_bar_text_view);
        resetSearch = root.findViewById(R.id.reset_search_image);
        backArrow = root.findViewById(R.id.search_bar_back_arrow);
        searchQuery = root.findViewById(R.id.search_bar_edit_text);
        botNavView = getActivity().findViewById(R.id.nav_view);
        searchListRecyclerView = root.findViewById(R.id.search_list_recycler_view);
        searchMainBackground = root.findViewById(R.id.search_main_background_layout);
        searchResultListLayout = root.findViewById(R.id.search_result_list_layout);
        searchEditTextLayout = root.findViewById(R.id.search_edit_text_layout);
        searchTextViewLayout = root.findViewById(R.id.search_text_layout);

        searchViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchQuery.requestFocus();
        openKeyboard();

        LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        SearchListAdapter searchListAdapter = new SearchListAdapter(Mock.getMockSearchData());

        searchListRecyclerView.setLayoutManager(layoutManager1);
        searchListRecyclerView.setAdapter(searchListAdapter);

        searchHistoryListLayout = view.findViewById(R.id.search_history_list_container);
        RecyclerView searchHistoryRecyclerView = view
                .findViewById(R.id.search_history_list_recycler_view);
        TextView clearRecentSearches = view.findViewById(R.id.clear_recent_searches_text_view);

        mySearchHistory = new ArrayList<>();
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));
        mySearchHistory.add(new SearchListItem("Zeft", "Ay neela", ""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchHistoryRecyclerView.setLayoutManager(layoutManager);

        clearRecentSearches.setOnClickListener(v -> {
            mySearchHistory.clear();
            searchMainBackground.setVisibility(View.VISIBLE);
            searchHistoryListLayout.setVisibility(View.GONE);
        });

        SearchHistoryListAdapter searchHistoryListAdapter = new SearchHistoryListAdapter(
                mySearchHistory);
        searchHistoryRecyclerView.setAdapter(searchHistoryListAdapter);

        if (!mySearchHistory.isEmpty()) {
            searchHistoryListLayout.setVisibility(View.VISIBLE);
            searchMainBackground.setVisibility(View.GONE);
        } else {
            searchHistoryListLayout.setVisibility(View.GONE);
            searchMainBackground.setVisibility(View.VISIBLE);
        }

        backArrow.setOnClickListener(v -> {
            closeKeyboard(view);
            getActivity().onBackPressed();
        });

        searchTextView.setOnClickListener((v -> {
            searchTextViewLayout.setVisibility(View.GONE);
            searchEditTextLayout.setVisibility(View.VISIBLE);
            searchQuery.requestFocus();
            openKeyboard();
        }));

        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            searchResultListLayout.setOnScrollChangeListener(
                    (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                        closeKeyboard(view);
                    });
        }

        resetSearch.setOnClickListener(v -> searchQuery.setText(""));

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                isOpen -> {
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
                    cameraInEditText.setVisibility(View.GONE);
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
                    cameraInEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Toggles soft keyboard
     */
    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * @param view the current view where the keyboard is open Hides Soft Keyboard
     */
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}