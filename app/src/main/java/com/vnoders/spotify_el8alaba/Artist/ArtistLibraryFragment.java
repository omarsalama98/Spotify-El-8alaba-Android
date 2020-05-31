package com.vnoders.spotify_el8alaba.Artist;

import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.mAlbums;
import static com.vnoders.spotify_el8alaba.Artist.ArtistMainActivity.mTracks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Adapters.Artist.AlbumsListAdapter;
import com.vnoders.spotify_el8alaba.Lists_Adapters.Artist.SongsListAdapter;
import com.vnoders.spotify_el8alaba.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistLibraryFragment extends Fragment {

    static SongsListAdapter songsListAdapter;
    static AlbumsListAdapter albumsListAdapter;
    private TextView songsTextView, albumsTextView;
    private RecyclerView songsRecyclerView, albumsRecyclerView;
    private Button addSongBtn, addAlbumBtn;

    public ArtistLibraryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_artist_library, container, false);
        songsTextView = root.findViewById(R.id.artist_songs_text_view);
        albumsTextView = root.findViewById(R.id.artist_albums_text_view);
        songsRecyclerView = root.findViewById(R.id.artist_songs_recycler_view);
        albumsRecyclerView = root.findViewById(R.id.artist_albums_recycler_view);
        addAlbumBtn = root.findViewById(R.id.artist_library_add_album);
        addSongBtn = root.findViewById(R.id.artist_library_add_song);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        songsListAdapter = new SongsListAdapter(this, mTracks);
        songsRecyclerView.setAdapter(songsListAdapter);
        songsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        albumsListAdapter = new AlbumsListAdapter(this, mAlbums);
        albumsRecyclerView.setAdapter(albumsListAdapter);
        albumsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        songsTextView.setOnClickListener(v -> {
            albumsTextView.setTextColor(getResources().getColor(R.color.pressed_grey));
            songsTextView.setTextColor(getResources().getColor(R.color.black));
            albumsRecyclerView.setVisibility(View.GONE);
            songsRecyclerView.setVisibility(View.VISIBLE);
        });

        albumsTextView.setOnClickListener(v -> {
            songsTextView.setTextColor(getResources().getColor(R.color.pressed_grey));
            albumsTextView.setTextColor(getResources().getColor(R.color.black));
            songsRecyclerView.setVisibility(View.GONE);
            albumsRecyclerView.setVisibility(View.VISIBLE);
        });

        addSongBtn.setOnClickListener(v -> {
            Fragment targetFragment = new ArtistAddSongFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                            R.anim.fade_out)
                    .replace(R.id.artist_nav_host_fragment, targetFragment)
                    .addToBackStack(null)
                    .commit();
        });


    }


}
