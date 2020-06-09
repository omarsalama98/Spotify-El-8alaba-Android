package com.vnoders.spotify_el8alaba.ui.library;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class ArtistFragment extends Fragment {

    private ArtistViewModel artistViewModel;

    private AppBarLayout appBar;
    private ImageView upButton;
    private TextView title;
    private ImageView follow;
    private ImageView menu;
    private TextView artistName;
    private Button shuffle;
    private TextView tracksSummary;
    private ProgressBar progressBar;
    private RecyclerView relatedArtistsRecyclerView;
    private TextView biography;

    private MediaPlaybackService mediaPlaybackService;


    // the fragment initialization parameters
    private static final String ARGUMENT_ARTIST_ID = "id";

    public ArtistFragment() {
        // Required empty public constructor
    }

    @NotNull
    public static ArtistFragment newInstance(String artistId) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_artist, container, false);

        appBar = root.findViewById(R.id.app_bar);

        upButton = root.findViewById(R.id.artist_up);
        title = root.findViewById(R.id.artist_title);
        follow = root.findViewById(R.id.artist_follow);
        menu = root.findViewById(R.id.artist_more_menu);

        artistName = root.findViewById(R.id.artist_name);

        shuffle = root.findViewById(R.id.artist_shuffle_button);
        tracksSummary = root.findViewById(R.id.artist_tracks);
        relatedArtistsRecyclerView = root.findViewById(R.id.related_artists_recycler_view);
        biography = root.findViewById(R.id.artist_biography);

        progressBar = root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        artistViewModel = new ViewModelProvider(this).get(ArtistViewModel.class);

        if (getArguments() != null) {
            String artistId = getArguments().getString(ARGUMENT_ARTIST_ID);
            artistViewModel.setArtistId(artistId);
        }

        mediaPlaybackService = ((MainActivity) requireActivity()).getService();

        RelatedArtistsAdapter adapter = new RelatedArtistsAdapter(
                requireActivity().getSupportFragmentManager());

        relatedArtistsRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        relatedArtistsRecyclerView.setAdapter(adapter);

        artistViewModel.getRelatedArtists().observe(getViewLifecycleOwner(),
                new Observer<List<Artist>>() {
                    @Override
                    public void onChanged(List<Artist> artists) {
                        adapter.setRelatedArtists(artists);
                        adapter.notifyDataSetChanged();
                    }
                });

        shuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistId = artistViewModel.getArtistId();
                mediaPlaybackService.playArtistTopTracks(artistId, true, true, null);
            }
        });

        tracksSummary.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArtistTracksFragment artistTracksFragment = ArtistTracksFragment
                        .newInstance(artistViewModel.getArtistId(), title.getText().toString());

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, artistTracksFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        artistViewModel.getFollowedState()
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

        artistViewModel.getImageUrl().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String url) {
                        loadArtistImage(url);
                    }
                });

        artistViewModel.getArtistName().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String name) {
                        artistName.setText(name);
                        title.setText(name);
                    }
                });

        artistViewModel.getTracksSummary().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String summary) {
                tracksSummary.setText(summary);
            }
        });

        artistViewModel.getFinishedLoadingState().observe(getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean finishedLoading) {
                        if (finishedLoading) {
                            initializeViews();
                        }
                    }
                });

        artistViewModel.getBiography().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String bio) {
                biography.setText(bio);
            }
        });

        artistViewModel.updateData();

    }

    private void loadArtistImage(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                appBar.setBackground(drawable);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                appBar.setBackground(placeHolderDrawable);
            }
        });
    }


    private void initializeViews() {

        progressBar.setVisibility(View.GONE);

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
                Boolean followState = artistViewModel.getFollowedState().getValue();
                if (followState != null) {
                    // We are NOTing the followState value because this button toggles follow and unfollow states
                    isFollowed = !followState;
                }
                if (isFollowed) {
                    artistViewModel.followArtist();
                } else {
                    artistViewModel.unfollowArtist();
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

}