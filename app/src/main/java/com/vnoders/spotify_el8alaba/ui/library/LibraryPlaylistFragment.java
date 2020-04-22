package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistItem;
import java.util.List;


/**
 * This is the fragment which appears inside the library (Playlist Tab) that displays the list of
 * the user's playlists
 */
public class LibraryPlaylistFragment extends Fragment {

    private LibraryPlaylistViewModel playlistViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playlistViewModel = new ViewModelProvider(this).get(LibraryPlaylistViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library_playlist, container, false);

        LibraryPlaylistAdapter playlistAdapter = new LibraryPlaylistAdapter(this);

        RecyclerView recyclerView = root.findViewById(R.id.library_playlist_recycler_view);

        recyclerView.setAdapter(playlistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistViewModel.requestUserPlaylists();

        playlistViewModel.getUserPlaylists().observe(getViewLifecycleOwner(),
                new Observer<List<LibraryPlaylistItem>>() {
                    @Override
                    public void onChanged(List<LibraryPlaylistItem> libraryPlaylistItems) {
                        playlistAdapter.submitList(libraryPlaylistItems);
                    }
                });
        return root;
    }
}