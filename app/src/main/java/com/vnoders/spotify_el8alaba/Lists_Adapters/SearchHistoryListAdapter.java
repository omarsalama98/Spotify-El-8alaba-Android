package com.vnoders.spotify_el8alaba.Lists_Adapters;

import static com.vnoders.spotify_el8alaba.MainActivity.db;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.ui.library.AlbumFragment;
import com.vnoders.spotify_el8alaba.ui.library.ArtistFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistTracksFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;

/**
 * A RecyclerView Adapter for showing a user's search history list in SearchFragment.
 */
public class SearchHistoryListAdapter extends
        RecyclerView.Adapter<SearchHistoryListAdapter.MyViewHolder> {

    private static ArrayList<RecentSearches> mDataset;
    private static Fragment fragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchHistoryListAdapter(ArrayList<RecentSearches> myDataset, Fragment fragment) {
        mDataset = myDataset;
        SearchHistoryListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchHistoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_history_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(mDataset.get(position).itemName);
        holder.info.setText(mDataset.get(position).itemInfo);
        if (!mDataset.get(position).itemImageUrl.equals("")) {
            Picasso.get().load(mDataset.get(position).itemImageUrl).into(holder.image);
        }

        holder.removeIcon.setOnClickListener(v -> {
            db.recentSearchesDao().delete(mDataset.get(position));
            mDataset.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mDataset.size());
            if (mDataset.isEmpty()) {
                SearchFragment.removeSearchHistoryList();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        public TextView name;
        TextView info;
        public ImageView image;
        ImageView removeIcon;

        MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_history_item_name_text_view);
            info = v.findViewById(R.id.search_history_item_info_text_view);
            image = v.findViewById(R.id.search_history_item_image_view);
            removeIcon = v.findViewById(R.id.search_history_item_remove);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Replace the Name Key with an ID one and pass the selected item id
                    Fragment targetFragment;
                    String type = mDataset.get(getAdapterPosition()).itemType;
                    String itemId = mDataset.get(getAdapterPosition()).itemId;

                    switch (type) {
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
                }
            });
        }
    }
}

