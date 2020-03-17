package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.palette.graphics.Palette;
import com.vnoders.spotify_el8alaba.Genre;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;

public class SearchGenresGridAdapter extends BaseAdapter {

    private Context mContext;
    // Keep all Images in array
    private ArrayList<Genre> genresList;

    // Constructor
    public SearchGenresGridAdapter(Context c, ArrayList<Genre> genresList) {
        mContext = c;
        this.genresList = genresList;
    }

    private int getGenreBackgroundClr(Bitmap bitmap) {

        return Palette.from(bitmap).generate().getDominantColor(Color.parseColor("#00FFFF"));
    }

    public int getCount() {
        return genresList.size();
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return genresList.get(position);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView genreImage;
        TextView genreTitle;
        LinearLayout genreLayout;

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.search_genres_item, null);
        }

        //convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, R.dimen.genre_item_height));

        genreImage = convertView.findViewById(R.id.search_genres_item_image);
        genreTitle = convertView.findViewById(R.id.search_genres_item_title_text);
        genreLayout = convertView.findViewById(R.id.search_genres_item_layout);

        Bitmap genreImageBitmap = genresList.get(position).getImageBitmap();
        genreImage.setImageBitmap(genreImageBitmap);
        genreTitle.setText(genresList.get(position).getTitle());
        genreLayout.setBackgroundColor(getGenreBackgroundClr(genreImageBitmap));

        return convertView;
    }


}
