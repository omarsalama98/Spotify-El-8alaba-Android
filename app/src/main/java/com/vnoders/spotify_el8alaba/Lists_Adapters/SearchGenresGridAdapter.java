package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.palette.graphics.Palette;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Genre;
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

    private int getGenreDominantClr(Bitmap bitmap) {

        return Palette.from(bitmap).generate().getDominantColor(Color.parseColor("#00FFFF"));
    }

    private int getGenreVibrantClr(Bitmap bitmap) {

        return Palette.from(bitmap).generate().getVibrantColor(Color.parseColor("#00FFFF"));
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

        //int h = genreLayout.getHeight();      //For other gradient types
        //int w = genreLayout.getWidth();

        ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
        mDrawable.getPaint().setShader
                (new SweepGradient(0, 0,
                        getGenreDominantClr(genreImageBitmap),
                        getGenreVibrantClr(genreImageBitmap)));

        genreLayout.setBackground(mDrawable);

        //genreLayout.setBackgroundColor(getGenreDominantClr(genreImageBitmap));

        return convertView;
    }


}
