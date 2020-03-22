package com.vnoders.spotify_el8alaba.ui.search;

import android.content.Context;
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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.SearchListAdapter;
import com.vnoders.spotify_el8alaba.SearchListItem;
import java.util.ArrayList;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private EditText search_query;
    private RecyclerView search_list_recycler_view;
    private LayoutManager layoutManager;
    private SearchListAdapter searchListAdapter;
    private BottomNavigationView bot_nav_view;
    private RelativeLayout search_text_view_layout;
    private LinearLayout search_edit_text_layout;
    private ImageView back_arrow;
    private ImageView camera_in_edit_text;
    private ImageView camera_in_text_view;
    private ImageView reset_search;
    private TextView search_text_view;
    Retrofit retrofit;

    private ArrayList<SearchListItem> getMockSearchData() {
        ArrayList<SearchListItem> myList = new ArrayList<>();
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("RER xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("DD xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));
        myList.add(new SearchListItem("LOL xd", "mad",
                "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4"));

        return myList;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_search, container, false);

        camera_in_text_view = root.findViewById(R.id.search_camera_image_in_text_view);
        camera_in_edit_text = root.findViewById(R.id.search_camera_image_in_edit_text);
        //TODO: Add Camera functionality

        search_text_view = root.findViewById(R.id.search_bar_text_view);
        reset_search = root.findViewById(R.id.reset_search_image);
        back_arrow = root.findViewById(R.id.search_bar_back_arrow);
        search_text_view_layout = root.findViewById(R.id.search_text_layout);
        search_edit_text_layout = root.findViewById(R.id.search_edit_text_layout);
        search_query = root.findViewById(R.id.search_bar_edit_text);
        bot_nav_view = getActivity().findViewById(R.id.nav_view);
        search_list_recycler_view = root.findViewById(R.id.search_list_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        searchListAdapter = new SearchListAdapter(getMockSearchData());

        search_list_recycler_view.setLayoutManager(layoutManager);
        search_list_recycler_view.setAdapter(searchListAdapter);

        back_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                getActivity().onBackPressed();
            }
        });

        search_text_view.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text_view_layout.setVisibility(View.GONE);
                search_query.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }));

        reset_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                search_query.setText("");
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            bot_nav_view.setVisibility(View.GONE);
                            search_text_view_layout.setVisibility(View.GONE);
                            search_edit_text_layout.setVisibility(View.VISIBLE);
                        } else {
                            bot_nav_view.setVisibility(View.VISIBLE);
                            if (search_list_recycler_view.getVisibility() != View.VISIBLE) {
                                search_text_view_layout.setVisibility(View.VISIBLE);
                                search_edit_text_layout.setVisibility(View.GONE);
                            }

                        }
                    }
                });

        search_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    search_list_recycler_view.setVisibility(View.VISIBLE);
                    reset_search.setVisibility(View.VISIBLE);
                } else {
                    search_list_recycler_view.setVisibility(View.GONE);
                    reset_search.setVisibility(View.GONE);
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

    @Override
    public void onPause() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.onPause();
    }

}