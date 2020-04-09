package com.vnoders.spotify_el8alaba.ui.search;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchHistoryListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.Mock;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;


public class SearchFragment extends Fragment implements OnClickListener {


    private ArrayList<SearchListItem> mySearchHistory;
    private EditText searchQuery;
    private BottomNavigationView botNavView;
    private RelativeLayout searchTextViewLayout;
    private RelativeLayout searchMainBackground;
    private LinearLayout searchEditTextLayout;
    private ImageView resetSearch;
    private LinearLayout searchHistoryListLayout;
    private RelativeLayout searchEmptyBackground;
    private RecyclerView searchListRecyclerView;
    private ImageView backArrow;
    private ImageView cameraInTextView;
    private ImageView cameraInEditText;
    private APIInterface apiService;
    private ScrollView searchResultListLayout;
    private TextView searchTextView, searchAllArtistsTextView,
            searchAllSongsTextView,
            searchAllPlaylistsTextView, searchAllAlbumsTextView,
            searchAllGenresAndMoodsTextView, searchAllProfilesTextView;

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.onPause();
    }

    /**
     * Hides Soft Keyboard
     *
     * @param view the current view where the keyboard is open
     */
    private void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Sets TextViews OnClickListeners and their transition names
     */
    private void handleTextViewsActions() {
        searchAllArtistsTextView.setTransitionName(SearchByTypeConstantsHelper.ARTISTS);
        searchAllArtistsTextView.setOnClickListener(this);
        searchAllSongsTextView.setTransitionName(SearchByTypeConstantsHelper.SONGS);
        searchAllSongsTextView.setOnClickListener(this);
        searchAllPlaylistsTextView.setTransitionName(SearchByTypeConstantsHelper.PLAYLISTS);
        searchAllPlaylistsTextView.setOnClickListener(this);
        searchAllAlbumsTextView.setTransitionName(SearchByTypeConstantsHelper.ALBUMS);
        searchAllAlbumsTextView.setOnClickListener(this);
        searchAllGenresAndMoodsTextView
                .setTransitionName(SearchByTypeConstantsHelper.GENRES_AND_MOODS);
        searchAllGenresAndMoodsTextView.setOnClickListener(this);
        searchAllProfilesTextView.setTransitionName(SearchByTypeConstantsHelper.PROFILES);
        searchAllProfilesTextView.setOnClickListener(this);
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

    private void setSearchMainBackgroundColor() {
        /*TODO:Get image of first search result and determine background based on it
        Palette palette = Palette.from().generate();

        ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
        mDrawable.getPaint().setShader
                (new LinearGradient(0,0,0,200,
                        palette.getDominantColor(0),
                        getResources().getColor(R.color.midGray),
                        TileMode.REPEAT));
        searchMainBackground.setBackground(mDrawable);*/
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        cameraInTextView = root.findViewById(R.id.search_camera_image_in_text_view);
        cameraInEditText = root.findViewById(R.id.search_camera_image_in_edit_text);
        //TODO: Add Camera functionality if it's required.

        searchTextView = root.findViewById(R.id.search_bar_text_view);
        resetSearch = root.findViewById(R.id.reset_search_image);
        backArrow = root.findViewById(R.id.search_bar_back_arrow);
        searchQuery = root.findViewById(R.id.search_bar_edit_text);
        botNavView = getActivity().findViewById(R.id.nav_view);
        searchListRecyclerView = root.findViewById(R.id.search_list_recycler_view);
        searchEmptyBackground = root.findViewById(R.id.search_empty_background_layout);
        searchResultListLayout = root.findViewById(R.id.search_result_list_layout);
        searchEditTextLayout = root.findViewById(R.id.search_edit_text_layout);
        searchTextViewLayout = root.findViewById(R.id.search_text_layout);
        searchAllArtistsTextView = root.findViewById(R.id.see_all_artists_text_view);
        searchAllSongsTextView = root.findViewById(R.id.see_all_songs_text_view);
        searchAllPlaylistsTextView = root.findViewById(R.id.see_all_playlists_text_view);
        searchAllAlbumsTextView = root.findViewById(R.id.see_all_albums_text_view);
        searchMainBackground = root.findViewById(R.id.search_main_background_layout);

        searchAllGenresAndMoodsTextView = root
                .findViewById(R.id.see_all_genres_and_moods_text_view);
        searchAllProfilesTextView = root.findViewById(R.id.see_all_profiles_text_view);

        searchViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchQuery.requestFocus();
        openKeyboard();

        handleTextViewsActions();

        LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        SearchListAdapter searchListAdapter = new SearchListAdapter(Mock.getMockSearchData(), this);

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
            searchEmptyBackground.setVisibility(View.VISIBLE);
            searchHistoryListLayout.setVisibility(View.GONE);
        });

        SearchHistoryListAdapter searchHistoryListAdapter = new SearchHistoryListAdapter(
                mySearchHistory);
        searchHistoryRecyclerView.setAdapter(searchHistoryListAdapter);

        if (!mySearchHistory.isEmpty()) {
            searchHistoryListLayout.setVisibility(View.VISIBLE);
            searchEmptyBackground.setVisibility(View.GONE);
        } else {
            searchHistoryListLayout.setVisibility(View.GONE);
            searchEmptyBackground.setVisibility(View.VISIBLE);
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
                    searchEmptyBackground.setVisibility(View.GONE);
                    searchHistoryListLayout.setVisibility(View.GONE);
                    resetSearch.setVisibility(View.VISIBLE);
                    cameraInEditText.setVisibility(View.GONE);

                    /*TODO:Remove Comments when backend is finished

                    *******  This populates the list every time the user types a letter inside search bar  *******

                    final ArrayList[] playlists = new ArrayList[]{new ArrayList<Playlist>()};

                    Call<List<Playlist>> call = apiService.getPLaylistsofSearch(s.toString());
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
                    setSearchMainBackgroundColor(colorOfFirstSearchResultImage);
                    */

                } else {
                    searchResultListLayout.setVisibility(View.GONE);
                    if (!mySearchHistory.isEmpty()) {
                        searchEmptyBackground.setVisibility(View.GONE);
                        searchHistoryListLayout.setVisibility(View.VISIBLE);
                    } else {
                        searchEmptyBackground.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        SearchByTypeFragment fragment = new SearchByTypeFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SearchByTypeConstantsHelper.SEARCH_TYPE_KEY, v.getTransitionName());
        arguments.putString(SearchByTypeConstantsHelper.SEARCH_QUERY_KEY,
                searchQuery.getText().toString());
        fragment.setArguments(arguments);
        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack("search")
                .commit();
    }
}