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
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.library.Artist;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbum;
import com.vnoders.spotify_el8alaba.models.library.LibraryAlbumItem;
import com.vnoders.spotify_el8alaba.ui.library.LibraryAlbumAdapter.AlbumViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the recycler view adapter in the {@link LibraryAlbumFragment} which holds the list
 * of albums to be displayed and how to recycle them. This adapter currently can ONLY exist inside a
 * fragment
 */
public class LibraryAlbumAdapter extends ListAdapter<LibraryAlbumItem, AlbumViewHolder> {

    private FragmentManager fragmentManager;

    private static final ItemCallback<LibraryAlbumItem> DIFF_COMPARE_CALLBACK = new ItemCallback<LibraryAlbumItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull LibraryAlbumItem oldItem,
                @NonNull LibraryAlbumItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull LibraryAlbumItem oldItem,
                @NonNull LibraryAlbumItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    /**
     * @param fragmentManager The fragment manager which is needed in order to be able to open
     *                        another fragments.
     */
    public LibraryAlbumAdapter(FragmentManager fragmentManager) {
        super(DIFF_COMPARE_CALLBACK);
        this.fragmentManager = fragmentManager;
    }

    /**
     * Create a new view and fill its data by {@link AlbumViewHolder}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 specific position.
     * @param viewType The type of the created view holder in case the list has multiple types of
     *                 views. In our case it is not used because we have only one type of views
     *                 (Album Item)
     *
     * @return A new {@link AlbumViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.playlist_list_item, parent, false);

        return new AlbumViewHolder(view);
    }

    /**
     * Instead of creating a new {@link AlbumViewHolder} we use an existing one that does not appear
     * on the screen but update its displaying data.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item
     *                 at the given position in the data set.
     * @param position The position of the item within the adapter's data set ({@link
     *                 #getCurrentList()}).
     */
    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        LibraryAlbumItem album = getItem(position);

        if (album != null) {
            holder.bind(album.getAlbum());
        }
    }


    /**
     * A ViewHolder describes an item in the list of the adapter and metadata about its place within
     * the RecyclerView.
     */
    class AlbumViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout albumBody;

        ImageView albumArt;

        TextView albumName;
        TextView albumArtist;

        String albumId;

        /**
         * @param itemView The view of the new created item
         */
        AlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            albumBody = itemView.findViewById(R.id.library_playlist_item_body);

            albumArt = itemView.findViewById(R.id.library_playlist_art);

            albumName = itemView.findViewById(R.id.library_playlist_name);
            albumArtist = itemView.findViewById(R.id.library_playlist_info);

            albumBody.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlbumFragment fragment = AlbumFragment.newInstance(albumId);

                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            albumBody.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //TODO:
                    v.setPressed(false);
                    return true;
                }
            });

        }

        void bind(LibraryAlbum album) {
            albumId = album.getId();

            albumName.setText(album.getName());

            ArrayList<Artist> artists = album.getArtists();

            if (artists != null && !artists.isEmpty()) {
                albumArtist.setText(artists.get(0).getName());
            }

            List<Image> images = album.getImages();
            String imageUrl = null;
            if (images != null && images.size() > 0) {
                imageUrl = images.get(0).getUrl();
            }

            Picasso.get().load(imageUrl).placeholder(R.drawable.artist_mock)
                    .into(albumArt);
        }
    }

}
