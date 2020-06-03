package com.vnoders.spotify_el8alaba.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.repositories.LibraryRepository;
import com.vnoders.spotify_el8alaba.ui.library.SearchTracksListAdapter.SearchViewHolder;
import java.util.ArrayList;

public class SearchTracksListAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private static ArrayList<SearchTrack> mDataset;
    private static String playlistId;

    public SearchTracksListAdapter(ArrayList<SearchTrack> myDataset, String playlistId) {
        mDataset = myDataset;
        SearchTracksListAdapter.playlistId = playlistId;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_tracks_list_item, parent, false);
        return new SearchViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {

        SearchTrack result = mDataset.get(position);

        String trackName = result.getName();
        holder.name.setText(trackName);
        //TODO: Tracks should have images (Not added in backend)
        String trackInfo = "Track";
        holder.info.setText(trackInfo);
        String trackImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
        Picasso.get().load(trackImageUrl).placeholder(R.drawable.spotify).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView info;
        ImageView image, addSong;

        SearchViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_item_name_text_view);
            info = v.findViewById(R.id.search_item_info_text_view);
            image = v.findViewById(R.id.search_item_image_view);
            addSong = v.findViewById(R.id.add_song_to_playlist_image);

            addSong.setOnClickListener(v12 -> {
                SearchTrack result = mDataset.get(getAdapterPosition());
                LibraryRepository.addTrackToPlaylist(playlistId, result.getId());
            });
        }
    }

}