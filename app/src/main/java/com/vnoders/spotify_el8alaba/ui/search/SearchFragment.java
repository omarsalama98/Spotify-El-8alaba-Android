package com.vnoders.spotify_el8alaba.ui.search;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.vnoders.spotify_el8alaba.MainActivity.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchHistoryListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.SearchListAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import com.vnoders.spotify_el8alaba.models.SearchResult;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment implements OnClickListener, TextWatcher {

    public static ArrayList<RecentSearches> mySearchHistory;
    private EditText searchQuery;
    private BottomNavigationView botNavView;
    private RelativeLayout searchTextViewLayout;
    private RelativeLayout searchMainBackground;
    private LinearLayout searchEditTextLayout;
    private ImageView resetSearch;
    private static NestedScrollView searchHistoryListLayout;
    private static RelativeLayout searchEmptyBackground;
    private RecyclerView searchListRecyclerView;
    private RecyclerView searchHistoryRecyclerView;
    private ImageView backArrow;
    private ImageView cameraInTextView;
    private ImageView cameraInEditText;
    private APIInterface apiService;
    private ScrollView searchResultListLayout;
    private TextView searchTextView, searchAllArtistsTextView,
            searchAllSongsTextView,
            searchAllPlaylistsTextView, searchAllAlbumsTextView,
            searchAllProfilesTextView; //searchAllGenresAndMoodsTextView
    private ArrayList<Object> searchResults;
    private SearchListAdapter searchListAdapter;
    private Bitmap firstSearchResult;

    private void getData() {
        class GetData extends AsyncTask<Void, Void, ArrayList<RecentSearches>> {

            @Override
            protected ArrayList<RecentSearches> doInBackground(Void... voids) {
                return (ArrayList<RecentSearches>) db.recentSearchesDao().getAll();
            }

            @Override
            protected void onPostExecute(ArrayList<RecentSearches> recentSearches) {
                SearchHistoryListAdapter searchHistoryListAdapter = new SearchHistoryListAdapter(
                        recentSearches, SearchFragment.this);
                searchHistoryRecyclerView.setAdapter(searchHistoryListAdapter);
                mySearchHistory = recentSearches;
                if (!mySearchHistory.isEmpty()
                        && searchResultListLayout.getVisibility() == View.GONE) {
                    showSearchHistoryList();
                }
                super.onPostExecute(recentSearches);
            }
        }
        GetData getData = new GetData();
        getData.execute();
    }

    /**
     * Returns two results from each type of search results(Two albums, two playlists, ...etc.)
     *
     * @param searchListResults A List containing Lists of (Albums, Playlists, ...etc.)
     *
     * @return A List containing the first two items of each Inner List(If they exist)
     */
    private ArrayList<Object> getTwosOfEach(SearchResult searchListResults) {

        ArrayList<Object> mSearchResult = new ArrayList<>();
        if (searchListResults != null) {
            List<SearchPlaylist> playlists = searchListResults.getPlaylists();
            if (playlists.size() == 1) {
                mSearchResult.add(playlists.get(0));
            } else if (playlists.size() > 1) {
                mSearchResult.add(playlists.get(0));
                mSearchResult.add(playlists.get(1));
            }
            List<SearchAlbum> albums = searchListResults.getAlbums();
            if (albums.size() == 1) {
                mSearchResult.add(albums.get(0));
            } else if (albums.size() > 1) {
                mSearchResult.add(albums.get(0));
                mSearchResult.add(albums.get(1));
            }
            List<SearchArtist> artists = searchListResults.getArtists();
            if (artists.size() == 1) {
                mSearchResult.add(artists.get(0));
            } else if (artists.size() > 1) {
                mSearchResult.add(artists.get(0));
                mSearchResult.add(artists.get(1));
            }
            List<SearchTrack> tracks = searchListResults.getTracks();
            if (tracks.size() == 1) {
                mSearchResult.add(tracks.get(0));
            } else if (tracks.size() > 1) {
                mSearchResult.add(tracks.get(0));
                mSearchResult.add(tracks.get(1));
            }
            List<User> users = searchListResults.getUsers();
            if (users.size() == 1) {
                mSearchResult.add(users.get(0));
            } else if (users.size() > 1) {
                mSearchResult.add(users.get(0));
                mSearchResult.add(users.get(1));
            }
        }
        return mSearchResult;
    }

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.onPause();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupUI() {
        // Set up touch listener for non-text box views to hide keyboard.
        searchResultListLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeKeyboard();
                return false;
            }
        });
    }

    private static void showSearchHistoryList() {
        searchHistoryListLayout.setVisibility(View.VISIBLE);
        searchEmptyBackground.setVisibility(View.GONE);
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
        /*searchAllGenresAndMoodsTextView
                .setTransitionName(SearchByTypeConstantsHelper.GENRES_AND_MOODS);
        searchAllGenresAndMoodsTextView.setOnClickListener(this);
         */
        searchAllProfilesTextView.setTransitionName(SearchByTypeConstantsHelper.PROFILES);
        searchAllProfilesTextView.setOnClickListener(this);
    }

    private void showTextViews() {
        searchAllArtistsTextView.setVisibility(View.VISIBLE);
        searchAllSongsTextView.setVisibility(View.VISIBLE);
        searchAllPlaylistsTextView.setVisibility(View.VISIBLE);
        searchAllAlbumsTextView.setVisibility(View.VISIBLE);
        /*
        searchAllGenresAndMoodsTextView.setVisibility(View.VISIBLE);
         */
        searchAllProfilesTextView.setVisibility(View.VISIBLE);
    }

    private void hideTextViews() {
        searchAllArtistsTextView.setVisibility(View.GONE);
        searchAllSongsTextView.setVisibility(View.GONE);
        searchAllPlaylistsTextView.setVisibility(View.GONE);
        searchAllAlbumsTextView.setVisibility(View.GONE);
        /*
        searchAllGenresAndMoodsTextView.setVisibility(View.GONE);
         */
        searchAllProfilesTextView.setVisibility(View.GONE);
    }

    public static void removeSearchHistoryList() {
        searchHistoryListLayout.setVisibility(View.GONE);
        searchEmptyBackground.setVisibility(View.VISIBLE);
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

       /* searchAllGenresAndMoodsTextView = root
                .findViewById(R.id.see_all_genres_and_moods_text_view);
        */
        searchAllProfilesTextView = root.findViewById(R.id.see_all_profiles_text_view);

        searchViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        mySearchHistory = new ArrayList<>();
        return root;
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

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI();
        getData();
        searchQuery.requestFocus();
        openKeyboard();

        handleTextViewsActions();

        searchResults = new ArrayList<>();

        searchListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new SearchListAdapter(searchResults, this);
        searchListRecyclerView.setAdapter(searchListAdapter);

        searchHistoryListLayout = view.findViewById(R.id.search_history_list_container);
        searchHistoryRecyclerView = view.findViewById(R.id.search_history_list_recycler_view);
        TextView clearRecentSearches = view.findViewById(R.id.clear_recent_searches_text_view);

        searchHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clearRecentSearches.setOnClickListener(v -> {
            db.recentSearchesDao().nukeTable();
            removeSearchHistoryList();
        });

        backArrow.setOnClickListener(v -> {
            closeKeyboard();
            getActivity().onBackPressed();
        });

        searchTextView.setOnClickListener((v -> {
            searchTextViewLayout.setVisibility(View.GONE);
            searchEditTextLayout.setVisibility(View.VISIBLE);
            searchQuery.requestFocus();
            openKeyboard();
        }));

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

        searchQuery.addTextChangedListener(this);
    }

    /**
     * OnClick Method for searching by a certain type: artists, playlists, ...etc.
     */
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {

            searchResultListLayout.setVisibility(View.VISIBLE);
            searchEmptyBackground.setVisibility(View.GONE);
            searchHistoryListLayout.setVisibility(View.GONE);
            resetSearch.setVisibility(View.VISIBLE);
            cameraInEditText.setVisibility(View.GONE);
            hideTextViews();

            //*******  This populates the list every time the user types a letter inside search bar  ********/

            Call<SearchResult> call = apiService.getAllOfSearch(s.toString());
            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Call<SearchResult> call,
                        Response<SearchResult> response) {
                    searchResults.clear();
                    searchResults.addAll(getTwosOfEach(response.body()));
                    searchListAdapter.notifyDataSetChanged();
                    showTextViews();
                }

                @Override
                public void onFailure(Call<SearchResult> call, Throwable t) {
                    Log.d(TAG, "failed to retrieve search items" + t.getMessage());
                }
            });
            //setSearchMainBackgroundColor(colorOfFirstSearchResultImage);

        } else {
            searchResultListLayout.setVisibility(View.GONE);
            searchResults.clear();
            searchListAdapter.notifyDataSetChanged();
            if (!mySearchHistory.isEmpty()) {
                showSearchHistoryList();
            } else {
                removeSearchHistoryList();
            }
            resetSearch.setVisibility(View.GONE);
            cameraInEditText.setVisibility(View.VISIBLE);
        }
    }
}