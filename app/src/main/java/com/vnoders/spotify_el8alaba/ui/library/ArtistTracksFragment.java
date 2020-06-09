package com.vnoders.spotify_el8alaba.ui.library;

import static com.vnoders.spotify_el8alaba.ui.library.TracksAdapter.TRACKS_TYPE.ARTIST_TRACKS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;
import java.util.List;
import org.jetbrains.annotations.NotNull;


/**
 * This is the fragment which contains the list of tracks of a specific artist.
 */
public class ArtistTracksFragment extends Fragment {

    private TextView title;
    private TextView artistName;
    private AppBarLayout appBar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private String artistId;

    // the fragment initialization parameters
    private static final String ARGUMENT_ARTIST_ID = "id";
    private static final String ARGUMENT_ARTIST_NAME = "name";

    /**
     * This is a required public constructor used by android framework but should NOT be used to
     * initialize a new instance of {@link ArtistTracksFragment}.
     * <p>
     * Use {@link #newInstance} instead to create a new {@link ArtistTracksFragment} with
     * parameters.
     */
    public ArtistTracksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param artistId The id of the current artist
     *
     * @return A new instance of fragment {@link ArtistTracksFragment}.
     */
    @NotNull
    public static ArtistTracksFragment newInstance(String artistId, String artistName) {
        ArtistTracksFragment fragment = new ArtistTracksFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ARTIST_ID, artistId);
        args.putString(ARGUMENT_ARTIST_NAME, artistName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArtistTracksViewModel artistViewModel = new ViewModelProvider(this)
                .get(ArtistTracksViewModel.class);

        MediaPlaybackService mediaPlaybackService = ((MainActivity) requireActivity()).getService();

        if (getArguments() != null) {
            artistId = getArguments().getString(ARGUMENT_ARTIST_ID);
            artistViewModel.setArtistId(artistId);
            artistName.setText(getArguments().getString(ARGUMENT_ARTIST_NAME));
            title.setText(getArguments().getString(ARGUMENT_ARTIST_NAME));
        }

        TracksAdapter adapter = new TracksAdapter(artistId, ARTIST_TRACKS, mediaPlaybackService);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        artistViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                adapter.setTracks(tracks);
                adapter.notifyDataSetChanged();
                initializeViews();
            }
        });

        artistViewModel.updateArtistTracks();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artist_tracks, container, false);

        title = root.findViewById(R.id.artist_tracks_title);
        artistName = root.findViewById(R.id.artist_tracks_name);
        appBar = root.findViewById(R.id.app_bar);
        recyclerView = root.findViewById(R.id.artist_tracks_recycler_view);
        ImageView upButton = root.findViewById(R.id.artist_tracks_up);

        progressBar = root.findViewById(R.id.progress_bar);

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return root;
    }

    private void initializeViews() {

        progressBar.setVisibility(View.GONE);

        appBar.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Title is fully transparent when the toolbar is expanded and fully visible
                // when toolbar is collapsed and semi-visible between them
                // visibility is calculated based on ratio between current toolbar height and the max height
                // Negative sign in -1.0f because verticalOffset is negative
                title.setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());
            }
        });

    }

}