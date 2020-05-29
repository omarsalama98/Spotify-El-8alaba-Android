package com.vnoders.spotify_el8alaba.Lists_Adapters.Artist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.vnoders.spotify_el8alaba.Artist.ArtistEditAlbumFragment;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Artist.MyTrack;
import java.util.ArrayList;

public class EditAlbumSongsListAdapter extends
        RecyclerView.Adapter<EditAlbumSongsListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<MyTrack> mockDataset;
    private static ArrayList<MyTrack> backDataset;

    //               The difference between these two constructors is that one uses mock data and the other
    //                  uses data retrieved from the server and the mock data one will be removed later on.

    /**
     * //@param myDataset List Of Playlists in the home Category lists
     *
     * @param fragment The fragment where this list is in (Used to load another fragment)
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    /*public EditAlbumSongsListAdapter(ArrayList<MyTrack> myDataset, Fragment fragment) {
        backDataset = new ArrayList<>();
        mockDataset = myDataset;
        EditAlbumSongsListAdapter.fragment = fragment;
    }
    */
    public EditAlbumSongsListAdapter(Fragment fragment, ArrayList<MyTrack> backDataset) {
        mockDataset = new ArrayList<>();
        EditAlbumSongsListAdapter.backDataset = backDataset;
        EditAlbumSongsListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EditAlbumSongsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_albums_list_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(backDataset.get(position).getName());
        holder.deleteSongImage.setImageResource(R.drawable.ic_remove_circle_outline_white_56dp);

        holder.deleteSongImage.setOnClickListener(v -> {
            backDataset.remove(position);
            notifyDataSetChanged();
            ArtistEditAlbumFragment.selectedSongsIds.add(backDataset.get(position).getId());
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return backDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View v;
        public TextView title;
        public ImageView image;
        public ImageView deleteSongImage;

        MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.artist_song_item_name_text_view);
            image = v.findViewById(R.id.artist_song_item_image_view);
            deleteSongImage = v.findViewById(R.id.artist_song_item_arrow_image_view);

        }
    }

}
