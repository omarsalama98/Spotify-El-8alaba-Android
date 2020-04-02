package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistFragment;
import java.util.ArrayList;

public class GenrePlaylistsGridAdapter extends
        RecyclerView.Adapter<GenrePlaylistsGridAdapter.MyViewHolder> {

    private ArrayList<HomeInnerListItem> mPlaylists;
    private static Fragment fragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GenrePlaylistsGridAdapter(ArrayList<HomeInnerListItem> myDataset,
            Fragment fragment) {
        mPlaylists = myDataset;
        GenrePlaylistsGridAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GenrePlaylistsGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_playlists_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.playlistTitle.setText(mPlaylists.get(position).getTitle());
        holder.playlistSubTitle.setText(mPlaylists.get(position).getSubTitle());
        Picasso.get().load(mPlaylists.get(position).getImageURL()).into(holder.playlistImage);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        ImageView playlistImage;
        TextView playlistTitle;
        TextView playlistSubTitle;

        MyViewHolder(View v) {
            super(v);

            playlistImage = v.findViewById(R.id.genre_playlists_item_image);
            playlistTitle = v.findViewById(R.id.genre_playlists_item_title);
            playlistSubTitle = v.findViewById(R.id.genre_playlists_item_sub_title);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getParentFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                                    R.anim.fade_out)
                            .replace(R.id.nav_host_fragment, new PlaylistFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

}
