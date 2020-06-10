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
import com.vnoders.spotify_el8alaba.ui.library.PlaylistHomeFragment;
import java.util.ArrayList;
import java.util.List;

public class HomeInnerListAdapter extends RecyclerView.Adapter<HomeInnerListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private ArrayList<HomePlaylist> backDataset;

    HomeInnerListAdapter(Fragment fragment, ArrayList<HomePlaylist> mbackDataset) {
        backDataset = mbackDataset;
        HomeInnerListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeInnerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_inner_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(backDataset.get(position).getName());
        holder.subTitle.setText(backDataset.get(position).getDescription());

        String imageUrl;
        List<Image> images = backDataset.get(position).getImages();
        if (images != null && !images.isEmpty()) {
            imageUrl = images.get(0).getUrl();
        } else {
            imageUrl = "https://getdrawings.com/free-icon-bw/black-music-icons-23.png";
        }
        Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Fragment targetFragment = PlaylistHomeFragment
                    .newInstance(backDataset.get(position).getId());
            fragment.getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                            R.anim.fade_out)
                    .replace(R.id.nav_host_fragment, targetFragment)
                    .addToBackStack(null)
                    .commit();
        });
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
        public View view;
        public TextView title;
        TextView subTitle;
        public ImageView image;

        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.home_inner_list_item_title);
            subTitle = v.findViewById(R.id.home_inner_list_item_sub_title);
            image = v.findViewById(R.id.home_inner_list_item_image);
        }
    }

}
