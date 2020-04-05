package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.library.UserLibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.ui.library.LibraryPlaylistAdapter.PlaylistViewHolder;
import java.util.ArrayList;
import java.util.List;

public class LibraryPlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {

    private List<UserLibraryPlaylistItem> playlists;
    private Fragment fragment;

    public LibraryPlaylistAdapter(Fragment fragment) {
        playlists = new ArrayList<>();
        this.fragment = fragment;
    }

    public void setUserPlaylists(List<UserLibraryPlaylistItem> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.playlist_list_item, parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.playlistName.setText(playlists.get(position).getName());
        holder.playlistInfo.setText( "by " + playlists.get(position).getOwner().getName());
        String imageUrl = playlists.get(position).getImages().get(0).getUrl();
        Picasso.get().load(imageUrl).placeholder(R.drawable.artist_mock).into(holder.playlistArt);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout playlistBody;

        ImageView playlistArt;

        TextView playlistName;
        TextView playlistInfo;

        PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            playlistBody = itemView.findViewById(R.id.library_playlist_item_body);

            playlistArt = itemView.findViewById(R.id.library_playlist_art);

            playlistName = itemView.findViewById(R.id.library_playlist_name);
            playlistInfo = itemView.findViewById(R.id.library_playlist_info);

            playlistBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_library, new PlaylistFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });

            playlistBody.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(),
                            "Playlist " + playlistName.getText().toString() + " long click menu",
                            Toast.LENGTH_SHORT).show();
                    v.setPressed(false);
                    return true;
                }
            });

        }

    }

}
