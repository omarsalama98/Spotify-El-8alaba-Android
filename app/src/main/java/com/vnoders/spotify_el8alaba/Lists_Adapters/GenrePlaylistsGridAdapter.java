package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Home.HomePlaylist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import java.util.ArrayList;
import java.util.List;

public class GenrePlaylistsGridAdapter extends
        RecyclerView.Adapter<GenrePlaylistsGridAdapter.MyViewHolder> {

    private static ArrayList<HomePlaylist> mPlaylists;
    private static ArrayList<SearchAlbum> mAlbums;
    private static Fragment fragment;

    /**
     * @param myDataSet List of Playlists to show for the current Genre
     * @param fragment  The current fragment where this list will be created
     */
    public GenrePlaylistsGridAdapter(ArrayList<HomePlaylist> myDataSet, Fragment fragment) {
        mPlaylists = myDataSet;
        GenrePlaylistsGridAdapter.fragment = fragment;
    }

    /**
     * @param myDataSet List of Playlists to show for the current Genre
     * @param fragment  The current fragment where this list will be created
     */
    public GenrePlaylistsGridAdapter(Fragment fragment, ArrayList<SearchAlbum> myDataSet) {
        mAlbums = myDataSet;
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

        if (mPlaylists != null) {
            holder.playlistTitle.setText(mPlaylists.get(position).getName());
            holder.playlistSubTitle.setText(mPlaylists.get(position).getDescription());
            List<Image> mImages = mPlaylists.get(position).getImages();
            if (mImages != null && !mImages.isEmpty()) {
                Picasso.get().load(mImages.get(0).getUrl())
                        .placeholder(R.drawable.spotify).into(holder.playlistImage);
            }
            holder.itemView.setOnClickListener(v -> {
                Fragment targetFragment = PlaylistHomeFragment
                        .newInstance(mPlaylists.get(position).getId());
                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                                R.anim.fade_out)
                        .replace(R.id.nav_host_fragment, targetFragment)
                        .addToBackStack(null)
                        .commit();
            });
        } else if (mAlbums != null) {
            holder.playlistTitle.setText(mAlbums.get(position).getName());
            holder.playlistSubTitle.setText(" ");
            List<Image> mImages = mAlbums.get(position).getImages();
            if (mImages != null && !mImages.isEmpty()) {
                Picasso.get().load(mImages.get(0).getUrl())
                        .placeholder(R.drawable.spotify).into(holder.playlistImage);
            }
            holder.itemView.setOnClickListener(v -> {
                Fragment targetFragment = AlbumFragment.newInstance(mAlbums.get(position).getId());
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

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mPlaylists != null) {
            return mPlaylists.size();
        } else {
            return mAlbums.size();
        }
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

        }
    }

}
