package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Home.HomePlaylist;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksFragment;
import java.util.ArrayList;

public class GenrePlaylistsGridAdapter extends
        RecyclerView.Adapter<GenrePlaylistsGridAdapter.MyViewHolder> {

    private static ArrayList<HomePlaylist> mPlaylists;
    private static Fragment fragment;

    /**
     * @param myDataSet List of Playlists to show for the current Genre
     * @param fragment  The current fragment where this list will be created
     */
    public GenrePlaylistsGridAdapter(ArrayList<HomePlaylist> myDataSet, Fragment fragment) {
        mPlaylists = myDataSet;
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

        holder.playlistTitle.setText(mPlaylists.get(position).getName());
        holder.playlistSubTitle.setText(mPlaylists.get(position).getDescription());
        Picasso.get().load(mPlaylists.get(position).getImages().get(0).getUrl())
                .placeholder(R.drawable.spotify).into(holder.playlistImage);
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View v;
        ImageView playlistImage;
        TextView playlistTitle;
        TextView playlistSubTitle;

        MyViewHolder(View v) {
            super(v);

            playlistImage = v.findViewById(R.id.genre_playlists_item_image);
            playlistTitle = v.findViewById(R.id.genre_playlists_item_title);
            playlistSubTitle = v.findViewById(R.id.genre_playlists_item_sub_title);

            v.setOnClickListener(v1 -> {
                Bundle arguments = new Bundle();
                arguments.putString
                        (SearchByTypeConstantsHelper.PLAYLIST_ID_KEY,
                                mPlaylists.get(getAdapterPosition()).getId());
                Fragment targetFragment = new PlaylistTracksFragment();
                targetFragment.setArguments(arguments);
                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                                R.anim.fade_out)
                        .replace(R.id.nav_host_fragment, targetFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

}
