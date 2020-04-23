package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.models.library.Track;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksAdapter.TrackViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the recycler view adapter in the {@link PlaylistTracksFragment} which holds the
 * list of tracks in the playlist to be displayed and how to recycle them.
 */
public class PlaylistTracksAdapter extends RecyclerView.Adapter<TrackViewHolder> {

    private List<Track> tracks;

    public PlaylistTracksAdapter() {
        this.tracks = new ArrayList<>();
    }


    /**
     * @param tracks Sets the list of tracks in the playlist to be displayed.
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }


    /**
     * Create a new view and fill its data by {@link TrackViewHolder}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 specific position.
     * @param viewType The type of the created view holder in case the list has multiple types of
     *                 views. In our case it is not used because we have only one type of views
     *                 (Track Item)
     *
     * @return A new {@link TrackViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.track_list_item, parent, false);

        return new TrackViewHolder(view);
    }


    /**
     * Instead of creating a new {@link TrackViewHolder} we use an existing one that does not appear
     * on the screen but update its displaying data.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set ({@link #tracks}).
     */
    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        if (track != null) {
            holder.bind(track);
        }
    }


    /**
     * @return The total number of items in this adapter. i.e. the number of tracks in the playlist.
     */
    @Override
    public int getItemCount() {
        return tracks.size();
    }


    /**
     * A ViewHolder describes an item in the list of the adapter and metadata about its place within
     * the RecyclerView.
     */
    static class TrackViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout trackBody;

        ImageView playIcon;
        ImageView trackArt;

        ProgressBar previewProgressBar;

        TextView trackName;
        TextView artistName;

        ToggleButton likeTrack;
        ToggleButton hideTrack;
        Button othersMenu;


        /**
         * @param itemView The view of the new created item
         */
        TrackViewHolder(@NonNull View itemView) {
            super(itemView);

            trackBody = itemView.findViewById(R.id.playlist_track_body);

            playIcon = itemView.findViewById(R.id.playlist_play_icon);
            trackArt = itemView.findViewById(R.id.playlist_track_art);

            previewProgressBar = itemView.findViewById(R.id.playlist_progress_bar);

            trackName = itemView.findViewById(R.id.playlist_track_name);
            artistName = itemView.findViewById(R.id.playlist_artist_name);

            likeTrack = itemView.findViewById(R.id.playlist_like_track);
            hideTrack = itemView.findViewById(R.id.playlist_hide_track);
            othersMenu = itemView.findViewById(R.id.playlist_others_menu);

            trackBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),
                            "Song " + trackName.getText().toString() + " plays", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            trackBody.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TrackViewHolder.this.openTrackMenu(v);
                    return true;
                }
            });

            playIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    playIcon.setVisibility(View.GONE);
                    previewProgressBar.setVisibility(View.VISIBLE);
                }
            });

            previewProgressBar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    playIcon.setVisibility(View.VISIBLE);
                    previewProgressBar.setVisibility(View.GONE);
                }
            });

            likeTrack.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setBackgroundResource(R.drawable.like_track_liked);
                    } else {
                        buttonView.setBackgroundResource(R.drawable.like_track_unliked);
                    }
                }
            });

            hideTrack.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setBackgroundResource(R.drawable.hide_track_hidden);
                    } else {
                        buttonView.setBackgroundResource(R.drawable.hide_track_visible);
                    }
                }
            });

            othersMenu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTrackMenu(v);
                }
            });
        }

        void bind(Track track) {
            trackName.setText(track.getName());

            List<Artist> artists = track.getArtists();
            if (artists != null && artists.size() > 0) {
                artistName.setText(artists.get(0).getName());
            }
        }

        /**
         * TODO: Update it to reflect its true function
         * <p>
         * Creates the slide up menu of more options
         */
        private void openTrackMenu(View view) {
            Toast.makeText(view.getContext(),
                    "Song " + trackName.getText().toString() + " long click menu",
                    Toast.LENGTH_SHORT).show();
            view.setPressed(false);
        }


    }

}
