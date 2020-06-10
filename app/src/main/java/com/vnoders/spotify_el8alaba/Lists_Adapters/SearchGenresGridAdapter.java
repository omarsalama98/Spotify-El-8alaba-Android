package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.ui.search.ChartsFragment;
import com.vnoders.spotify_el8alaba.ui.search.GenreFragment;
import com.vnoders.spotify_el8alaba.ui.search.SpecialGenresFragment;
import java.util.ArrayList;

public class SearchGenresGridAdapter extends
        RecyclerView.Adapter<SearchGenresGridAdapter.MyViewHolder> {

    private ArrayList<Category> backGenresList;
    private static Fragment mFragment;

    public SearchGenresGridAdapter(ArrayList<Category> myDataset, Fragment fragment) {
        backGenresList = myDataset;
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

        final Bitmap[] genreImageBitmap = new Bitmap[1];

        if (!backGenresList.get(position).getImages().isEmpty()) {
            String imgUrl = backGenresList.get(position).getImages().get(0).getUrl();
            Picasso.get()
                    .load(imgUrl)
                    .into(holder.genreImage);
            GradientUtils.generate(imgUrl, holder.genreLayout);
        } else {
            BitmapDrawable bD = (BitmapDrawable) (holder.genreImage.getDrawable());
            GradientUtils.generate(bD.getBitmap(), holder.genreLayout);
        }
        holder.genreTitle.setText(backGenresList.get(position).getName());

        holder.genreLayout.setOnClickListener(v -> {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new SpecialGenresFragment();
                    break;
                case 1:
                    fragment = new ChartsFragment();
                    break;
                default:
                    fragment = new GenreFragment();
                    break;
            }
            Bundle arguments = new Bundle();
            arguments.putString(SearchByTypeConstantsHelper.GENRE_ID_KEY,
                    backGenresList.get(position).getId());
            arguments.putString(SearchByTypeConstantsHelper.GENRE_NAME_KEY,
                    backGenresList.get(position).getName());
            fragment.setArguments(arguments);
            mFragment.getParentFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right)
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return backGenresList.size();
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
