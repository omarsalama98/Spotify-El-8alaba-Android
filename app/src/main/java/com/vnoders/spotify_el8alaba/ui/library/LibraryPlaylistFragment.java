package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Playlist;
import java.util.ArrayList;


public class LibraryPlaylistFragment extends Fragment {

    private ArrayList<Playlist> playlists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        playlistViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);

        playlists = new ArrayList<>();

        playlists.add(new Playlist("Playlist 1 Name", "by user 1"));
        playlists.add(new Playlist("Playlist 2 Name", "by user 2"));
        playlists.add(new Playlist("Playlist 3 Name", "by user 3"));
        playlists.add(new Playlist("Playlist 4 Name", "by user 4"));
        playlists.add(new Playlist("Playlist 5 Name", "by user 5"));
        playlists.add(new Playlist("Playlist 6 Name", "by user 6"));
        playlists.add(new Playlist("Playlist 7 Name", "by user 7"));
        playlists.add(new Playlist("Playlist 8 Name", "by user 8"));

//        playlistViewModel.setTracks(tracks);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library_playlist, container, false);

//        LibraryPlaylistAdapter playlistAdapter = new PlaylistAdapter(
//                playlistViewModel.getTracks().getValue());

        LibraryPlaylistAdapter playlistAdapter = new LibraryPlaylistAdapter(playlists, this);

        RecyclerView recyclerView = root.findViewById(R.id.library_playlist_recycler_view);

        recyclerView.setAdapter(playlistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        playlistViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
//            @Override
//            public void onChanged(List<Track> tracks) {
//                TODO: update behavior of recycler view
//            }
//        });
        return root;
    }
}