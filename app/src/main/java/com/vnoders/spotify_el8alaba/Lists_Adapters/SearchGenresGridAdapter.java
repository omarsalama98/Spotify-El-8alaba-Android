package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.ChartsFragment;
import com.vnoders.spotify_el8alaba.ConstantsHelper.SearchByTypeConstantsHelper;
import com.vnoders.spotify_el8alaba.GradientUtils;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Category;
import com.vnoders.spotify_el8alaba.models.Genre;
import com.vnoders.spotify_el8alaba.ui.search.GenreFragment;
import com.vnoders.spotify_el8alaba.ui.search.SpecialGenresFragment;
import java.io.IOException;
import java.util.ArrayList;

public class SearchGenresGridAdapter extends
        RecyclerView.Adapter<SearchGenresGridAdapter.MyViewHolder> {

    private ArrayList<Genre> mockGenresList;
    private ArrayList<Category> backGenresList;
    private static Fragment mFragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchGenresGridAdapter(Fragment fragment, ArrayList<Genre> myDataset) {
        mockGenresList = myDataset;
        backGenresList = new ArrayList<>();
        SearchGenresGridAdapter.mFragment = fragment;
    }

    public SearchGenresGridAdapter(ArrayList<Category> myDataset, Fragment fragment) {
        backGenresList = myDataset;
        mockGenresList = new ArrayList<>();
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

        if (backGenresList.isEmpty()) {
            genreImageBitmap[0] = mockGenresList.get(position).getImageBitmap();
            holder.genreImage.setImageBitmap(genreImageBitmap[0]);
            holder.genreTitle.setText(mockGenresList.get(position).getTitle());
        } else {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        genreImageBitmap[0] = Picasso.get()
                                .load(backGenresList.get(position).getIcons().get(0).getUrl())
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            holder.genreImage.setImageBitmap(genreImageBitmap[0]);
            holder.genreTitle.setText(backGenresList.get(position).getName());
        }

        GradientUtils.generate(genreImageBitmap[0], holder.genreLayout);

        holder.genreLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
                arguments.putString(SearchByTypeConstantsHelper.GENRE_NAME_KEY,
                        mockGenresList.get(position).getTitle());
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
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mockGenresList.isEmpty() ? backGenresList.size() : mockGenresList.size();
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
