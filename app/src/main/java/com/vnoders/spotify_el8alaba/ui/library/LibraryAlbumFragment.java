package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumItem;
import java.util.List;


/**
 * This is the fragment which appears inside the library (Albums Tab) that displays the list of the
 * user's albums
 */
public class LibraryAlbumFragment extends Fragment {

    private LibraryAlbumViewModel albumViewModel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        albumViewModel = new ViewModelProvider(requireActivity()).get(LibraryAlbumViewModel.class);

        View root = inflater.inflate(R.layout.fragment_library_album, container, false);

        progressBar = root.findViewById(R.id.progress_bar);

        LibraryAlbumAdapter albumAdapter = new LibraryAlbumAdapter(
                requireActivity().getSupportFragmentManager());

        RecyclerView recyclerView = root.findViewById(R.id.library_album_recycler_view);

        recyclerView.setAdapter(albumAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        albumViewModel.getUserAlbums().observe(getViewLifecycleOwner(),
                new Observer<List<LibraryAlbumItem>>() {
                    @Override
                    public void onChanged(List<LibraryAlbumItem> libraryAlbumItems) {
                        progressBar.setVisibility(View.GONE);
                        albumAdapter.submitList(libraryAlbumItems);
                    }
                });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        albumViewModel.requestUserAlbums();
    }

    @Override
    public void onPause() {
        super.onPause();
        progressBar.setVisibility(View.VISIBLE);
    }
}