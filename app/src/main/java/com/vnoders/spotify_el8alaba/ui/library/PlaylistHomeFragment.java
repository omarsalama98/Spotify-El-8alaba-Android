package com.vnoders.spotify_el8alaba.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.R;

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

        shuffle = root.findViewById(R.id.playlist_home_shuffle_button);
        editOrPreviewPlaylist = root.findViewById(R.id.playlist_home_edit_playlist);
        tracksSummary = root.findViewById(R.id.playlist_home_tracks);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        playlistHomeViewModel = new ViewModelProvider(this).get(PlaylistHomeViewModel.class);

        View.OnClickListener openTracksClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new PlaylistTracksFragment())
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
            boolean isChecked = true;

            @Override
            public void onClick(View view) {
                isChecked = !isChecked;
                if (isChecked) {
                    ((ImageView) view).setImageResource(R.drawable.like_track_liked);
                } else {
                    ((ImageView) view).setImageResource(R.drawable.like_track_unliked_white);
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
                new Observer<String>() {
                    @Override
                    public void onChanged(String summary) {
                        tracksSummary.setText(summary);
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

        playlistHomeViewModel.updateData();

    }

}
