package com.vnoders.spotify_el8alaba.ui.library;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;
import org.jetbrains.annotations.NotNull;


/**
 * This is the fragment which appears when the user clicks on a playlist which contains the
 * playlist's cover photo and the playlist info.
 */
public class PlaylistHomeFragment extends Fragment {

    private PlaylistHomeViewModel playlistHomeViewModel;
    private AppBarLayout appBar;
    private ImageView upButton;
    private TextView title;
    private ImageView follow;
    private ImageView menu;
    private ImageView playlistImage;
    private TextView playlistName;
    private TextView playlistOwner;
    private Button shuffle;
    private Button editOrPreviewPlaylist;
    private TextView tracksSummary;
    private NestedScrollView playlistBody;
    private ProgressBar progressBar;

    private MediaPlaybackService mediaPlaybackService;


    // the fragment initialization parameters
    private static final String ARGUMENT_PLAYLIST_ID = "id";

    /**
     * This is a required public constructor used by android framework but should NOT be used to
     * initialize a new instance of {@link PlaylistHomeFragment}.
     * <p>
     * Use {@link #newInstance} instead to create a new {@link PlaylistHomeFragment} with
     * parameters.
     */
    public PlaylistHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param playlistId The id of the current playlist
     *
     * @return A new instance of fragment {@link PlaylistHomeFragment}.
     */
    @NotNull
    public static PlaylistHomeFragment newInstance(String playlistId) {
        PlaylistHomeFragment fragment = new PlaylistHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PLAYLIST_ID, playlistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlist_home, container, false);

        appBar = root.findViewById(R.id.app_bar);

        upButton = root.findViewById(R.id.playlist_home_up);
        title = root.findViewById(R.id.playlist_home_title);
        follow = root.findViewById(R.id.playlist_home_follow);
        menu = root.findViewById(R.id.playlist_home_more_menu);

        playlistImage = root.findViewById(R.id.playlist_home_image);
        playlistName = root.findViewById(R.id.playlist_home_playlist_name);
        playlistOwner = root.findViewById(R.id.playlist_home_playlist_owner);

        playlistBody = root.findViewById(R.id.playlist_home_body);
        shuffle = root.findViewById(R.id.playlist_home_shuffle_button);
        editOrPreviewPlaylist = root.findViewById(R.id.playlist_home_edit_playlist);
        tracksSummary = root.findViewById(R.id.playlist_home_tracks);

        progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setBackgroundColor(Color.BLACK);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        playlistHomeViewModel = new ViewModelProvider(this).get(PlaylistHomeViewModel.class);

        if (getArguments() != null) {
            String playlistId = getArguments().getString(ARGUMENT_PLAYLIST_ID);
            playlistHomeViewModel.setPlaylistId(playlistId);
        }

        mediaPlaybackService = ((MainActivity) requireActivity()).getService();

        shuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistId = playlistHomeViewModel.getPlaylistId();
                mediaPlaybackService.playPlaylist(playlistId, true, true, null);
            }
        });

        playlistHomeViewModel.getFollowedState()
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isFollowed) {
                        if (isFollowed) {
                            follow.setImageResource(R.drawable.like_track_liked);
                        } else {
                            follow.setImageResource(R.drawable.like_track_unliked_white);
                        }
                    }
                });


        playlistHomeViewModel.getImageUrl().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String url) {
                        Picasso.get().load(url).into(playlistImage);
                        GradientUtils.generate(url, appBar, GradientUtils.GRADIENT_LINEAR_BLACK);
                    }
                });

        playlistHomeViewModel.getPlaylistName().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String name) {
                        playlistName.setText(name);
                        title.setText(name);
                    }
                });

        playlistHomeViewModel.getPlaylistOwnerName().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String owner) {
                        playlistOwner.setText("By " + owner);
                    }
                });

        playlistHomeViewModel.getTracksSummary().observe(getViewLifecycleOwner(),
                new Observer<Spanned>() {
                    @Override
                    public void onChanged(Spanned summary) {
                        tracksSummary.setText(summary);
                    }
                });

        playlistHomeViewModel.getFinishedLoadingState().observe(getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean finishedLoading) {
                        if (finishedLoading) {
                            setProperData();
                            initializeViews();
                        }
                    }
                });

        playlistHomeViewModel.updateData();

    }

    /**
     * Set proper data based on current playlist state as owner, size and collaboration
     */
    private void setProperData() {
        Boolean isCollaborative = playlistHomeViewModel.isCollaborative().getValue();
        Boolean isOwnedByMe = playlistHomeViewModel.isOwnedByMe().getValue();
        Boolean isEmptyPlaylist = playlistHomeViewModel.isEmptyPlaylist().getValue();

        if(isCollaborative != null && isOwnedByMe != null && isEmptyPlaylist != null){
            if((isOwnedByMe || isCollaborative) && isEmptyPlaylist){
                shuffle.setText("Add Songs");
                editOrPreviewPlaylist.setVisibility(View.GONE);
                tracksSummary.setVisibility(View.GONE);
            }else if (!isOwnedByMe && !isCollaborative){
                editOrPreviewPlaylist.setText("Preview");
            }
        }
    }

    private void initializeViews() {

        updateViewsVisibility();

        OnClickListener openTracksClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaylistTracksFragment playlistTracksFragment = PlaylistTracksFragment
                        .newInstance(playlistHomeViewModel.getPlaylistId(),
                                title.getText().toString());

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, playlistTracksFragment)
                        .addToBackStack(null)
                        .commit();
            }
        };

        tracksSummary.setOnClickListener(openTracksClickListener);
        editOrPreviewPlaylist.setOnClickListener(openTracksClickListener);

        upButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        follow.setOnClickListener(new OnClickListener() {
            boolean isFollowed = true;

            @Override
            public void onClick(View view) {
                Boolean followState = playlistHomeViewModel.getFollowedState().getValue();
                if (followState != null) {
                    // We are NOTing the followState value because this button toggles follow and unfollow states
                    isFollowed = !followState;
                }
                if (isFollowed) {
                    playlistHomeViewModel.followPlaylist();
                } else {
                    playlistHomeViewModel.unfollowPlaylist();
                }
            }
        });

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

    private void updateViewsVisibility() {

        progressBar.setVisibility(View.GONE);

        appBar.setVisibility(View.VISIBLE);

        playlistBody.setVisibility(View.VISIBLE);
    }

}
