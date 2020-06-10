package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class RecentlyPlayedListAdapter extends
        RecyclerView.Adapter<RecentlyPlayedListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<Object> backDataset;

    /**
     * @param myDataset List Of Items in this RecyclerView
     * @param fragment  The fragment where this list is in (Used to load another fragment)
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    public RecentlyPlayedListAdapter(Fragment fragment, ArrayList<Object> myDataset) {
        backDataset = myDataset;
        RecentlyPlayedListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecentlyPlayedListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recently_played_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Object result = backDataset.get(position);
        if (result instanceof SearchArtist) {
            SearchArtist mArtist = (SearchArtist) result;
            String imageUrl;
            holder.title.setText(mArtist.getName());
            List<Image> images = mArtist.getUserInfo().getImages();
            if (!images.isEmpty()) {
                imageUrl = images.get(0).getUrl();
            } else {
                imageUrl = "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba";
            }
            Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchAlbum) {
            SearchAlbum mAlbum = (SearchAlbum) result;
            String imageUrl;
            holder.title.setText(mAlbum.getName());
            List<Image> images = mAlbum.getImages();
            if (!images.isEmpty()) {
                imageUrl = images.get(0).getUrl();
            } else {
                imageUrl = "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba";
            }
            Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
            holder.image.setDisableCircularTransformation(true);
        } else if (result instanceof SearchPlaylist) {
            SearchPlaylist mPlaylist = (SearchPlaylist) result;
            String imageUrl;
            holder.title.setText(mPlaylist.getName());
            List<Image> images = mPlaylist.getImages();
            if (!images.isEmpty()) {
                imageUrl = images.get(0).getUrl();
            } else {
                imageUrl = "https://i.scdn.co/image/ab67706f00000002aa93fe4e8c2d24fc62556cba";
            }
            Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
            holder.image.setDisableCircularTransformation(true);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return backDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        public TextView title;
        public CircleImageView image;

        MyViewHolder(View v) {
            super(v);

            title = v.findViewById(R.id.home_recently_played_list_item_title);
            image = v.findViewById(R.id.home_recently_played_list_item_image);

            v.setOnClickListener(v1 -> {
                Fragment targetFragment;
                Object result = backDataset.get(getAdapterPosition());
                if (result instanceof SearchArtist) {
                    SearchArtist mArtist = (SearchArtist) result;
                    targetFragment = ArtistFragment.newInstance(mArtist.getId());
                } else if (result instanceof SearchAlbum) {
                    SearchAlbum mAlbum = (SearchAlbum) result;
                    targetFragment = AlbumFragment.newInstance(mAlbum.getId());
                } else if (result instanceof SearchPlaylist) {
                    SearchPlaylist mPlaylist = (SearchPlaylist) result;
                    targetFragment = PlaylistHomeFragment.newInstance(mPlaylist.getId());
                } else {
                    targetFragment = null;
                }

                if (targetFragment != null) {
                    fragment.getParentFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                                    R.anim.fade_out)
                            .replace(R.id.nav_host_fragment, targetFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

}

