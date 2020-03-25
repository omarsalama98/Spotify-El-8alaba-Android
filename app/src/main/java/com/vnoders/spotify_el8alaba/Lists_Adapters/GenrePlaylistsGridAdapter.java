package com.vnoders.spotify_el8alaba.Lists_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Lists_Items.HomeInnerListItem;
import com.vnoders.spotify_el8alaba.R;
import java.util.ArrayList;

public class GenrePlaylistsGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HomeInnerListItem> mPlaylists;

    public GenrePlaylistsGridAdapter(Context mContext, ArrayList<HomeInnerListItem> myPlaylists) {
        this.mContext = mContext;
        mPlaylists = myPlaylists;
    }

    @Override
    public int getCount() {
        return mPlaylists.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlaylists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView playlistImage;
        TextView playlistTitle;
        TextView playlistSubTitle;

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.genre_playlists_list_item, null);
        }

        playlistImage = convertView.findViewById(R.id.genre_playlists_item_image);
        playlistTitle = convertView.findViewById(R.id.genre_playlists_item_title);
        playlistSubTitle = convertView.findViewById(R.id.genre_playlists_item_sub_title);

        playlistTitle.setText(mPlaylists.get(position).getTitle());
        playlistSubTitle.setText(mPlaylists.get(position).getSubTitle());
        Picasso.get().load(mPlaylists.get(position).getImageURL()).into(playlistImage);

        return convertView;
    }
}
