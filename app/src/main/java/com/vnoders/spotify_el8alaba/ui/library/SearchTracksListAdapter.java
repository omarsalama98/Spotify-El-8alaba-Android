package com.vnoders.spotify_el8alaba.ui.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.models.Search.SearchTrack;
import com.vnoders.spotify_el8alaba.models.library.AddTrackToPlaylistRequestBody;
import com.vnoders.spotify_el8alaba.repositories.LibraryApi;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTracksListAdapter extends
        RecyclerView.Adapter<SearchTracksListAdapter.MyViewHolder> {

    private static ArrayList<SearchTrack> mDataset;
    private static LibraryApi apiService;
    private static Fragment fragment;
    private static String playlistId;
    private static String trackInfo = "", trackName = "", trackImageUrl = "", trackId = "";

    public SearchTracksListAdapter(ArrayList<SearchTrack> myDataset, Fragment fragment,
            String playlistId) {
        mDataset = myDataset;
        SearchTracksListAdapter.playlistId = playlistId;
        SearchTracksListAdapter.fragment = fragment;
        apiService = RetrofitClient.getInstance().getAPI(LibraryApi.class);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchTracksListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_tracks_list_item, parent, false);
        return new SearchTracksListAdapter.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            SearchTracksListAdapter.MyViewHolder holder, final int position) {

        SearchTrack result = mDataset.get(position);

        trackName = result.getName();
        holder.name.setText(trackName);
        //TODO: Tracks should have images (Not added in backend)
        trackInfo = "Track";
        holder.info.setText(trackInfo);
        trackImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
        Picasso.get().load(trackImageUrl).placeholder(R.drawable.spotify).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View v;
        public TextView name;
        TextView info;
        public ImageView image, addSong;

        MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_item_name_text_view);
            info = v.findViewById(R.id.search_item_info_text_view);
            image = v.findViewById(R.id.search_item_image_view);
            addSong = v.findViewById(R.id.add_song_to_playlist_image);

            addSong.setOnClickListener(v12 -> {
                SearchTrack result = mDataset.get(getAdapterPosition());
                trackImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
                trackId = result.getId();
                trackName = result.getName();
                trackInfo = "Track";
                List<String> ids = new ArrayList<>();
                ids.add(trackId);
                AddTrackToPlaylistRequestBody requestBody = new AddTrackToPlaylistRequestBody(ids);
                Call<Void> call = apiService.addTracksToPlaylist(playlistId, requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(fragment.getContext(),
                                    mDataset.get(getAdapterPosition()).getName()
                                            + " added to your playlist",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                });
            });
        }
    }

}