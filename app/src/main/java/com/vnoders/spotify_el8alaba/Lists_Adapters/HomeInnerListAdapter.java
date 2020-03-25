package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;

public class HomeInnerListAdapter extends RecyclerView.Adapter<HomeInnerListAdapter.MyViewHolder> {

    private static Context context;
    private ArrayList<HomeInnerListItem> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeInnerListAdapter(ArrayList<HomeInnerListItem> myDataset, Context context) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeInnerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_inner_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(mDataset.get(position).getTitle());
        holder.subTitle.setText(mDataset.get(position).getSubTitle());
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
        public TextView title;
        public TextView subTitle;
        public ImageView image;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.home_inner_list_item_title);
            subTitle = v.findViewById(R.id.home_inner_list_item_sub_title);
            image = v.findViewById(R.id.home_inner_list_item_image);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
