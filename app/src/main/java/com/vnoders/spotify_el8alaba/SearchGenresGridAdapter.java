package com.vnoders.spotify_el8alaba;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SearchGenresGridAdapter extends BaseAdapter {

    private Context mContext;
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ic_spotify_logo, R.drawable.spotify_el8alaba,
            R.drawable.ic_spotify_logo, R.drawable.spotify_el8alaba,
            R.drawable.ic_spotify_logo, R.drawable.spotify_el8alaba,
            R.drawable.ic_photo_camera, R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_spotify_logo, R.drawable.ic_left_arrow
    };

    // Constructor
    public SearchGenresGridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
