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
import com.vnoders.spotify_el8alaba.models.Search.Artist;
import com.vnoders.spotify_el8alaba.models.library.FollowArtistRequestBody;
import com.vnoders.spotify_el8alaba.repositories.LibraryApi;
import com.vnoders.spotify_el8alaba.repositories.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArtistsListAdapter extends
        RecyclerView.Adapter<SearchArtistsListAdapter.MyViewHolder> {

    private static ArrayList<Artist> mDataset;
    private static LibraryApi apiService;
    private static Fragment fragment;
    private static String artistId = "";

    public SearchArtistsListAdapter(ArrayList<Artist> myDataset, Fragment fragment) {
        mDataset = myDataset;
        SearchArtistsListAdapter.fragment = fragment;
        apiService = RetrofitClient.getInstance().getAPI(LibraryApi.class);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchArtistsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_tracks_list_item, parent, false);
        return new SearchArtistsListAdapter.MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(
            SearchArtistsListAdapter.MyViewHolder holder, final int position) {

        Artist result = mDataset.get(position);
        String artistName = result.getName();
        holder.name.setText(artistName);
        String artistImageUrl = "";
        if (mDataset.get(position).getImage() != null) {
            artistImageUrl = mDataset.get(position).getImage().getUrl();
        } else {
            artistImageUrl = "https://i.scdn.co/image/8522fc78be4bf4e83fea8e67bb742e7d3dfe21b4";
        }
        Picasso.get().load(artistImageUrl).placeholder(R.drawable.spotify).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView name;
        public ImageView image, addArtist;

        MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.search_item_name_text_view);
            image = v.findViewById(R.id.search_item_image_view);
            addArtist = v.findViewById(R.id.search_add_artist_image);

            addArtist.setOnClickListener(v12 -> {
                artistId = mDataset.get(getAdapterPosition()).getId();
                List<String> artistIds = new ArrayList<>();
                artistIds.add(artistId);
                FollowArtistRequestBody requestBody = new FollowArtistRequestBody(artistIds);
                Call<Void> call = apiService.followArtist(requestBody);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(fragment.getContext(),
                                    mDataset.get(getAdapterPosition()).getName()
                                            + " added to your artists",
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