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
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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

    private static final int REQUEST_CREATED_PLAYLIST_ID = 0;
    static final String REQUEST_DATA_CREATED_PLAYLIST_ID = "id";

    private ProgressBar progressBar;
    private NestedScrollView playlistsContainer;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CREATED_PLAYLIST_ID && resultCode == RESULT_OK && data != null) {
            String createdPlaylistId = data.getStringExtra(REQUEST_DATA_CREATED_PLAYLIST_ID);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment,
                            PlaylistHomeFragment.newInstance(createdPlaylistId))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        LibraryPlaylistViewModel playlistViewModel = new ViewModelProvider(requireActivity())
                .get(LibraryPlaylistViewModel.class);

        View root = inflater.inflate(R.layout.fragment_library_playlist, container, false);

        progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setBackgroundColor(Color.BLACK);

        playlistsContainer = root.findViewById(R.id.library_playlist_container);
        View createPlaylist = root.findViewById(R.id.create_playlist);

        createPlaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , CreatePlaylistActivity.class);
                startActivityForResult(intent, REQUEST_CREATED_PLAYLIST_ID);
            }
        });

        LibraryPlaylistAdapter playlistAdapter = new LibraryPlaylistAdapter(
                requireActivity().getSupportFragmentManager());

        RecyclerView recyclerView = root.findViewById(R.id.library_playlist_recycler_view);

        recyclerView.setAdapter(playlistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistViewModel.requestUserPlaylists();

        playlistViewModel.getUserPlaylists().observe(getViewLifecycleOwner(),
                new Observer<List<LibraryPlaylistItem>>() {
                    @Override
                    public void onChanged(List<LibraryPlaylistItem> libraryPlaylistItems) {
                        updateViewsVisibility();
                        playlistAdapter.submitList(libraryPlaylistItems);
                    }
                });

        View likedSongs = root.findViewById(R.id.liked_songs);
        TextView numberOfLikedSongs = root.findViewById(R.id.number_of_liked_songs);

        likedSongs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaylistTracksFragment playlistTracksFragment = PlaylistTracksFragment
                        .newInstance(null,"Liked Songs");

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, playlistTracksFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        playlistViewModel.getNumberOfLikedSongs().observe(getViewLifecycleOwner(),
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer noOfLikedSongs) {
                        if (noOfLikedSongs > 0) {
                            likedSongs.setVisibility(View.VISIBLE);
                            numberOfLikedSongs.setText(noOfLikedSongs + " songs");
                        } else {
                            // if no liked songs, the item will disappear
                            likedSongs.setVisibility(View.GONE);
                        }
                    }
                });

        return root;
    }

    private void updateViewsVisibility() {
        progressBar.setVisibility(View.GONE);
        playlistsContainer.setVisibility(View.VISIBLE);
    }
}