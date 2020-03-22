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
import com.vnoders.spotify_el8alaba.models.Track;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistAdapter.TrackViewHolder;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<TrackViewHolder> {

    private List<Track> tracks;

    public PlaylistAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.track_list_item, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        holder.trackName.setText(tracks.get(position).getName());
        holder.artistName.setText(tracks.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

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

        private void openTrackMenu(View view) {
            Toast.makeText(view.getContext(),
                    "Song " + trackName.getText().toString() + " long click menu",
                    Toast.LENGTH_SHORT).show();
            view.setPressed(false);
        }


    }

}
