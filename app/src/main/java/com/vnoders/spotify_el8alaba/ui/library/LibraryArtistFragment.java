package com.vnoders.spotify_el8alaba.ui.library;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import java.util.List;

public class LibraryArtistFragment extends Fragment {

    private static final int REQUEST_ADD_ARTISTS = 1;
    public static final String INTENT_DATA_FOLLOWED_ARTISTS_IDS = "ids";

    private ProgressBar progressBar;
    private LibraryArtistViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(LibraryArtistViewModel.class);

        View root = inflater.inflate(R.layout.fragment_library_artist, container, false);

        progressBar = root.findViewById(R.id.progress_bar);

        LibraryArtistAdapter artistAdapter = new LibraryArtistAdapter(
                requireActivity().getSupportFragmentManager());

        RecyclerView recyclerView = root.findViewById(R.id.library_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getUserArtists().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                artistAdapter.submitList(artists);
                progressBar.setVisibility(View.GONE);
            }
        });

        View addArtists = root.findViewById(R.id.add_artists);

        addArtists.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddArtistActivity.class);
                startActivityForResult(intent, REQUEST_ADD_ARTISTS);
            }
        });

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        viewModel.requestUserFollowedArtists();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ADD_ARTISTS && resultCode == RESULT_OK && data != null) {
            List<String> followedArtistsIds = data
                    .getStringArrayListExtra(INTENT_DATA_FOLLOWED_ARTISTS_IDS);
            viewModel.followArtists(followedArtistsIds);
        }
    }
}
