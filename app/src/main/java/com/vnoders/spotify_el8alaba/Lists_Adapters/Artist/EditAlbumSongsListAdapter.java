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

/**
 * A RecyclerView Adapter for showing artist's songs in edit album fragment in Spotify Artist
 * library.
 */
public class EditAlbumSongsListAdapter extends
        RecyclerView.Adapter<EditAlbumSongsListAdapter.MyViewHolder> {

    private static Fragment fragment;
    private static ArrayList<MyTrack> backDataset;

    /**
     * @param backDataset List Of Tracks in the artist's edit album fragment.
     * @param fragment    The fragment where this list is in
     */
    public EditAlbumSongsListAdapter(Fragment fragment, ArrayList<MyTrack> backDataset) {
        EditAlbumSongsListAdapter.backDataset = backDataset;
        EditAlbumSongsListAdapter.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EditAlbumSongsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.deleteSongImage.setImageResource(R.drawable.ic_remove_circle_outline_white_56dp);

        holder.deleteSongImage.setOnClickListener(v -> {
            ArtistEditAlbumFragment.selectedSongsIds.add(backDataset.get(position).getId());
            backDataset.remove(position);
            notifyDataSetChanged();
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
