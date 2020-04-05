package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.ChartsFragment;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.GenreFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Genre;
import java.util.ArrayList;

public class SearchGenresGridAdapter extends
        RecyclerView.Adapter<SearchGenresGridAdapter.MyViewHolder> {

    private ArrayList<Genre> genresList;
    private static Fragment mFragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchGenresGridAdapter(ArrayList<Genre> myDataset, Fragment fragment) {
        genresList = myDataset;
        SearchGenresGridAdapter.mFragment = fragment;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public SearchGenresGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_genres_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Bitmap genreImageBitmap = genresList.get(position).getImageBitmap();
        holder.genreImage.setImageBitmap(genreImageBitmap);
        holder.genreTitle.setText(genresList.get(position).getTitle());

        GradientUtils.generate(genreImageBitmap , holder.genreLayout);

        holder.genreLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                if (position > 1) {
                    fragment = new GenreFragment();
                } else {
                    fragment = new ChartsFragment();
                }
                mFragment.getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.search_fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return genresList.size();
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        ImageView genreImage;
        TextView genreTitle;
        LinearLayout genreLayout;

        MyViewHolder(View v) {
            super(v);
            genreImage = v.findViewById(R.id.search_genres_item_image);
            genreTitle = v.findViewById(R.id.search_genres_item_title_text);
            genreLayout = v.findViewById(R.id.search_genres_item_layout);
        }
    }

}
