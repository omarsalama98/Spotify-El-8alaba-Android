package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeMainListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;

public class HomeMainListAdapter extends RecyclerView.Adapter<HomeMainListAdapter.MyViewHolder> {

    private ArrayList<HomeMainListItem> mDataset;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeMainListAdapter(ArrayList<HomeMainListItem> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeMainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_main_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(mDataset.get(position).getTitle());
        holder.innerList.setAdapter(
                new HomeInnerListAdapter(mDataset.get(position).getInnerListItems(), context));
        holder.innerList.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

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
        public RecyclerView innerList;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.home_main_list_item_title);
            innerList = v.findViewById(R.id.home_inner_list_recycler_view);
        }

    }
}
