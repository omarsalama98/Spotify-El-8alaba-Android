package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.DownloadImageTask;
import com.vnoders.spotify_el8alaba.Lists_Items.SearchListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;

public class SearchHistoryListAdapter extends
        RecyclerView.Adapter<SearchHistoryListAdapter.MyViewHolder> {

    private ArrayList<SearchListItem> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchHistoryListAdapter(ArrayList<SearchListItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchHistoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_history_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(mDataset.get(position).getName());
        holder.info.setText(mDataset.get(position).getInfo());
        new DownloadImageTask(holder.image).execute(mDataset.get(position).getImageURL());
        holder.removeIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());

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
        public TextView info;
        public ImageView image;
        public ImageView removeIcon;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_history_item_name_text_view);
            info = v.findViewById(R.id.search_history_item_info_text_view);
            image = v.findViewById(R.id.search_history_item_image_view);
            removeIcon = v.findViewById(R.id.search_history_item_remove);
        }

    }

}

