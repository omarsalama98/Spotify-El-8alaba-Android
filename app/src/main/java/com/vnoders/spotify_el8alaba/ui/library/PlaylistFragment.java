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
import com.vnoders.spotify_el8alaba.PlaylistAdapter;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Track;
import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {

    private PlaylistViewModel playlistViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        playlistViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);

        ArrayList<Track> tracks = new ArrayList<>();

        // TODO: Change this to real objects instead of mocking
        tracks.add(new Track("Artist Name" , "Song Name 1"));
        tracks.add(new Track("Artist Name" , "Song Name 2"));
        tracks.add(new Track("Artist Name" , "Song Name 3"));
        tracks.add(new Track("Artist Name" , "Song Name 4"));
        tracks.add(new Track("Artist Name" , "Song Name 5"));
        tracks.add(new Track("Artist Name" , "Song Name 6"));
        tracks.add(new Track("Artist Name" , "Song Name 7"));
        tracks.add(new Track("Artist Name" , "Song Name 8"));
        tracks.add(new Track("Artist Name" , "Song Name 9"));
        tracks.add(new Track("Artist Name" , "Song Name 10"));
        tracks.add(new Track("Artist Name" , "Song Name 11"));
        tracks.add(new Track("Artist Name" , "Song Name 12"));
        tracks.add(new Track("Artist Name" , "Song Name 13"));
        tracks.add(new Track("Artist Name" , "Song Name 14"));
        tracks.add(new Track("Artist Name" , "Song Name 15"));

        playlistViewModel.setTracks(tracks);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library_playlist, container, false);

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(playlistViewModel.getTracks().getValue());

        RecyclerView recyclerView = root.findViewById(R.id.library_playlist_recycler_view);

        recyclerView.setAdapter(playlistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playlistViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                //TODO: update behavior of recycler view
            }
        });
        return root;
    }
}