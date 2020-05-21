package com.vnoders.spotify_el8alaba.Lists_Adapters;

import static com.vnoders.spotify_el8alaba.MainActivity.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Album;
import com.vnoders.spotify_el8alaba.models.Artist;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.Track;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.models.library.Playlist;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    //TODO: SearchListItem Class should be a parent to all types that appear in search

    private static ArrayList<Object> mDataset;
    private static Fragment fragment;
    private static String itemInfo = "", itemName = "", itemImageUrl = "", itemId = "", type = "";

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchListAdapter(ArrayList<Object> myDataset, Fragment fragment) {
        mDataset = myDataset;
        SearchListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Object result = mDataset.get(position);
        if (result instanceof Album) {
            type = "Album";
            itemId = ((Album) result).getId();
            itemName = ((Album) result).getName();
            holder.name.setText(itemName);
            List<Artist> artists = ((Album) result).getArtists();
            List<TrackImage> images = ((Album) result).getImages();
            itemInfo = "Album";
            if (!artists.isEmpty()) {
                itemInfo += artists.get(0).getName();
            }
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).into(holder.image);
        } else if (result instanceof Artist) {
            type = "Artist";
            itemId = ((Artist) result).getId();
            itemName = ((Artist) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((Artist) result).getImages();
            itemInfo = "Artist " + ((Artist) result).getName();
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).into(holder.image);

        }
        /*  TODO: Add Users to search
        if(result instanceof User){
            type = "User";
            holder.name.setText((() result).get());
            List<Artist> artists = ((User) result).getArtists();
            List<TrackImage> images = ((Album) result).getImages();
            String albumInfo = "Album";
            if(!artists.isEmpty())  albumInfo += artists.get(0).getName();
            holder.info.setText(albumInfo);
            String imageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if(!images.isEmpty()) imageUrl = images.get(0).getUrl();
            Picasso.get().load(imageUrl).into(holder.image);
        }*/
        if (result instanceof Playlist) {
            type = "Playlist";
            itemId = ((Playlist) result).getId();
            itemName = ((Playlist) result).getName();
            holder.name.setText(itemName);
            List<TrackImage> images = ((Playlist) result).getImages();
            itemInfo = "Playlist";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).into(holder.image);
        }
        if (result instanceof Track) {
            type = "Track";
            itemId = ((Track) result).getId();
            itemName = ((Track) result).getName();
            holder.name.setText(itemName);
            List<Artist> artists = ((Track) result).getArtists();
            //TODO: Tracks should have images (Not added in backend)
            itemInfo = "Track";
            if (!artists.isEmpty()) {
                itemInfo += artists.get(0).getName();
            }
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            Picasso.get().load(itemImageUrl).into(holder.image);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View v;
        public TextView name;
        TextView info;
        public ImageView image;

        MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_item_name_text_view);
            info = v.findViewById(R.id.search_item_info_text_view);
            image = v.findViewById(R.id.search_item_image_view);

            v.setOnClickListener(v1 -> {
                RecentSearches recentSearches = new RecentSearches();
                recentSearches.itemName = itemName;
                recentSearches.itemInfo = itemInfo;
                recentSearches.itemImageUrl = itemImageUrl;
                if (!SearchFragment.mySearchHistory.contains(recentSearches)) {
                    db.recentSearchesDao().insertAll(recentSearches);
                }

                //TODO: Replace the Name Key with an ID one and pass the selected item id
                //TODO: The fragment to go to depends on the selected item type
                Fragment targetFragment;
                switch (type) {
                    case "Album":
                        targetFragment = new AlbumFragment();
                        break;
                    case "Artist":
                        targetFragment = new ArtistFragment();
                        break;
                    case "Playlist":
                        targetFragment = PlaylistHomeFragment
                                .newInstance(itemId);
                        break;
                    default:
                        targetFragment = new PlaylistTracksFragment();
                        break;
                }

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
