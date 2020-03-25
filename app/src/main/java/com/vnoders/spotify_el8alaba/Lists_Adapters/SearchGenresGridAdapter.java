package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import com.vnoders.spotify_el8alaba.ChartsFragment;
import com.vnoders.spotify_el8alaba.GenreFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Genre;
import java.util.ArrayList;

public class SearchGenresGridAdapter extends BaseAdapter {

    private Context mContext;
    // Keep all Images in array
    private ArrayList<Genre> genresList;
    private Fragment mFragment;

    // Constructor
    public SearchGenresGridAdapter(Context c, ArrayList<Genre> genresList, Fragment mFragment) {
        mContext = c;
        this.genresList = genresList;
        this.mFragment = mFragment;
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

        genreImage = convertView.findViewById(R.id.search_genres_item_image);
        genreTitle = convertView.findViewById(R.id.search_genres_item_title_text);
        genreLayout = convertView.findViewById(R.id.search_genres_item_layout);

        Bitmap genreImageBitmap = genresList.get(position).getImageBitmap();
        genreImage.setImageBitmap(genreImageBitmap);
        genreTitle.setText(genresList.get(position).getTitle());

        genreLayout.setOnClickListener(new OnClickListener() {
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
        //int h = genreLayout.getHeight();      //For other gradient types
        //int w = genreLayout.getWidth();

        ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
        mDrawable.getPaint().setShader
                (new SweepGradient(0, 0,
                        getGenreDominantClr(genreImageBitmap),
                        getGenreVibrantClr(genreImageBitmap)));

        genreLayout.setBackground(mDrawable);

        return convertView;
    }


}
