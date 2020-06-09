package com.vnoders.spotify_el8alaba.ui.library;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.MainActivity;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.trackplayer.MediaPlaybackService;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;


/**
 * This is the fragment which appears when the user clicks on a album which contains the album's
 * cover photo and the album info.
 */
public class AlbumFragment extends Fragment {

    private AlbumViewModel albumViewModel;
    private ImageView upButton;
    private TextView title;
    private ImageView follow;
    private ImageView menu;
    private View albumContainer;
    private ImageView albumImage;
    private TextView albumName;
    private TextView albumArtistInfo;
    private Button shuffle;
    private TextView tracksSummary;
    private TextView releaseDate;
    private View artistBody;
    private CircleImageView artistImage;
    private TextView artistName;

    private ProgressBar progressBar;

    private MediaPlaybackService mediaPlaybackService;


    // the fragment initialization parameters
    private static final String ARGUMENT_ALBUM_ID = "id";

    /**
     * This is a required public constructor used by android framework but should NOT be used to
     * initialize a new instance of {@link AlbumFragment}.
     * <p>
     * Use {@link #newInstance} instead to create a new {@link AlbumFragment} with parameters.
     */
    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param albumId The id of the current album
     *
     * @return A new instance of fragment {@link AlbumFragment}.
     */
    @NotNull
    public static AlbumFragment newInstance(String albumId) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ALBUM_ID, albumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_album, container, false);

        upButton = root.findViewById(R.id.album_up);
        title = root.findViewById(R.id.album_title);
        follow = root.findViewById(R.id.album_follow);
        menu = root.findViewById(R.id.album_more_menu);

        albumContainer = root.findViewById(R.id.album_container);
        albumImage = root.findViewById(R.id.album_image);
        albumName = root.findViewById(R.id.album_name);
        albumArtistInfo = root.findViewById(R.id.album_artist_info);

        shuffle = root.findViewById(R.id.album_shuffle_button);
        tracksSummary = root.findViewById(R.id.album_tracks);
        releaseDate = root.findViewById(R.id.album_release_date);

        artistBody = root.findViewById(R.id.album_artist_body);
        artistImage = root.findViewById(R.id.album_artist_image);
        artistName = root.findViewById(R.id.album_artist_name);

        progressBar = root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);

        if (getArguments() != null) {
            String albumId = getArguments().getString(ARGUMENT_ALBUM_ID);
            albumViewModel.setAlbumId(albumId);
        }

        mediaPlaybackService = ((MainActivity) requireActivity()).getService();

        shuffle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumId = albumViewModel.getAlbumId();
                mediaPlaybackService.playAlbum(albumId, true, true, null);
            }
        });

        albumViewModel.updateData();

        albumViewModel.getFollowedState().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFollowed) {
                if (isFollowed) {
                    follow.setImageResource(R.drawable.like_track_liked);
                } else {
                    follow.setImageResource(R.drawable.like_track_unliked_white);
                }
            }
        });

        albumViewModel.getImageUrl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String url) {
                Picasso.get().load(url).into(albumImage);
                GradientUtils
                        .generate(url, albumContainer, GradientUtils.GRADIENT_LINEAR_BLACK);
            }
        });

        albumViewModel.getAlbumName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name) {
                albumName.setText(name);
                title.setText(name);
            }
        });

        albumViewModel.getAlbumArtistInfo().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String info) {
                        albumArtistInfo.setText(info);
                    }
                });

        albumViewModel.getTracksSummary().observe(getViewLifecycleOwner(), new Observer<Spanned>() {
            @Override
            public void onChanged(Spanned summary) {
                tracksSummary.setText(summary);
            }
        });

        albumViewModel.getReleaseDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String date) {
                releaseDate.setText(date);
            }
        });

        albumViewModel.getArtistImageUrl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String url) {
                Picasso.get().load(url).into(artistImage);
            }
        });

        albumViewModel.getArtistName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name) {
                artistName.setText(name);
            }
        });

        albumViewModel.getFinishedLoadingState()
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean finishedLoading) {
                        if (finishedLoading) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        tracksSummary.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
            }
        });

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
                Boolean followState = albumViewModel.getFollowedState().getValue();
                if (followState != null) {
                    // We are NOTing the followState value because this button toggles follow and unfollow states
                    isFollowed = !followState;
                }
                if (isFollowed) {
                    albumViewModel.followAlbum();
                } else {
                    albumViewModel.unfollowAlbum();
                }
            }
        });

        artistBody.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistId = albumViewModel.getArtistId();

                ArtistFragment fragment = ArtistFragment.newInstance(artistId);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


}
