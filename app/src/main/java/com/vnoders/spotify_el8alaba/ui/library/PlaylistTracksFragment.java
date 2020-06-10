package com.vnoders.spotify_el8alaba.ui.library;

import static com.vnoders.spotify_el8alaba.ui.library.TracksAdapter.TRACKS_TYPE.LIKED_TRACKS;
import static com.vnoders.spotify_el8alaba.ui.library.TracksAdapter.TRACKS_TYPE.PLAYLIST_TRACKS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.ui.library.TracksAdapter.TRACKS_TYPE;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;


/**
 * This is the fragment which contains the list of tracks of a specific playlist.
 */
public class PlaylistTracksFragment extends Fragment {

    private TextView title;
    private TextView playlistName;
    private AppBarLayout appBar;
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton shuffle;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    private List<String> tracksIds;
    private String playlistId;


    // the fragment initialization parameters
    private static final String ARGUMENT_PLAYLIST_ID = "id";
    private static final String ARGUMENT_PLAYLIST_NAME = "name";
    private MediaPlaybackService mediaPlaybackService;

    /**
     * This is a required public constructor used by android framework but should NOT be used to
     * initialize a new instance of {@link PlaylistTracksFragment}.
     * <p>
     * Use {@link #newInstance} instead to create a new {@link PlaylistTracksFragment} with
     * parameters.
     */
    public PlaylistTracksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param playlistId The id of the current playlist
     *
     * @return A new instance of fragment {@link PlaylistTracksFragment}.
     */
    @NotNull
    public static PlaylistTracksFragment newInstance(String playlistId, String playlistName) {
        PlaylistTracksFragment fragment = new PlaylistTracksFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PLAYLIST_ID, playlistId);
        args.putString(ARGUMENT_PLAYLIST_NAME, playlistName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlaylistTracksViewModel playlistViewModel = new ViewModelProvider(this)
                .get(PlaylistTracksViewModel.class);

        mediaPlaybackService = ((MainActivity) requireActivity()).getService();


        if (getArguments() != null) {
            playlistId = getArguments().getString(ARGUMENT_PLAYLIST_ID);
            playlistViewModel.setPlaylistId(playlistId);
            playlistName.setText(getArguments().getString(ARGUMENT_PLAYLIST_NAME));
            title.setText(getArguments().getString(ARGUMENT_PLAYLIST_NAME));
        }

        TRACKS_TYPE type = (playlistId == null) ? LIKED_TRACKS : PLAYLIST_TRACKS;

        TracksAdapter playlistAdapter = new TracksAdapter(playlistId, type, mediaPlaybackService);

        recyclerView.setAdapter(playlistAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                playlistAdapter.setTracks(tracks);

                // if no playlist id, therefore it is list of liked songs, we need tracks' ids to play them
                if (playlistId == null) {
                    List<String> tracksIds = new ArrayList<>();
                    // TODO: make it async
                    for (Track track : tracks) {
                        tracksIds.add(track.getId());
                    }
                    PlaylistTracksFragment.this.tracksIds = tracksIds;
                    playlistAdapter.setTracksIds(tracksIds);
                }

                playlistAdapter.notifyDataSetChanged();
                initializeViews();
                //TODO : Use DiffUtil to generate smaller changes instead of notifyDataSetChanged
                // for better performance
            }
        });

        playlistViewModel.getPlaylistImageUrl().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String url) {
                        GradientUtils.generate(url, appBar, GradientUtils.GRADIENT_LINEAR_BLACK);
                    }
                });

        playlistViewModel.updatePlaylistTracks();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlist_tracks, container, false);

        title = root.findViewById(R.id.playlist_tracks_title);
        playlistName = root.findViewById(R.id.playlist_tracks_name);
        appBar = root.findViewById(R.id.app_bar);
        toolbar = root.findViewById(R.id.toolbar);
        ImageView upButton = root.findViewById(R.id.playlist_tracks_up);
        shuffle = root.findViewById(R.id.playlist_tracks_shuffle);
        recyclerView = root.findViewById(R.id.playlist_recycler_view);

        progressBar = root.findViewById(R.id.progress_bar);

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        shuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // if no playlist id, therefore it is list of liked songs, we need tracks' ids to play them
                if (playlistId != null) {
                    mediaPlaybackService.playPlaylist(playlistId, true, true, null);
                } else {
                    mediaPlaybackService.playList(tracksIds, true, true, null);
                }
            }
        });

        return root;
    }

    private void initializeViews(){

        progressBar.setVisibility(View.GONE);

        appBar.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Title is fully transparent when the toolbar is expanded and fully visible
                // when toolbar is collapsed and semi-visible between them
                // visibility is calculated based on ratio between current toolbar height and the max height
                // Negative sign in -1.0f because verticalOffset is negative
                title.setAlpha(-1.0f * verticalOffset / appBarLayout.getTotalScrollRange());

                int topBoundary = toolbar.getMeasuredHeight()
                        + ((MarginLayoutParams) toolbar.getLayoutParams()).topMargin;

                int margin = appBarLayout.getBottom() - shuffle.getMeasuredHeight();

                MarginLayoutParams layoutParams = (MarginLayoutParams) shuffle.getLayoutParams();
                layoutParams.topMargin = Math.max(margin, topBoundary);

            }
        });

    }

}