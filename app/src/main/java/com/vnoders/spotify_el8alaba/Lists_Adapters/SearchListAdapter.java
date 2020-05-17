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
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.repositories.LocalDB.RecentSearches;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import com.vnoders.spotify_el8alaba.ui.search.SearchFragment;
import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {

    //TODO: SearchListItem Class should be a parent to all types that appear in search

    private static ArrayList<SearchListItem> mDataset;
    private static Fragment fragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchListAdapter(ArrayList<SearchListItem> myDataset, Fragment fragment) {
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

        holder.name.setText(mDataset.get(position).getName());
        holder.info.setText(mDataset.get(position).getInfo());
        Picasso.get().load(mDataset.get(position).getImageURL()).into(holder.image);

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

        MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_item_name_text_view);
            info = v.findViewById(R.id.search_item_info_text_view);
            image = v.findViewById(R.id.search_item_image_view);

            v.setOnClickListener(v1 -> {
                RecentSearches recentSearches = new RecentSearches();
                recentSearches.itemName = mDataset.get(getAdapterPosition()).getName();
                recentSearches.itemInfo = mDataset.get(getAdapterPosition()).getInfo();
                recentSearches.itemImageUrl = mDataset.get(getAdapterPosition()).getImageURL();
                if (!SearchFragment.mySearchHistory.contains(recentSearches)) {
                    db.recentSearchesDao().insertAll(recentSearches);
                }

                //TODO: Replace the Name Key with an ID one and pass the selected item id
                //TODO: The fragment to go to depends on the selected item type
                Fragment targetFragment = PlaylistHomeFragment
                        .newInstance(mDataset.get(getAdapterPosition()).getName());
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
