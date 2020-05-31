package com.vnoders.spotify_el8alaba.ui.currentUserProfile;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.userProfile.UserPlaylistItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ali Adel
 * Adapter to use in recycler view to display the playlist item
 */
public class OwnedPlaylistsAdapter extends RecyclerView.Adapter<OwnedPlaylistsAdapter.OwnedPlaylistViewHolder> {

    // data source of playlists
    private ArrayList<UserPlaylistItem> mOwnedPlaylists;
    // listener that responds to item clicks
    private OwnedPlaylistItemClick mListener;

    /**
     * Interface implemented by associating fragment to respond to item clicks
     */
    public interface OwnedPlaylistItemClick {
        void onItemClick(int position);
    }

    /**
     * View holder to hold data of each item view
     */
    public static class OwnedPlaylistViewHolder extends RecyclerView.ViewHolder{
        // image at left of item
        private CircleImageView mPlaylistImage;
        // name of playlist
        private TextView mPlaylistName;
        // it's description
        private TextView mPlaylistDescription;
        // layout which holds entire item
        private View mPlaylistWrapper;

        /**
         * init the variables to get reference to all of them
         */
        public OwnedPlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            mPlaylistImage = itemView.findViewById(R.id.owned_playlist_image);
            mPlaylistName = itemView.findViewById(R.id.owned_playlist_name);
            mPlaylistDescription = itemView.findViewById(R.id.owned_playlist_description);
            mPlaylistWrapper = itemView.findViewById(R.id.owned_playlist_single_item);
        }
    }

    /**
     * Constructor to init the adapter
     * @param ownedPlaylists data source containing data
     * @param listener to listen to item clicks
     */
    public OwnedPlaylistsAdapter(ArrayList<UserPlaylistItem> ownedPlaylists, OwnedPlaylistItemClick listener) {
        this.mOwnedPlaylists = ownedPlaylists;
        this.mListener = listener;
    }

    /**
     * create the view and return it
     */
    @NonNull
    @Override
    public OwnedPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.owned_playlist_item, parent,false);
        return new OwnedPlaylistViewHolder(v);
    }

    /**
     * sets the data with items to display a single item and sets the listener
     */
    @Override
    public void onBindViewHolder(@NonNull OwnedPlaylistViewHolder holder, int position) {
        UserPlaylistItem currentItem = mOwnedPlaylists.get(position);
        holder.mPlaylistName.setText(currentItem.getName());
        holder.mPlaylistDescription.setText(currentItem.getDescription());

        if (currentItem.getImages() != null) {
            if (currentItem.getImages().size() > 0) {
                if (!(TextUtils.isEmpty(currentItem.getImages().get(0).getUrl()))) {
                    Picasso.get().load(currentItem.getImages().get(0).getUrl())
                            .placeholder(R.drawable.unnamed).error(R.drawable.unnamed)
                            .into(holder.mPlaylistImage);
                }
            }
        }

        holder.mPlaylistWrapper.setOnClickListener(v -> mListener.onItemClick(position));
    }

    /**
     * @return items count
     */
    @Override
    public int getItemCount() {
        return mOwnedPlaylists.size();
    }

}
