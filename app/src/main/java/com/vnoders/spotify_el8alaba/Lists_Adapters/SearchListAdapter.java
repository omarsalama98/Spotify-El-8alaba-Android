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
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Image;
import com.vnoders.spotify_el8alaba.models.Search.Album;
import com.vnoders.spotify_el8alaba.models.Search.Playlist;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private static ArrayList<Object> mDataset;
    private static Fragment fragment;
    private static String itemInfo = "", itemName = "", itemImageUrl = "", itemId = "", itemType = "";

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
            itemName = ((Album) result).getName();
            holder.name.setText(itemName);
            List<TrackImage> images = ((Album) result).getImages();
            itemInfo = "Album";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchArtist) {
            itemName = ((SearchArtist) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((SearchArtist) result).getImages();
            itemInfo = "Artist " + ((SearchArtist) result).getName();
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        }
        if(result instanceof User){
            itemName = ((User) result).getName();
            holder.name.setText(itemName);
            Image image = ((User) result).getImage();
            itemInfo = "User";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (image != null) {
                itemImageUrl = image.getUrl();
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof Playlist) {
            itemName = ((Playlist) result).getName();
            holder.name.setText(itemName);
            itemInfo = "Playlist";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            List<TrackImage> images = ((Playlist) result).getImages();
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchTrack) {
            itemName = ((SearchTrack) result).getName();
            holder.name.setText(itemName);
            List<String> artistsIds = ((SearchTrack) result).getArtists();
            //TODO: Tracks should have images (Not added in backend)
            itemInfo = "Track";
            // TODO: Maybe request the artists from the server by their ids and show them here
            /*if (!artistsIds.isEmpty()) {
                itemInfo += artistsIds.get(0);
            }*/
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        }

    }

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

                Object result = mDataset.get(getAdapterPosition());
                itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
                if (result instanceof Album) {
                    itemType = SearchByTypeConstantsHelper.ALBUM;
                    itemId = ((Album) result).getId();
                    itemName = ((Album) result).getName();
                    itemInfo = "Album";
                    List<TrackImage> images = ((Album) result).getImages();
                    if (!images.isEmpty()) {
                        itemImageUrl = images.get(0).getUrl();
                    }
                } else if (result instanceof SearchArtist) {
                    itemType = SearchByTypeConstantsHelper.ARTIST;
                    itemId = ((SearchArtist) result).getId();
                    itemName = ((SearchArtist) result).getName();
                    itemInfo = "Artist";
                    Image image = ((SearchArtist) result).getImages().get(0);
                    if (image != null) {
                        itemImageUrl = image.getUrl();
                    }
                }
                if (result instanceof User) {
                    itemType = SearchByTypeConstantsHelper.USER;
                    itemId = ((User) result).getId();
                    itemName = ((User) result).getName();
                    itemInfo = "User";
                    Image image = ((User) result).getImage();
                    if (image != null) {
                        itemImageUrl = image.getUrl();
                    }
                } else if (result instanceof Playlist) {
                    itemType = SearchByTypeConstantsHelper.PLAYLIST;
                    itemId = ((Playlist) result).getId();
                    itemName = ((Playlist) result).getName();
                    itemInfo = "Playlist";
                    List<TrackImage> images = ((Playlist) result).getImages();
                    if (!images.isEmpty()) {
                        itemImageUrl = images.get(0).getUrl();
                    }
                } else if (result instanceof SearchTrack) {
                    itemType = SearchByTypeConstantsHelper.TRACK;
                    itemId = ((SearchTrack) result).getId();
                    itemName = ((SearchTrack) result).getName();
                    itemInfo = "Track";
                }

                RecentSearches recentSearches = new RecentSearches();
                recentSearches.itemName = itemName;
                recentSearches.itemInfo = itemInfo;
                recentSearches.itemImageUrl = itemImageUrl;
                recentSearches.itemType = itemType;
                recentSearches.itemId = itemId;
                if (!SearchFragment.mySearchHistory.contains(recentSearches)) {
                    db.recentSearchesDao().insertAll(recentSearches);
                }

                //TODO: Replace the Name Key with an ID one and pass the selected item id
                //TODO: User should have a fragment to go to
                Fragment targetFragment;
                switch (itemType) {
                    case SearchByTypeConstantsHelper.ALBUM:
                        targetFragment = new AlbumFragment();
                        break;
                    case SearchByTypeConstantsHelper.ARTIST:
                        targetFragment = ArtistFragment.newInstance(itemId);
                        break;
                    case SearchByTypeConstantsHelper.PLAYLIST:
                        targetFragment = PlaylistHomeFragment.newInstance(itemId);
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
