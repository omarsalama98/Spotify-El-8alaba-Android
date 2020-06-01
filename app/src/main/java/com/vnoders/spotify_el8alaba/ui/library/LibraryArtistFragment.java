package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import java.util.ArrayList;
import java.util.List;

public class LibraryArtistFragment extends Fragment {

    private LibraryArtistViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(LibraryArtistViewModel.class);

        List<Artist> artists = new ArrayList<>();

        artists.add(new Artist("Artist 1 Name"));
        artists.add(new Artist("Artist 2 Name"));
        artists.add(new Artist("Artist 3 Name"));
        artists.add(new Artist("Artist 4 Name"));
        artists.add(new Artist("Artist 5 Name"));
        artists.add(new Artist("Artist 6 Name"));
        artists.add(new Artist("Artist 7 Name"));
        artists.add(new Artist("Artist 8 Name"));

        viewModel.setUserArtists(artists);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_library_artist, container, false);

        LibraryArtistAdapter artistAdapter = new LibraryArtistAdapter();

        artistAdapter.setArtists(viewModel.getUserArtists().getValue());

        RecyclerView recyclerView = root.findViewById(R.id.library_artist_recycler_view);

        recyclerView.setAdapter(artistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
