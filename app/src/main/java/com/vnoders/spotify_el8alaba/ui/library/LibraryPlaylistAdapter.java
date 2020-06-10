package com.vnoders.spotify_el8alaba.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.App;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.LibraryPlaylistItem;
import com.vnoders.spotify_el8alaba.models.library.Owner;
import com.vnoders.spotify_el8alaba.ui.library.LibraryPlaylistAdapter.PlaylistViewHolder;
import java.util.List;

/**
 * This class is the recycler view adapter in the {@link LibraryPlaylistFragment} which holds the
 * list of playlists to be displayed and how to recycle them. This adapter currently can ONLY exist
 * inside a fragment
 */
public class LibraryPlaylistAdapter extends ListAdapter<LibraryPlaylistItem, PlaylistViewHolder> {

    private FragmentManager fragmentManager;

    private static final ItemCallback<LibraryPlaylistItem> DIFF_COMPARE_CALLBACK = new ItemCallback<LibraryPlaylistItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull LibraryPlaylistItem oldItem,
                @NonNull LibraryPlaylistItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull LibraryPlaylistItem oldItem,
                @NonNull LibraryPlaylistItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    /**
     * @param fragmentManager The fragment manager which is needed in order to be able to open
     *                        another fragments.
     */
    public LibraryPlaylistAdapter(FragmentManager fragmentManager) {
        super(DIFF_COMPARE_CALLBACK);
        this.fragmentManager = fragmentManager;
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
     * @param position The position of the item within the adapter's data set ({@link #getCurrentList()}).
     */
    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        LibraryPlaylistItem playlist = getItem(position);

        if (playlist != null) {
            holder.bind(playlist);
        }
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
                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment,
                                    PlaylistHomeFragment.newInstance(playlistId))
                            .addToBackStack(null)
                            .commit();
                }
            });

            playlistBody.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    LibraryPlaylistItem currentPlaylist = getItem(getLayoutPosition());

                    LibraryPlaylistOverflowMenu overflowMenu =
                            LibraryPlaylistOverflowMenu.newInstance(currentPlaylist);

                    overflowMenu.show(fragmentManager, null);

                    v.setPressed(false);
                    return true;
                }
            });

        }

        void bind(LibraryPlaylistItem playlist) {
            playlistName.setText(playlist.getName());

            Owner owner = playlist.getOwner();
            if (owner != null) {
                String info = String.format("%s %s",
                        App.getInstance().getString(R.string.by_owner),
                        owner.getName());
                playlistInfo.setText(info);
            }

            playlistId = playlist.getId();

            List<Image> images = playlist.getImages();
            String imageUrl = null;
            if (images != null && images.size() > 0) {
                imageUrl = images.get(0).getUrl();
            }

            Picasso.get().load(imageUrl).placeholder(R.drawable.artist_mock)
                    .into(playlistArt);
        }
    }

}
