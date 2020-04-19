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

/**
 * This class is the recycler view adapter in the {@link LibraryPlaylistFragment} which holds the
 * list of playlists to be displayed and how to recycle them. This adapter currently can ONLY exist
 * inside a fragment
 */
public class LibraryPlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {

    private List<UserLibraryPlaylistItem> playlists;
    private Fragment fragment;

    /**
     * @param fragment The fragment which the recycler view of this adapter lives in. This is needed
     *                 in order to be able to open another fragments.
     */
    public LibraryPlaylistAdapter(Fragment fragment) {
        playlists = new ArrayList<>();
        this.fragment = fragment;
    }

    /**
     * @param playlists Sets the list of playlists to be displayed.
     */
    public void setUserPlaylists(List<UserLibraryPlaylistItem> playlists) {
        this.playlists = playlists;
    }

    /**
     * Create a new view and fill its data by {@link PlaylistViewHolder}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 specific position.
     * @param viewType The type of the created view holder in case the list has multiple types of
     *                 views. In our case it is not used because we have only one type of views
     *                 (Playlist Item)
     *
     * @return A new {@link PlaylistViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.playlist_list_item, parent, false);

        return new PlaylistViewHolder(view);
    }

    /**
     * Instead of creating a new {@link PlaylistViewHolder} we use an existing one that does not
     * appear on the screen but update its displaying data.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set ({@link #playlists}).
     */
    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.playlistName.setText(playlists.get(position).getName());
        holder.playlistInfo.setText( "by " + playlists.get(position).getOwner().getName());
        holder.playlistId = playlists.get(position).getId();
        String imageUrl = playlists.get(position).getImages().get(0).getUrl();
        Picasso.get().load(imageUrl).placeholder(R.drawable.artist_mock).into(holder.playlistArt);
    }

    /**
     * @return The total number of items in this adapter. i.e. the number of playlists.
     */
    @Override
    public int getItemCount() {
        return playlists.size();
    }


    /**
     * A ViewHolder describes an item in the list of the adapter and metadata about its place within
     * the RecyclerView.
     */
    class PlaylistViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout playlistBody;

        ImageView playlistArt;

        TextView playlistName;
        TextView playlistInfo;

        String playlistId;

        /**
         * @param itemView The view of the new created item
         */
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
                            .replace(R.id.nav_host_fragment,
                                    PlaylistHomeFragment.newInstance(playlistId))
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
