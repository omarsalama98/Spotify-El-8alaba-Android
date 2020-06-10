package com.vnoders.spotify_el8alaba.Lists_Adapters.Artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Artist.ArtistConstantsHelper;
import com.vnoders.spotify_el8alaba.Artist.ArtistEditSongFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.MyTrack;
import java.util.ArrayList;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<MyTrack> mockDataset;
    private static ArrayList<MyTrack> backDataset;

    //               The difference between these two constructors is that one uses mock data and the other
    //                  uses data retrieved from the server and the mock data one will be removed later on.

    /**
     * @param myDataset List Of Playlists in the home Category lists
     * @param fragment  The fragment where this list is in (Used to load another fragment)
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    public SongsListAdapter(ArrayList<MyTrack> myDataset, Fragment fragment) {
        backDataset = new ArrayList<>();
        mockDataset = myDataset;
        SongsListAdapter.fragment = fragment;
    }

    public SongsListAdapter(Fragment fragment, ArrayList<MyTrack> backDataset) {
        mockDataset = new ArrayList<>();
        SongsListAdapter.backDataset = backDataset;
        SongsListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SongsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_songs_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(backDataset.get(position).getName());
        String streams = String.valueOf(backDataset.get(position).getStreams());
        holder.streams.setText(streams);

        /*
        String imageUrl;
        List<Image> images = backDataset.get(position).get();
        if (!images.isEmpty()) {
            imageUrl = images.get(0).getUrl();
        } else {
            imageUrl = "https://getdrawings.com/free-icon-bw/black-music-icons-23.png";
        }
        Picasso.get().load(imageUrl).placeholder(R.drawable.spotify).into(holder.image);
        */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return backDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        View v;
        TextView title;
        TextView streams;
        ImageView image;

        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.artist_song_item_name_text_view);
            streams = v.findViewById(R.id.artist_song_item_streams_text_view);
            image = v.findViewById(R.id.artist_song_item_image_view);

            v.setOnClickListener(v1 -> {
                Bundle bundle = new Bundle();
                bundle.putString(ArtistConstantsHelper.SONG_ID,
                        backDataset.get(getAdapterPosition()).getId());
                bundle.putString(ArtistConstantsHelper.SONG_NAME,
                        backDataset.get(getAdapterPosition()).getName());
                Fragment targetFragment = new ArtistEditSongFragment();
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
