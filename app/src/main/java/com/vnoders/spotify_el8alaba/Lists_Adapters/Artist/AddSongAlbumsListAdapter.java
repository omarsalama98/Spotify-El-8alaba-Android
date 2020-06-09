package com.vnoders.spotify_el8alaba.Lists_Adapters.Artist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Artist.ArtistAddSongFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.MyAlbum;
import java.util.ArrayList;

public class AddSongAlbumsListAdapter extends
        RecyclerView.Adapter<AddSongAlbumsListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<MyAlbum> mDataset;

    //               The difference between these two constructors is that one uses mock data and the other
    //                  uses data retrieved from the server and the mock data one will be removed later on.

    /**
     * //@param myDataset List Of Playlists in the home Category lists
     *
     * @param fragment The fragment where this list is in (Used to load another fragment)
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    /*public AddSongAlbumsListAdapter(ArrayList<MyAlbum> myDataset, Fragment fragment) {
        backDataset = new ArrayList<>();
        mockDataset = myDataset;
        AddSongAlbumsListAdapter.fragment = fragment;
    }*/
    public AddSongAlbumsListAdapter(Fragment fragment, ArrayList<MyAlbum> mDataset) {
        AddSongAlbumsListAdapter.mDataset = mDataset;
        AddSongAlbumsListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AddSongAlbumsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_albums_list_item, parent, false);
        return new AddSongAlbumsListAdapter.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            AddSongAlbumsListAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(mDataset.get(position).getName());
        holder.addSongImage.setVisibility(View.GONE);
        String imageUrl;
        String imgUrl = mDataset.get(position).getImgUrl();
        if (imgUrl != null) {
            imageUrl = imgUrl;
        } else {
            imageUrl = "https://getdrawings.com/free-icon-bw/black-music-icons-23.png";
        }
        Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
        if (mDataset.get(position).getSelected()) {
            holder.v.setBackground(fragment.getResources().getDrawable(R.drawable.cornered_button));
        } else {
            holder.v.setBackground(null);
        }

        holder.v.setOnClickListener(v -> {
            for (int i = 0; i < mDataset.size(); i++) {
                mDataset.get(i).setSelected(false);
            }
            mDataset.get(position).setSelected(true);
            notifyDataSetChanged();
            ArtistAddSongFragment.selectedAlbumId = mDataset.get(position).getId();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        public TextView title;
        public ImageView image;
        public ImageView addSongImage;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.artist_album_item_name_text_view);
            image = view.findViewById(R.id.artist_album_item_image_view);
            addSongImage = view.findViewById(R.id.artist_album_item_arrow_image_view);
            v = view.findViewById(R.id.artist_albums_list_item_view);

        }
    }

}
