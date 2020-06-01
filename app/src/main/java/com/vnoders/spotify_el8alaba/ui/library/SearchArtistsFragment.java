package com.vnoders.spotify_el8alaba.ui.library;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.Artist;
import com.vnoders.spotify_el8alaba.models.Search.Artists;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchArtistsFragment extends Fragment implements TextWatcher {

    private EditText searchQuery;
    private BottomNavigationView botNavView;
    private RelativeLayout searchTextViewLayout;
    private LinearLayout searchEditTextLayout;
    private ImageView resetSearch;
    private RelativeLayout searchEmptyBackground;
    private RecyclerView searchListRecyclerView;
    private ImageView backArrow;
    private APIInterface apiService;
    private TextView searchTextView;
    private ArrayList<Artist> searchResults;
    private SearchArtistsListAdapter searchListAdapter;

    private void setupUI(View view, View root) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    closeKeyboard(root);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, root);
            }
        }
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

        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);

        View root = inflater.inflate(R.layout.fragment_search_artists, container, false);

        searchTextView = root.findViewById(R.id.search_bar_text_view);
        resetSearch = root.findViewById(R.id.reset_search_image);
        backArrow = root.findViewById(R.id.search_bar_back_arrow);
        searchQuery = root.findViewById(R.id.search_bar_edit_text);
        botNavView = requireActivity().findViewById(R.id.nav_view);
        searchListRecyclerView = root.findViewById(R.id.search_artists_list_recycler_view);
        searchEmptyBackground = root.findViewById(R.id.search_empty_background_layout);
        searchEditTextLayout = root.findViewById(R.id.search_edit_text_layout);
        searchTextViewLayout = root.findViewById(R.id.search_text_layout);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI(view, view);
        searchQuery.requestFocus();
        openKeyboard();

        searchResults = new ArrayList<>();

        searchListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchListAdapter = new SearchArtistsListAdapter(searchResults, this);
        searchListRecyclerView.setAdapter(searchListAdapter);

        backArrow.setOnClickListener(v -> {
            closeKeyboard(view);
            requireActivity().onBackPressed();
        });

        searchTextView.setOnClickListener((v -> {
            searchTextViewLayout.setVisibility(View.GONE);
            searchEditTextLayout.setVisibility(View.VISIBLE);
            searchQuery.requestFocus();
            openKeyboard();
        }));

        resetSearch.setOnClickListener(v -> searchQuery.setText(""));

        KeyboardVisibilityEvent.setEventListener(
                requireActivity(),
                isOpen -> {
                    if (isOpen) {
                        botNavView.setVisibility(View.GONE);
                        searchTextViewLayout.setVisibility(View.GONE);
                        searchEditTextLayout.setVisibility(View.VISIBLE);
                    } else {
                        botNavView.setVisibility(View.VISIBLE);
                        if (searchListRecyclerView.getVisibility() != View.VISIBLE) {
                            searchTextViewLayout.setVisibility(View.VISIBLE);
                            searchEditTextLayout.setVisibility(View.GONE);
                        }
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
                    Log.d(TAG, "failed to load tracks" + t.getMessage());
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