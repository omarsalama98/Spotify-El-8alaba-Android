package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.HomePlaylist;
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class RecentlyPlayedListAdapter extends
        RecyclerView.Adapter<RecentlyPlayedListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<HomePlaylist> backDataset;

    /**
     * @param myDataset List Of Items in this RecyclerView
     * @param fragment  The fragment where this list is in (Used to load another fragment)
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    public RecentlyPlayedListAdapter(Fragment fragment, ArrayList<HomePlaylist> myDataset) {
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

        holder.title.setText(backDataset.get(position).getName());
        Picasso.get().load(backDataset.get(position).getImages().get(0).getUrl())
                .into(holder.image);
        /*TODO: Change The image circularity according to type of list item
        if(!mDataset.get(position).getType().equals("Artist"))
            holder.image.setDisableCircularTransformation(true);
         */

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
                Fragment targetFragment = PlaylistHomeFragment
                        .newInstance(backDataset.get(getAdapterPosition()).getId());
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

