package com.vnoders.spotify_el8alaba.ui.library;

import static com.vnoders.spotify_el8alaba.ui.library.TracksAdapter.TRACKS_TYPE.ALBUM_TRACKS;

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
 * This is the fragment which contains the list of tracks of a specific album.
 */
public class AlbumTracksFragment extends Fragment {

    private TextView title;
    private TextView albumName;
    private AppBarLayout appBar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private String albumId;

    // the fragment initialization parameters
    private static final String ARGUMENT_ALBUM_ID = "id";
    private static final String ARGUMENT_ALBUM_NAME = "album_name";
    private static final String ARGUMENT_ARTIST_NAME = "artist_name";
    private static final String ARGUMENT_ALBUM_IMAGE = "album_image";

    /**
     * This is a required public constructor used by android framework but should NOT be used to
     * initialize a new instance of {@link AlbumTracksFragment}.
     * <p>
     * Use {@link #newInstance} instead to create a new {@link AlbumTracksFragment} with
     * parameters.
     */
    public AlbumTracksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param albumId The id of the current album
     *
     * @return A new instance of fragment {@link AlbumTracksFragment}.
     */
    @NotNull
    public static AlbumTracksFragment newInstance(String albumId, String albumName,
            String artistName, String albumImageUrl) {
        AlbumTracksFragment fragment = new AlbumTracksFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ALBUM_ID, albumId);
        args.putString(ARGUMENT_ALBUM_NAME, albumName);
        args.putString(ARGUMENT_ARTIST_NAME, artistName);
        args.putString(ARGUMENT_ALBUM_IMAGE, albumImageUrl);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlbumTracksViewModel albumViewModel = new ViewModelProvider(this)
                .get(AlbumTracksViewModel.class);

        MediaPlaybackService mediaPlaybackService = ((MainActivity) requireActivity()).getService();

        if (getArguments() != null) {
            albumId = getArguments().getString(ARGUMENT_ALBUM_ID);
            albumViewModel.setAlbumId(albumId);

            String albumNameString = getArguments().getString(ARGUMENT_ALBUM_NAME);
            albumViewModel.setAlbumName(albumNameString);
            albumName.setText(albumNameString);
            title.setText(albumNameString);

            String artistName = getArguments().getString(ARGUMENT_ARTIST_NAME);
            albumViewModel.setArtistName(artistName);

            String albumImageUrl = getArguments().getString(ARGUMENT_ALBUM_IMAGE);
            albumViewModel.setAlbumImageUrl(albumImageUrl);
        }

        TracksAdapter adapter = new TracksAdapter(albumId, ALBUM_TRACKS, mediaPlaybackService);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        albumViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                adapter.setTracks(tracks);
                adapter.notifyDataSetChanged();
                initializeViews();
            }
        });

        albumViewModel.updateAlbumTracks();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_album_tracks, container, false);

        title = root.findViewById(R.id.album_tracks_title);
        albumName = root.findViewById(R.id.album_tracks_name);
        appBar = root.findViewById(R.id.app_bar);
        recyclerView = root.findViewById(R.id.album_tracks_recycler_view);
        ImageView upButton = root.findViewById(R.id.album_tracks_up);

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