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
import com.vnoders.spotify_el8alaba.models.Search.AlbumForTrack;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchPlaylist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.currentUserProfile.UserProfile;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A RecyclerView Adapter for showing all search results in SearchFragment.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private ArrayList<Object> mDataset;
    private static Fragment fragment;
    private static APIInterface apiService;
    private static String itemInfo = "", itemName = "", itemImageUrl = "", itemId = "", itemType = "";

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchListAdapter(ArrayList<Object> myDataset, Fragment fragment) {
        mDataset = myDataset;
        SearchListAdapter.fragment = fragment;
        apiService = RetrofitClient.getInstance().getAPI(APIInterface.class);
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
        itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
        if (result instanceof SearchAlbum) {
            itemName = ((SearchAlbum) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((SearchAlbum) result).getImages();
            itemInfo = "Album";
            holder.info.setText(itemInfo);
            if (!images.isEmpty()) {
                itemImageUrl = images.get(0).getUrl();
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchArtist) {
            itemName = ((SearchArtist) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((SearchArtist) result).getImages();
            itemInfo = "Artist";
            holder.info.setText(itemInfo);
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        }
        if (result instanceof User) {
            itemName = ((User) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((User) result).getImages();
            itemInfo = "User";
            holder.info.setText(itemInfo);
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchPlaylist) {
            itemName = ((SearchPlaylist) result).getName();
            holder.name.setText(itemName);
            itemInfo = "Playlist";
            holder.info.setText(itemInfo);
            List<Image> images = ((SearchPlaylist) result).getImages();
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchTrack) {
            SearchTrack mTrack = (SearchTrack) result;
            itemName = mTrack.getName();
            holder.name.setText(itemName);
            if (mTrack.getArtistsNames() != null) {
                holder.info.setText(mTrack.getArtistsNames());
                itemImageUrl = mTrack.getImageUrl();
                Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify)
                        .into(holder.image);
            } else {
                Call<AlbumForTrack> call = apiService.getAlbum(mTrack.getAlbum());
                call.enqueue(new Callback<AlbumForTrack>() {
                    @Override
                    public void onResponse(Call<AlbumForTrack> call,
                            Response<AlbumForTrack> response) {
                        if (response.body() != null) {
                            itemInfo = "Song . ";
                            for (int i = 0; i < response.body().getArtists().size(); i++) {
                                itemInfo += response.body().getArtists().get(i).getName() + " ";
                            }
                            List<Image> images = response.body().getImages();
                            if (images != null) {
                                if (!images.isEmpty()) {
                                    itemImageUrl = images.get(0).getUrl();
                                }
                            }
                        } else {
                            itemInfo = "Song";
                        }
                        holder.info.setText(itemInfo);
                        mTrack.setArtistsNames(itemInfo);
                        mTrack.setImageUrl(itemImageUrl);
                        Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify)
                                .into(holder.image);
                    }

                    @Override
                    public void onFailure(Call<AlbumForTrack> call, Throwable t) {
                    }
                });
            }
        }

        holder.itemView.setOnClickListener(v -> {
            Object result1 = mDataset.get(position);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (result1 instanceof SearchAlbum) {
                itemType = SearchByTypeConstantsHelper.ALBUM;
                itemId = ((SearchAlbum) result1).getId();
                itemName = ((SearchAlbum) result1).getName();
                itemInfo = "Album";
                List<Image> images = ((SearchAlbum) result1).getImages();
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            } else if (result1 instanceof SearchArtist) {
                itemType = SearchByTypeConstantsHelper.ARTIST;
                itemId = ((SearchArtist) result1).getId();
                itemName = ((SearchArtist) result1).getName();
                itemInfo = "Artist";
                List<Image> images = ((SearchArtist) result1).getImages();
                if (images != null) {
                    if (!images.isEmpty()) {
                        itemImageUrl = images.get(0).getUrl();
                    }
                }
            }
            if (result1 instanceof User) {
                itemType = SearchByTypeConstantsHelper.USER;
                itemId = ((User) result1).getId();
                itemName = ((User) result1).getName();
                itemInfo = "User";
                List<Image> images = ((User) result1).getImages();
                if (images != null) {
                    if (!images.isEmpty()) {
                        itemImageUrl = images.get(0).getUrl();
                    }
                }
            } else if (result1 instanceof SearchPlaylist) {
                itemType = SearchByTypeConstantsHelper.PLAYLIST;
                itemId = ((SearchPlaylist) result1).getId();
                itemName = ((SearchPlaylist) result1).getName();
                itemInfo = "Playlist";
                List<Image> images = ((SearchPlaylist) result1).getImages();
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            } else if (result1 instanceof SearchTrack) {
                SearchTrack mTrack = (SearchTrack) result1;
                itemType = SearchByTypeConstantsHelper.TRACK;
                itemId = mTrack.getId();
                itemName = mTrack.getName();
                itemInfo = mTrack.getArtistsNames();
                if (mTrack.getImageUrl() != null) {
                    itemImageUrl = mTrack.getImageUrl();
                }
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

            Fragment targetFragment;
            switch (itemType) {
                case SearchByTypeConstantsHelper.ALBUM:
                    targetFragment = AlbumFragment.newInstance(itemId);
                    break;
                case SearchByTypeConstantsHelper.ARTIST:
                    targetFragment = ArtistFragment.newInstance(itemId);
                    break;
                case SearchByTypeConstantsHelper.PLAYLIST:
                    targetFragment = PlaylistHomeFragment.newInstance(itemId);
                    break;
                case SearchByTypeConstantsHelper.USER:
                    targetFragment = UserProfile.newInstance(itemId);
                    break;
                default:
                    targetFragment = AlbumFragment
                            .newInstance(((SearchTrack) result1).getAlbum());
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

        }
    }

}
