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
import com.vnoders.spotify_el8alaba.models.Search.Playlist;
import com.vnoders.spotify_el8alaba.models.Search.SearchAlbum;
import com.vnoders.spotify_el8alaba.models.Search.SearchArtist;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.Search.User;
import com.vnoders.spotify_el8alaba.models.TrackImage;
import com.vnoders.spotify_el8alaba.repositories.APIInterface;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    private static ArrayList<Object> mDataset;
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
        if (result instanceof SearchAlbum) {
            itemName = ((SearchAlbum) result).getName();
            holder.name.setText(itemName);
            List<TrackImage> images = ((SearchAlbum) result).getImages();
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
        if(result instanceof User) {
            itemName = ((User) result).getName();
            holder.name.setText(itemName);
            List<Image> images = ((User) result).getImages();
            itemInfo = "User";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof Playlist) {
            itemName = ((Playlist) result).getName();
            holder.name.setText(itemName);
            itemInfo = "Playlist";
            holder.info.setText(itemInfo);
            itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
            List<TrackImage> images = ((Playlist) result).getImages();
            if (images != null) {
                if (!images.isEmpty()) {
                    itemImageUrl = images.get(0).getUrl();
                }
            }
            Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify).into(holder.image);
        } else if (result instanceof SearchTrack) {
            itemName = ((SearchTrack) result).getName();
            holder.name.setText(itemName);
            Call<AlbumForTrack> call = apiService.getAlbum(((SearchTrack) result).getAlbum());
            call.enqueue(new Callback<AlbumForTrack>() {
                @Override
                public void onResponse(Call<AlbumForTrack> call, Response<AlbumForTrack> response) {
                    if (response.body() != null) {
                        itemInfo = "";
                        for (int i = 0; i < response.body().getArtists().size(); i++) {
                            itemInfo += response.body().getArtists().get(i).getName() + " ";
                        }
                        List<TrackImage> images = response.body().getImages();
                        if (images != null) {
                            if (!images.isEmpty()) {
                                itemImageUrl = images.get(0).getUrl();
                            }
                        }
                    } else {
                        itemInfo = "Track";
                        itemImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
                    }
                    holder.info.setText(itemInfo);
                    Picasso.get().load(itemImageUrl).placeholder(R.drawable.spotify)
                            .into(holder.image);
                }

                @Override
                public void onFailure(Call<AlbumForTrack> call, Throwable t) {
                }
            });
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
                if (result instanceof SearchAlbum) {
                    itemType = SearchByTypeConstantsHelper.ALBUM;
                    itemId = ((SearchAlbum) result).getId();
                    itemName = ((SearchAlbum) result).getName();
                    itemInfo = "Album";
                    List<TrackImage> images = ((SearchAlbum) result).getImages();
                    if (!images.isEmpty()) {
                        itemImageUrl = images.get(0).getUrl();
                    }
                } else if (result instanceof SearchArtist) {
                    itemType = SearchByTypeConstantsHelper.ARTIST;
                    itemId = ((SearchArtist) result).getId();
                    itemName = ((SearchArtist) result).getName();
                    itemInfo = "Artist";
                    List<Image> images = ((SearchArtist) result).getImages();
                    if (images != null) {
                        if(!images.isEmpty())
                            itemImageUrl = images.get(0).getUrl();
                    }
                }
                if (result instanceof User) {
                    itemType = SearchByTypeConstantsHelper.USER;
                    itemId = ((User) result).getId();
                    itemName = ((User) result).getName();
                    itemInfo = "User";
                    List<Image> images = ((User) result).getImages();
                    if (images != null) {
                        if (!images.isEmpty()) {
                            itemImageUrl = images.get(0).getUrl();
                        }
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
