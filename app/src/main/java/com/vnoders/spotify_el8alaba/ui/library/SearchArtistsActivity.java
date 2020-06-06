package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtistsActivity extends AppCompatActivity implements TextWatcher {

    private EditText searchQuery;
    private RelativeLayout searchTextViewLayout;
    private LinearLayout searchEditTextLayout;
    private ImageView resetSearch;
    private RelativeLayout searchEmptyBackground;
    private RecyclerView searchListRecyclerView;
    private ImageView backArrow;
    private APIInterface apiService;
    private TextView searchTextView;
    private ArrayList<SearchArtist> searchResults;
    private SearchArtistsListAdapter searchListAdapter;

    //    private void setupUI(View view, View root) {
    private void setupUI() {
        searchListRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    closeKeyboard();
                }
            }
        });

    }

    /**
     * Toggles soft keyboard
     */
    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Hides Soft Keyboard
     */
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


    @Override
    public void onPause() {
        closeKeyboard();
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_artists);

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        searchTextView = findViewById(R.id.search_bar_text_view);
        resetSearch = findViewById(R.id.reset_search_image);
        backArrow = findViewById(R.id.search_bar_back_arrow);
        searchQuery = findViewById(R.id.search_bar_edit_text);
        searchListRecyclerView = findViewById(R.id.search_artists_list_recycler_view);
        searchEmptyBackground = findViewById(R.id.search_empty_background_layout);
        searchEditTextLayout = findViewById(R.id.search_edit_text_layout);
        searchTextViewLayout = findViewById(R.id.search_text_layout);

//        setupUI(view, view);
        setupUI();
        searchQuery.requestFocus();
        openKeyboard();

        searchResults = new ArrayList<>();

        searchListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchListAdapter = new SearchArtistsListAdapter(searchResults, this);
        searchListRecyclerView.setAdapter(searchListAdapter);

        backArrow.setOnClickListener(v -> {
            closeKeyboard();
            onBackPressed();
        });

        searchTextView.setOnClickListener((v -> {
            searchTextViewLayout.setVisibility(View.GONE);
            searchEditTextLayout.setVisibility(View.VISIBLE);
            searchQuery.requestFocus();
            openKeyboard();
        }));

        resetSearch.setOnClickListener(v -> searchQuery.setText(""));

        KeyboardVisibilityEvent.setEventListener(
                this, isOpen -> {
                    if (isOpen) {
                        searchTextViewLayout.setVisibility(View.GONE);
                        searchEditTextLayout.setVisibility(View.VISIBLE);
                    } else if (searchListRecyclerView.getVisibility() != View.VISIBLE) {
                        searchTextViewLayout.setVisibility(View.VISIBLE);
                        searchEditTextLayout.setVisibility(View.GONE);
                    }

                });

        searchQuery.addTextChangedListener(this);
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

            searchListRecyclerView.setVisibility(View.VISIBLE);
            searchEmptyBackground.setVisibility(View.GONE);
            resetSearch.setVisibility(View.VISIBLE);

            Call<Artists> call = apiService.getArtistsOfSearch(s.toString());
            call.enqueue(new Callback<Artists>() {
                @Override
                public void onResponse(Call<Artists> call, Response<Artists> response) {
                    searchResults.clear();
                    searchResults.addAll(response.body().getArtists());
                    searchListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Artists> call, Throwable t) {
                    Log.d("Search Artists Activity", "failed to load tracks" + t.getMessage());
                }
            });

        } else {
            searchListRecyclerView.setVisibility(View.GONE);
            searchResults.clear();
            searchEmptyBackground.setVisibility(View.VISIBLE);
            searchListAdapter.notifyDataSetChanged();
            resetSearch.setVisibility(View.GONE);
        }
    }
}