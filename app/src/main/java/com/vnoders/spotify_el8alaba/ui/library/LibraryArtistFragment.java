package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import java.util.List;

public class LibraryArtistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        LibraryArtistViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(LibraryArtistViewModel.class);

        View root = inflater.inflate(R.layout.fragment_library_artist, container, false);

        LibraryArtistAdapter artistAdapter = new LibraryArtistAdapter();

        RecyclerView recyclerView = root.findViewById(R.id.library_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.requestUserFollowedArtists();

        viewModel.getUserArtists().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                artistAdapter.submitList(artists);
            }
        });

        View addArtists = root.findViewById(R.id.add_artists);

        addArtists.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddArtistActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
