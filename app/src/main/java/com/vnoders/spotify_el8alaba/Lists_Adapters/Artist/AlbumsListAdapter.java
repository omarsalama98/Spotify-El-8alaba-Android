package com.vnoders.spotify_el8alaba.Lists_Adapters.Artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.Artist.ArtistConstantsHelper;
import com.vnoders.spotify_el8alaba.Artist.ArtistEditAlbumFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.ArtistAlbum;
import com.vnoders.spotify_el8alaba.models.Image;
import java.util.ArrayList;
import java.util.List;

public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumsListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<ArtistAlbum> mDataset;

    //               The difference between these two constructors is that one uses mock data and the other
    //                  uses data retrieved from the server and the mock data one will be removed later on.

    /**
     * //@param myDataset List Of Playlists in the home Category lists
     *
     * @param fragment The fragment where this list is in (Used to load another fragment)
     *                 <p>
     *                 // Provide a suitable constructor (depends on the kind of dataset) public
     *                 AlbumsListAdapter(ArrayList<ArtistAlbum> myDataset, Fragment fragment) {
     *                 mDataset = new ArrayList<>(); mockDataset = myDataset;
     *                 AlbumsListAdapter.fragment = fragment; }
     */

    public AlbumsListAdapter(Fragment fragment, ArrayList<ArtistAlbum> mDataset) {
        AlbumsListAdapter.mDataset = mDataset;
        AlbumsListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_albums_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(mDataset.get(position).getName());

        String imageUrl;
        List<Image> images = mDataset.get(position).getImages();
        if (!images.isEmpty()) {
            imageUrl = images.get(0).getUrl();
        } else {
            imageUrl = "https://getdrawings.com/free-icon-bw/black-music-icons-23.png";
        }
        Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
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

        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.artist_album_item_name_text_view);
            image = v.findViewById(R.id.artist_album_item_image_view);

            v.setOnClickListener(v1 -> {
                Bundle bundle = new Bundle();
                bundle.putString(ArtistConstantsHelper.ALBUM_ID,
                        mDataset.get(getAdapterPosition()).getId());
                bundle.putString(ArtistConstantsHelper.ALBUM_NAME,
                        mDataset.get(getAdapterPosition()).getName());

                String imageUrl;
                List<Image> images = mDataset.get(getAdapterPosition()).getImages();
                if (!images.isEmpty()) {
                    imageUrl = images.get(0).getUrl();
                } else {
                    imageUrl = "https://getdrawings.com/free-icon-bw/black-music-icons-23.png";
                }
                bundle.putString(ArtistConstantsHelper.ALBUM_IMAGE_URL, imageUrl);

                Fragment targetFragment = new ArtistEditAlbumFragment();
                targetFragment.setArguments(bundle);
                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in,
                                R.anim.fade_out)
                        .replace(R.id.artist_nav_host_fragment, targetFragment)
                        .addToBackStack(null)
                        .commit();
            });

        }
    }

}
